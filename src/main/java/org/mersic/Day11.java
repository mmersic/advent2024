package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 {
    
    record Memo(long l, int blinks) {}
    
    public static long stones(long l, Map<Memo, Long> map, int blinks) {
        if (blinks == 0) {
            return 1;
        }
        
        Memo key = new Memo(l, blinks);
        if (map.containsKey(key)) {
            return map.get(key);
        }
        
        long S = -1;
        if (l == 0) {
            S = stones(1L, map, blinks - 1);
        } else {
            String s = Long.toString(l);
            if (s.length() % 2 == 0) {
                String left = s.substring(0, s.length() / 2);
                String right = s.substring(s.length() / 2);
                S = stones(Long.parseLong(left), map, blinks - 1) + stones(Long.parseLong(right), map, blinks - 1);
            } else {
                S = stones(l*2024, map, blinks - 1);
            }
        }

        map.put(key, S);
        
        return S;
    }
    
    public static void main(String[] args) throws Exception {
        List<Integer> input = Arrays.stream(Files.readString(Path.of(Day09.class.getClassLoader().getResource("day.11.input").toURI()))
                .split(" ")).map(Integer::parseInt).toList();


        long start = System.currentTimeMillis();
        Map<Memo, Long> map = new HashMap<>();
        long partOne = 0;
        for (Integer I : input) {
            partOne += stones(I, map, 25);
        }
        
        long partTwo = 0;
        for (Integer I : input) {
            partTwo += stones(I, map, 75);
        }
        long end = System.currentTimeMillis();
        
        System.out.println("Time: " + (end - start));
        System.out.println("Day 11 part 1: " + partOne);
        System.out.println("Day 11 part 2: " + partTwo);
    }
}
