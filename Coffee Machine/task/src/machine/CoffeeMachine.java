package machine;

import java.util.Scanner;

enum machineState {
    CHOOSING_ACTION("Write action (buy, fill, take, remaining, exit): %n"),
    BUY("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:%n"),
    FILL_WATER("Write how many ml of water do you want to add:%n"),
    FILL_MILK("Write how many ml of milk do you want to add:%n"),
    FILL_COFFEE_BEANS("Write how many grams of coffee beans do you want to add:%n"),
    FILL_CUPS("Write how many disposable cups of coffee you want to add:%n");

    String outputText;

    machineState(String outputText) {
        this.outputText = outputText;
    }
}
public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String userInput = "";
        Machine machine = new Machine();

        while (!userInput.equals("exit")) {
            machine.userDisplay();
            userInput = input.nextLine();
            machine.userChoice(userInput);
        }
    }
}
     class Machine {
        private int water;
        private int milk;
        private int coffeeBeans;
        private int cups;
        private int money;
        machineState state = machineState.CHOOSING_ACTION;


         public Machine() {
            water = 400;
            milk = 540;
            coffeeBeans = 120;
            cups = 9;
            money = 550;
        }

        public void userDisplay() {
             System.out.printf(state.outputText);
        }

        public void userChoice(String userInput) {
             if (state.equals(machineState.BUY)) {
                if (userInput.equals("back")) {
                    state = machineState.CHOOSING_ACTION;
                    System.out.printf("%n");
                    return;
                } else {
                    int selection = Integer.parseInt(userInput);
                    buyCoffee(selection);

                }
                state = machineState.CHOOSING_ACTION;
                return;
            }
            if (state.equals(machineState.CHOOSING_ACTION)) {
                switch (userInput) {
                    case "buy":
                        state = machineState.BUY;
                        break;
                    case "fill":
                        state = machineState.FILL_WATER;
                        break;
                    case "take":
                        take();
                        break;
                    case "remaining":
                        displayContents();
                        break;
                    case "exit":
                        break;
                }
                return;
            }

            int selection = Integer.parseInt(userInput);
            if (state.equals(machineState.FILL_WATER)) {
                water += selection;
                state = machineState.FILL_MILK;
                return;
            }
            if (state.equals(machineState.FILL_MILK)) {
                milk += selection;
                state = machineState.FILL_COFFEE_BEANS;
                return;
            }
            if (state.equals(machineState.FILL_COFFEE_BEANS)) {
                coffeeBeans += selection;
                state = machineState.FILL_CUPS;
                return;
            }
            if (state.equals(machineState.FILL_CUPS)) {
                cups += selection;
                state = machineState.CHOOSING_ACTION;
                System.out.println();
            }
        }

    private void displayContents() {
        System.out.printf("%n");
        System.out.printf("The coffee machine has: %n");
        System.out.printf("%d of water %n", water);
        System.out.printf("%d of milk %n", milk);
        System.out.printf("%d of coffee beans %n", coffeeBeans);
        System.out.printf("%d of disposable cups %n", cups);
        System.out.printf("$%d of money %n", money);
        System.out.printf("%n");
    }

    private void buyCoffee(int selection) {
        int waterUsed = 0;
        int milkUsed = 0;
        int coffeeBeansUsed = 0;
        int costOfItem = 0;
        int cupUsed = 1;
        boolean canMakeItem = true;
        String notEnoughIngredient = "";

        switch (selection) {
            case 1:
                waterUsed = 250;
                coffeeBeansUsed = 16;
                costOfItem = 4;
                break;
            case 2:
                waterUsed = 350;
                milkUsed = 75;
                coffeeBeansUsed = 20;
                costOfItem = 7;
                break;
            case 3:
                waterUsed = 200;
                milkUsed = 100;
                coffeeBeansUsed = 12;
                costOfItem = 6;
                break;
        }
        if (water < waterUsed) {
            canMakeItem = false;
            notEnoughIngredient = "water";
        } else if (milk < milkUsed) {
            canMakeItem = false;
            notEnoughIngredient = "milk";
        } else if (coffeeBeans < coffeeBeansUsed) {
            canMakeItem = false;
            notEnoughIngredient = "coffee beans";
        } else if (cups < 1) {
            canMakeItem = false;
            notEnoughIngredient = "disposable cups";
        }

        if (!canMakeItem) {
            System.out.printf("Sorry, not enough %s! %n", notEnoughIngredient);
            System.out.printf("%n");
            return;
        }

        water -= waterUsed;
        milk -= milkUsed;
        coffeeBeans -= coffeeBeansUsed;
        cups -= cupUsed;
        money += costOfItem;
        System.out.printf("I have enough resources, making you a coffee!%n");
        System.out.printf("%n");

    }

    public void take() {
        System.out.printf("I gave you $%d %n", money);
        System.out.printf("%n");
        money = 0;
    }

}



