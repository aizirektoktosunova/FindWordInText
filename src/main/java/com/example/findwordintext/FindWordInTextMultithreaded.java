package com.example.findwordintext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FindWordInTextMultithreaded  {
    public static void main(String[] args) {
        String wordToFind = "Пьер";
        String textFileName = "src/main/resources/bookFile/war_and_peace.ru.txt";
        InputStream inputStream = FindWordInTextMultithreaded.class.getClassLoader().getResourceAsStream(textFileName);

        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Файл не найден: " + textFileName);
        }
        int numThreads = 5;
        List<Future<List<Integer>>> results = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(textFileName))) {
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);
            List<String> lines = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            int chunkSize = lines.size() / numThreads;

            for (int i = 0; i < numThreads; i++) {
                int start = i * chunkSize;
                int end = (i == numThreads - 1) ? lines.size() : (i + 1) * chunkSize;
                List<String> chunk = new ArrayList<>(lines.subList(start, end));
                FindWordTask task = new FindWordTask(chunk, wordToFind, start + 1);
                results.add(executor.submit(task));
            }

            executor.shutdown();

            for (Future<List<Integer>> result : results) {
                List<Integer> indices = result.get();
                for (Integer index : indices) {
                    System.out.println("Слово '" + wordToFind + "' найдено на строке: " + index);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



