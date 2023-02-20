import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AddToys {
  public static void addNewToy() {
    Scanner scanner = new Scanner(System.in, "CP866");
    File file = new File("toys.csv");

    int lastID = 0;
    double totalPercent = 0; 

    try (Scanner fileScanner = new Scanner(file)) {
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] fields = line.split(",");
            lastID = Integer.parseInt(fields[0]);
            totalPercent += Double.parseDouble(fields[3].replace("%", ""));
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    int newID = lastID + 1;

    System.out.print("\u001B[35mВведите название новой игрушки: \u001B[0m");
    String name = scanner.nextLine();

    int quantity = 0;
    boolean validQuantity = false;
    while (!validQuantity) {
        System.out.print("\u001B[35mВведите количество новой игрушки: \u001B[0m");
        if (scanner.hasNextInt()) {
            quantity = scanner.nextInt();
            validQuantity = true;
        } else {
            scanner.next();
            System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
            System.out.println("\u001B[31mНеверный ввод. Пожалуйста, введите число!\u001B[0m");
            System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
        }
    }
    
    double percent = 0;
    boolean validPercent = false;
    while (!validPercent) {
        System.out.print("\u001B[35mВведите процент выпадания новой игрушки (разделитель ','): \u001B[0m");
        if (scanner.hasNextDouble()) {
            percent = scanner.nextDouble();
            if (percent >= 0.01 && percent <= 100 - totalPercent) {
                validPercent = true;
            } else {
                System.out.println("\n" + String.format("%s", "\u001B[31m-".repeat(98)));
                System.out.println("Общий процент выпадения игрушек превышает 100%,\nизмените процент выпадания игрушки от 0,01 до " + (100 - totalPercent) + "\nили поменяйте у других игрушек!");
                System.out.println(String.format("%s", "-".repeat(98) + "\u001B[0m"));
                return;
            }
        } else {
            scanner.next();
            System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
            System.out.println("\u001B[31mНеверный ввод. Пожалуйста, введите число!\u001B[0m");
            System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
        }
    }


    String formattedPercent = percent + "%";
    try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8, true)) {
        writer.write(String.format("%d,%s,%d,%s%n", newID, name, quantity, formattedPercent));
        System.out.println("\nНовая игрушка добавлена в список.");
        System.out.println(String.format("%s", "-".repeat(98)));
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
