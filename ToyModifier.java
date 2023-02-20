import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ToyModifier {
    public static void modifyToyById() throws IOException {
        Scanner scanner = new Scanner(System.in, "CP866");
        File file = new File("toys.csv");
        boolean found = false;

        System.out.print("\n\u001B[35mВведите ID игрушки, которую вы хотите изменить: \u001B[0m");
        String id = scanner.nextLine().trim();

        double totalPercentage = 0.0;
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] fields = line.split(",");
                totalPercentage += Double.parseDouble(fields[3].replace("%", ""));
            }
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] fields = line.split(",");

                if (fields[0].equals(id)) {
                    found = true;
                    System.out.println("\nНайдена игрушка: \nID: " + fields[0] + "\nНазвание игрушки: " + fields[1] + "\nКоличество игрушек: " + fields[2] + "\nПроцент выпадания: " + fields[3]);
                    System.out.print("\n\u001B[35mВведите новое название игрушки (можно оставить пустым): \u001B[0m");
                    String name = scanner.nextLine().trim();
                    if (name.isEmpty()) {
                        name = fields[1];
                    }

                    String quantity = "";
                    while (true) {
                        System.out.print("\n\u001B[35mВведите новое количество игрушек (можно оставить пустым): \u001B[0m");
                        quantity = scanner.nextLine().trim();
                        if (quantity.isEmpty()) {
                            quantity = fields[2];
                            break;
                        }
                        try {
                            int quantityInt = Integer.parseInt(quantity);
                            if (quantityInt < 1) {
                                System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
                                System.out.println("\u001B[31mОшибка: количество игрушек должно быть положительным числом!\u001B[0m");
                                System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
                            } else {
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
                            System.out.println("\u001B[31mОшибка: количество игрушек должно быть целым числом!\u001B[0m");
                            System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
                        }
                    }

                    String percent = "";
                    while (true) {
                        System.out.print("\n\u001B[35mВведите процент розыгрыша (можно оставить пустым): \u001B[0m");
                        percent = scanner.nextLine().trim();
                        if (percent.isEmpty()) {
                            percent = fields[3];
                            break;
                        }
                        try {
                            double percentDouble = Double.parseDouble(percent);
                            if (percentDouble <= 0.0) {
                                System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
                                System.out.println("\u001B[31mОшибка: процент розыгрыша должен быть положительным числом!\u001B[0m");
                                System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));

                            } else if (percentDouble > 100.0 - totalPercentage + Double.parseDouble(fields[3].replace("%", ""))) {
                                System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
                                System.out.println("\u001B[31mОшибка: общая сумма процентов не должна превышать 100%!\u001B[0m");
                                System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));

                            } else {
                                break;
                        }
                        } catch (NumberFormatException e) {
                            System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
                            System.out.println("\u001B[31mОшибка: процент розыгрыша должен быть числом или разделитель '.'!\u001B[0m");
                            System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
                        }
                    }

                    String modifiedLine = id + "," + name + "," + quantity + "," + percent + "%";
                    replaceLineInFile(file, line, modifiedLine);
                    System.out.println("\nИгрушка успешно изменена!");
                    System.out.println(String.format("%s", "-".repeat(98)));
                    break;
              }
            }
        }

        if (!found) {
            System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
            System.out.println("\u001B[31mИгрушка не найдена!\u001B[0m");
            System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
        }
    }

    private static void replaceLineInFile(File file, String oldLine, String newLine) throws IOException {
        String fileContent = "";
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals(oldLine)) {
                    fileContent += newLine + System.lineSeparator();
                } else {
                    fileContent += line + System.lineSeparator();
                }
            }
        }
        FileWriter writer = new FileWriter(file);
        writer.write(fileContent);
        writer.close();
    }
}

