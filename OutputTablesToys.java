import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class OutputTablesToys {
    private static final String TOY_LIST = "toys.csv";
    private static final String PARTICIPATING_TOYS = "played.csv";


    private static void printToyList(BufferedReader br, boolean printHeader) throws IOException {
        String line;
        if (printHeader) {
            System.out.println(String.format("%s", "-".repeat(98)));
            System.out.println(String.format("| \u001B[31m%-5s\u001B[0m | \u001B[31m%-30s\u001B[0m | \u001B[31m%-30s\u001B[0m | \u001B[31m%-20s\u001B[0m |", "ID", "Название игрушки", "Количество игрушек", "Процент выпадания"));

            System.out.println(String.format("%s", "-".repeat(98)));
        }

        while ((line = br.readLine()) != null) {
            String[] toyData = line.split(",");
            System.out.println(String.format("| %-5s | %-30s | %-30s | %-20s |", toyData[0], toyData[1], toyData[2], toyData[3]));
            System.out.println(String.format("%s", "-".repeat(98)));
        }
    }

    public static void displayToyList() {
        try (BufferedReader br = new BufferedReader(new FileReader(TOY_LIST, StandardCharsets.UTF_8))) {
            String spaces = new String(new char[22]).replace('\0', ' ');
            System.out.println("\n" + spaces + "\u001B[33m==== Список всех игрушек участвующих в розыгрыше ====\n\u001B[0m");
            printToyList(br, true);
        } catch (IOException e) {
						System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
            System.out.println("\u001B[31mОшибка чтения файла: " + e.getMessage() + "\u001B[0m");
						System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
        }
    }

    public static void displayParticipatingToys() {
        try (BufferedReader br = new BufferedReader(new FileReader(PARTICIPATING_TOYS, StandardCharsets.UTF_8))) {
            String spaces = new String(new char[31]).replace('\0', ' ');
            System.out.println("\n" + spaces + "\u001B[33m==== Список разыгранных игрушек ====\n\u001B[0m");
            printToyList(br, true);
        } catch (IOException e) {
						System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
            System.out.println("\u001B[31mОшибка чтения файла: " + e.getMessage() + "\u001B[0m");
						System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
        }
    }
}
