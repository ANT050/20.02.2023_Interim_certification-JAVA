import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RemoveToy {

    public static void removeToyById() {
        Scanner scanner = new Scanner(System.in);
        List<String[]> records = new ArrayList<>();
        String id = "";
        boolean validInput = false;

        try (BufferedReader br = new BufferedReader(new FileReader("toys.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("\n\u001B[35mВведите ID игрушки, которую нужно удалить: \u001B[0m");
        while (!validInput) {
            id = scanner.nextLine();
            if (id.matches("\\d+")) {
                validInput = true;
            } else {
                System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
                System.out.print("\u001B[31mНеверный ввод. Пожалуйста, введите число!\n\u001B[0m");
                System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
                System.out.print("\n\u001B[35mВведите ID игрушки, которую нужно удалить: \u001B[0m");
            }
        }

        boolean removed = false;
        for (int i = 0; i < records.size(); i++) {
            String[] record = records.get(i);
            if (record[0].equals(id)) {
                records.remove(i);
                removed = true;
                break;
            }
        }

        if (removed) {
            List<String[]> updatedRecords = new ArrayList<>();
            int newId = 1;
            for (String[] record : records) {
                if (Integer.parseInt(record[0]) != newId) {
                    record[0] = String.valueOf(newId);
                }
                updatedRecords.add(record);
                newId++;
            }
            try (FileWriter writer = new FileWriter(new File("toys.csv"))) {
                for (String[] record : updatedRecords) {
                    writer.write(String.join(",", record) + "\n");
                }
                System.out.println("\nИгрушка с ID " + id + " успешно удалена!");
                System.out.println(String.format("%s", "-".repeat(98)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
            System.out.println("\u001B[31mИгрушка с ID " + id + " не найдена!\u001B[0m");
            System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
        }
    }
}
