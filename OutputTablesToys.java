import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class OutputTablesToys {
    private static final String TOY_LIST = "toys.csv";
    private static final String PARTICIPATING_TOYS = "played.csv";
	

    // Функция считывания и вывода данных из файла "toys.csv"
    public static void displayToyList() {
        try (BufferedReader br = new BufferedReader(new FileReader(TOY_LIST, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] toyData = line.split(",");
                System.out.println(toyData[0] + ". " + toyData[1] + ": " + toyData[2] + "шт. " + toyData[3]);
                System.out.println(String.format("%s", "-".repeat(98)));
            }
        } catch (IOException e) {
            System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
        }
    }

    // Функция считывания и вывода данных из файла "played.csv"
    public static void displayParticipatingToys() {
        try (BufferedReader br = new BufferedReader(new FileReader(PARTICIPATING_TOYS, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] toyData = line.split(",");
                System.out.println(toyData[0] + ". " + toyData[1] + ": " + toyData[2] + "шт. ");
                System.out.println(String.format("%s", "-".repeat(98)));
            }
        } catch (IOException e) {
            System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
        }
    }   
}
