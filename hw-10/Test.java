import java.io.*;
import java.util.*;

public class Test {
    private static List<String> createdTestFiles = new ArrayList<>();
    
    public static void main(String[] args) {
        try {
            System.out.println("Создание тестовых файлов: ");
            createTestFile("testfile1.txt", "Перый тестовый файл.");
            createTestFile("testfile2.txt", "Второй тестовый файл.");
            createTestFile("testfile3.txt", "Третий тестовый файл.");
            createTestFile("testfile4.txt", "Четвертый тестовый файл.");
            createTestFile("testfile5.txt", "Пятый тестовый файл.");
            
            createdTestFiles.add("testfile1.txt");
            createdTestFiles.add("testfile2.txt");
            createdTestFiles.add("testfile3.txt");
            createdTestFiles.add("testfile4.txt");
            createdTestFiles.add("testfile5.txt");
            
            System.out.println("\nРабота кеша при чтении файлов");
            ClassicIOCacheWithLimit cache = new ClassicIOCacheWithLimit(10);
            
            String content1 = cache.readFile("testfile1.txt");
            System.out.println("Первое чтение testfile1.txt: " + content1);
            
            String content2 = cache.readFile("testfile1.txt");
            System.out.println("Повторное чтение testfile1.txt: " + content2);
            
            if (cache.isCached("testfile1.txt")) {
                System.out.println("Файл testfile1.txt находится в кэше");
            }
            
            System.out.println("\nДемонстрация вытеснения файлов при превышении лимита");
            ClassicIOCacheWithLimit smallCache = new ClassicIOCacheWithLimit(3);
            
            smallCache.readFile("testfile1.txt");
            smallCache.readFile("testfile2.txt");
            smallCache.readFile("testfile3.txt");
            
            System.out.println("Кеш после добавления 3 файлов:");
            smallCache.printCacheStats();
            
            smallCache.readFile("testfile4.txt");
            System.out.println("Кеш после добавления 4-го файла (лимит - 3):");
            smallCache.printCacheStats();
            
            if (!smallCache.isCached("testfile1.txt")) {
                System.out.println("testfile1.txt был вытеснен из кеша");
            }
            
            System.out.println("\nСравнение производительности чтения с диска и из кэша");
            
            long startTime = System.nanoTime();
            String diskContent = cache.readFile("testfile2.txt");
            long diskTime = System.nanoTime() - startTime;
            
            startTime = System.nanoTime();
            String cacheContent = cache.readFile("testfile2.txt");
            long cacheTime = System.nanoTime() - startTime;
            
            System.out.printf("Время чтения с диска: %,d нс%n", diskTime);
            System.out.printf("Время чтения из кеша: %,d нс%n", cacheTime);
            System.out.printf("Ускорение: %.1f раз%n", (double) diskTime / cacheTime);
            
        } catch (Exception e) {
            System.err.println("Ошибка при тестировании: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("\nУдаление тестовых файлов");
            int deletedCount = 0;
            for (String filename : createdTestFiles) {
                File file = new File(filename);
                if (file.exists() && file.delete()) {
                    deletedCount++;
                    System.out.println("Удален файл: " + filename);
                }
            }
            System.out.println("Всего удалено файлов: " + deletedCount);
        }
    }
    
    private static void createTestFile(String filename, String content) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
        }
        System.out.println("Создан файл: " + filename);
    }
    
}