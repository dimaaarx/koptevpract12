import java.io.*;
import java.util.Scanner;

public class SimpleTextEditor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Меню:");
            System.out.println("1. Записати кілька рядків у файл");
            System.out.println("2. Прочитати файл з нумерацією");
            System.out.println("3. Вивести діапазон рядків");
            System.out.println("4. Вставити в обраний рядок");
            System.out.println("5. Вихід");
            System.out.print("Виберіть дію: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> writeMultipleLines(scanner);
                case 2 -> readFromFile();
                case 3 -> printRange(scanner);
                case 4 -> insertLine(scanner);
                case 5 -> {
                    System.out.println("Вихід з програми.");
                    return;
                }
                default -> System.out.println("Невірний ввід. Спробуйте ще раз.");
            }
        }
    }

    public static void writeMultipleLines(Scanner scanner) {
        System.out.print("Скільки рядків ви хочете додати? ");
        int count = scanner.nextInt();
        scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt", true))) {
            for (int i = 0; i < count; i++) {
                System.out.print("Рядок " + (i + 1) + ": ");
                String line = scanner.nextLine();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Рядки успішно додано.");
        } catch (IOException e) {
            System.out.println("Помилка при записі у файл.");
        }
    }

    public static void readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                System.out.println(lineNumber + ": " + line);
                lineNumber++;
            }
        } catch (IOException e) {
            System.out.println("Помилка при читанні файлу.");
        }
    }

    public static void printRange(Scanner scanner) {
        System.out.print("Введіть початковий рядок: ");
        int start = scanner.nextInt();
        System.out.print("Введіть кінцевий рядок: ");
        int end = scanner.nextInt();
        scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            int currentLine = 1;

            while ((line = reader.readLine()) != null) {
                if (currentLine >= start && currentLine <= end) {
                    System.out.println(currentLine + ": " + line);
                }
                currentLine++;
            }
        } catch (IOException e) {
            System.out.println("Помилка при читанні файлу.");
        }
    }

    public static void insertLine(Scanner scanner) {
        System.out.print("Введіть номер рядка, куди вставити: ");
        int insertAt = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Введіть текст для вставки: ");
        String newText = scanner.nextLine();

        String[] lines = new String[1000];
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null && count < lines.length) {
                lines[count++] = line;
            }
        } catch (IOException e) {
            System.out.println("Помилка при читанні файлу.");
            return;
        }

        String[] updated = new String[1001];
        int newIndex = 0;
        for (int i = 0; i < count + 1; i++) {
            if (i == insertAt - 1) {
                updated[newIndex++] = newText;
            }
            if (i < count) {
                updated[newIndex++] = lines[i];
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"))) {
            for (int i = 0; i < newIndex; i++) {
                if (updated[i] != null) {
                    writer.write(updated[i]);
                    writer.newLine();
                }
            }
            System.out.println("Рядок успішно вставлено.");
        } catch (IOException e) {
            System.out.println("Помилка при записі у файл.");
        }
    }
}
