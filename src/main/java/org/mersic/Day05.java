package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day05 {

    private static final Set<Integer> emptySet = new LinkedHashSet<>();

    public static int[] reOrder(Map<Integer, Set<Integer>> map, int[] pages) {
        List<Integer> list = new ArrayList<>(Arrays.stream(pages).boxed().toList());
        int resultIndex = 0;
        next: while (!list.isEmpty()) {
            int page = list.removeFirst();
            for (int j = 0; j < list.size(); j++) {
                if (!map.getOrDefault(page, emptySet).contains(list.get(j))) {
                    list.addLast(page);
                    continue next;
                }
            }
            pages[resultIndex++] = page;
        }
        
        return pages;
    }
    
    private static boolean valid(Map<Integer, Set<Integer>> map, int[] pages) {
        for (int i = 0; i < pages.length; i++) {
            for (int j = i + 1; j < pages.length; j++) {
                if (!map.getOrDefault(pages[i], emptySet).contains(pages[j])) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int partOneMiddle(Map<Integer, Set<Integer>> map, int[] pages) {
        if (valid(map, pages)) {
            return pages[pages.length/2];
        } else {
            return 0;
        }
    }

    public static int partTwoMiddle(Map<Integer, Set<Integer>> map, int[] pages) {
        if (valid(map, pages)) {
            return 0;
        } else {
            return reOrder(map, pages)[pages.length/2];
        }
    }
    
    public static void main(String[] args) throws Exception {
        String input = Files.readString(Path.of(Day05.class.getClassLoader().getResource("day.05.input").toURI()));
        String[] section = input.split("\n\n");
        String[] ruleSection = section[0].split("\n");
        String[] pageSection = section[1].split("\n");
        
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (String rule : ruleSection) {
            String[] R = rule.split("\\|");
            int from = Integer.parseInt(R[0]);
            int to = Integer.parseInt(R[1]);
            Set<Integer> set = map.getOrDefault(from, new LinkedHashSet<>());
            set.add(to);
            map.put(from, set);
        }
        
        int partOneSum = 0;
        int partTwoSum = 0;
        for (String pageString : pageSection) {
            int[] pages = Arrays.stream(pageString.split(",")).mapToInt(Integer::parseInt).toArray();
            partOneSum += partOneMiddle(map, pages);
            partTwoSum += partTwoMiddle(map, pages);
        }
        
        System.out.println("Day 5 part 1: " + partOneSum);
        System.out.println("Day 5 part 2: " + partTwoSum);
    }
}
