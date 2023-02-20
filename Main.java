import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> menuItems = new ArrayList<>();
        menuItems.add("Отобразите весь список игрушек");
        menuItems.add("Отобразить список разыгранных игрушек");
        menuItems.add("Добавьте новую игрушку в список");
        menuItems.add("Удалить игрушку из списка");
        menuItems.add("Изменить данные игрушки");
        menuItems.add("Начать игру");
        menuItems.add("Выход");

        while (true) {
            System.out.println("\n\u001B[32m==== МЕНЮ ПРИЛОЖЕНИЯ ====\u001B[0m\n");
            for (int i = 0; i < menuItems.size(); i++) {
                System.out.println((i + 1) + ". " + menuItems.get(i));
            }

            int choice = 0;
            boolean validInput = false;
            while (!validInput) {
                System.out.print("\n\u001B[35mВведите число от 1 до " + menuItems.size() + ":\u001B[0m ");

                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    validInput = (choice >= 1 && choice <= menuItems.size());
                } else {
                    scanner.next();
                }
                if (!validInput) {
                    System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
                    System.out.println("\u001B[31mНеверный ввод. Пожалуйста, введите целое число от 1 до " + menuItems.size() + ".\u001B[0m");
                    System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
                }
            }
            scanner.nextLine();

            String menuItem = menuItems.get(choice - 1);
            System.out.println("\n\u001B[36mВы выбрали: " + menuItem + "\u001B[0m");
            System.out.println(String.format("%s", "-".repeat(98)));

            if (menuItem.equals("Выход")) {
                System.out.println("\u001B[31mВыход из приложения\u001B[0m");
                System.exit(0);
            }

            switch (menuItem) {
                case "Отобразите весь список игрушек":
                    OutputTablesToys.displayToyList();
                    break;
                case "Отобразить список разыгранных игрушек":
                    OutputTablesToys.displayParticipatingToys();
                    break;
                case "Добавьте новую игрушку в список":
                    AddToys.addNewToy();
                    break;
                case "Удалить игрушку из списка":
                    OutputTablesToys.displayToyList();
                    RemoveToy.removeToyById();
                    break;
                case "Изменить данные игрушки":
                    try {
                        ToyModifier.modifyToyById();
                    } catch (IOException e) {
                        System.out.println("Произошла ошибка при изменении данных: " + e.getMessage());
                    }
                    break;
                case "Начать игру":
                    Game.start();
                    break;
            }
        }
    }
}
