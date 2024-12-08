package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day05 {

    static class PageComparator implements Comparator<Integer> {
        private final Map<Integer, Set<Integer>> map;

        public PageComparator(Map<Integer, Set<Integer>> map) {
            this.map = map;
        }

        @Override
        public int compare(Integer i1, Integer i2) {
            if (map.get(i1).contains(i2)) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    
    private static final Set<Integer> emptySet = new LinkedHashSet<>();
    private static boolean valid(Map<Integer, Set<Integer>> map, List<Integer> pages) {
        for (int i = 0; i < pages.size()-1; i++) {
            if (!map.getOrDefault(pages.get(i), emptySet).contains(pages.get(i+1))) {
                return false;
            }
        }
        return true;
    }

    public static int partOneMiddle(Map<Integer, Set<Integer>> map, List<Integer> pages) {
        if (valid(map, pages)) {
            return pages.get(pages.size()/2);
        } else {
            return 0;
        }
    }

    public static int partTwoMiddle(Map<Integer, Set<Integer>> map, List<Integer> pages) {
        if (valid(map, pages)) {
            return 0;
        } else {
            pages.sort(new PageComparator(map));
            return pages.get(pages.size()/2);
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
            List<Integer> pages = Arrays.stream(pageString.split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
            partOneSum += partOneMiddle(map, pages);
            partTwoSum += partTwoMiddle(map, pages);
        }

        System.out.println("Day 5 part 1: " + partOneSum);
        System.out.println("Day 5 part 2: " + partTwoSum);
    }
}
