import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private static final String TOY_LIST = "toys.csv";
    private static final String PARTICIPATING_TOYS = "played.csv";

    public static void start() {
        List<String> toys = readToysFromFile();
        if (toys.isEmpty()) {
            System.out.println("\u001B[31mСписок игрушек пуст!\u001B[0m");
            System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
            return;
        }

        String selectedToy = selectRandomToy(toys);
        System.out.println("Выбрана игрушка: " + selectedToy);

        writePlayedToyToFile(selectedToy);
    }

    private static List<String> readToysFromFile() {
      List<String> toys = new ArrayList<>();
      try (BufferedReader reader = new BufferedReader(new FileReader(TOY_LIST))) {
          String line;
          while ((line = reader.readLine()) != null) {
              String[] toyData = line.split(",");
              int quantity = Integer.parseInt(toyData[2]);
              if (quantity > 0) {
                  toys.add(line);
              }
          }
      } catch (IOException e) {
          System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
          System.out.println("\u001B[31mОшибка при чтении файла " + TOY_LIST + ": " + e.getMessage() + "\u001B[0m");
          System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
      }
      return toys;
    }

    private static String selectRandomToy(List<String> toys) {
        Random random = new Random();
        double totalProbability = 0.0;
        for (String toy : toys) {
            totalProbability += Double.parseDouble(toy.split(",")[3].replaceAll("%", "")) / 100.0;
        }
        double randomValue = random.nextDouble() * totalProbability;
        double accumulatedProbability = 0.0;
        for (String toy : toys) {
            double toyProbability = Double.parseDouble(toy.split(",")[3].replaceAll("%", "")) / 100.0;
            accumulatedProbability += toyProbability;
            if (accumulatedProbability >= randomValue) {
                return toy.split(",")[1];
            }
        }
        return null;
    }

    private static void writePlayedToyToFile(String toy) {
      List<String> toys = readToysFromFile();
      boolean toyFound = false;
      for (int i = 0; i < toys.size(); i++) {
          String[] toyData = toys.get(i).split(",");
          if (toyData[1].equals(toy.split(",")[0])) {
              int quantity = Integer.parseInt(toyData[2]) - 1;
              toys.set(i, toyData[0] + "," + toyData[1] + "," + quantity + "," + toyData[3]);
              toyFound = true;
              break;
          }
      }

      if (!toyFound) {
          System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
          System.out.println("\u001B[31mОшибка: выбранная игрушка не найдена в файле " + TOY_LIST + "\u001B[0m");
          System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
          return;
      }

      try (FileWriter writer = new FileWriter(TOY_LIST)) {
          for (String toyData : toys) {
              writer.write(toyData + "\n");
          }
      } catch (IOException e) {
          System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
          System.out.println("\u001B[31mОшибка при записи файла " + TOY_LIST + ": " + e.getMessage() + "\u001B[0m");
          System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
          return;
      }

      try (BufferedReader reader = new BufferedReader(new FileReader(PARTICIPATING_TOYS))) {
          List<String> playedToys = new ArrayList<>();
          String line;
          boolean toyFound2 = false;
          while ((line = reader.readLine()) != null) {
              String[] playedToyData = line.split(",");
              if (playedToyData[1].equals(toy.split(",")[0])) {
                  int quantity = Integer.parseInt(playedToyData[2]) + 1;
                  line = playedToyData[0] + "," + playedToyData[1] + "," + quantity;
                  toyFound2 = true;
              }
              playedToys.add(line);
          }

          if (!toyFound2) {
              int id = 0;
              for (String playedToy : playedToys) {
                  String[] playedToyData = playedToy.split(",");
                  id = Math.max(id, Integer.parseInt(playedToyData[0]));
              }
              int quantity = 1;
              String name = toy.split(",")[0];
              String playedToyEntry = (id + 1) + "," + name + "," + quantity;
              playedToys.add(playedToyEntry);
          }

          try (FileWriter writer = new FileWriter(PARTICIPATING_TOYS)) {
              for (String playedToy : playedToys) {
                  writer.write(playedToy + "\n");
              }
              System.out.println("Выбранная игрушка записана в файл: " + PARTICIPATING_TOYS);
              System.out.println(String.format("%s", "-".repeat(98)));
          } catch (IOException e) {
              System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
              System.out.println("\u001B[31mОшибка при записи файла " + PARTICIPATING_TOYS + ": " + e.getMessage() + "\u001B[0m");
              System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
              return;
          }
      } catch (IOException e) {
          System.out.println("\n" + String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
          System.out.println("\u001B[31mОшибка при чтении файла " + PARTICIPATING_TOYS + ": " + e.getMessage() + "\u001B[0m");
          System.out.println(String.format("%s", "\u001B[31m-\u001B[0m".repeat(98)));
      }
    }
}
