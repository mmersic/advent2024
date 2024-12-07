package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day02 {

    public static boolean safe(List<Integer> levels) {

        boolean increasing = false;
        int diff = levels.get(0) - levels.get(1);
        if (diff == 0 || Math.abs(diff) >= 4) {
            return false;
        } else if (diff < 0) {
            increasing = true;
        }

        for (int i = 1; i < levels.size() - 1; i++) {
            diff = levels.get(i) - levels.get(i + 1);
            if (diff < 0 && diff >= -3 && increasing) {
                continue;
            } else if (diff > 0 && diff <= 3 && !increasing) {
                continue;
            } else {
                return false;
            }
        }
        
        return true;
    }

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Path.of(Day02.class.getClassLoader().getResource("day.02.input").toURI()));

        int partOneSafeCount = 0;
        int partTwoSafeCount = 0;
        for (String line : lines) {
            List<Integer> levels = Arrays.stream(line.split(" ")).map(Integer::parseInt).collect(Collectors.toCollection(ArrayList<Integer>::new));
            if (safe(levels)) {
                partOneSafeCount++;
            } else {
                for (int i = 0; i < levels.size(); i++) {
                    int rem = levels.remove(i);
                    if (safe(levels)) {
                        partTwoSafeCount++;
                        break;
                    }
                    levels.add(i, rem);
                }
            }
        }
        partTwoSafeCount += partOneSafeCount;
        
        
        System.out.println("Day 2 part 1: " + partOneSafeCount);
        System.out.println("Day 2 part 2: " + partTwoSafeCount);
    }
}
