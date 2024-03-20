package com.example.findwordintext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class FindWordTask implements Callable<List<Integer>> {

    private List<String> lines;
    private String wordToFind;
    private int startLineNumber;

    public FindWordTask(List<String> lines, String wordToFind, int startLineNumber) {
        this.lines = lines;
        this.wordToFind = wordToFind;
        this.startLineNumber = startLineNumber;
    }

    @Override
    public List<Integer> call() {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains(wordToFind)) {
                indices.add(startLineNumber + i);
            }
        }
        return indices;
    }
}
