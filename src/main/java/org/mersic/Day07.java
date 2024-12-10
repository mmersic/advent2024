package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day07 {
    
    public static long solve(long lhs, long total, long[] rhs, int ri, int ops) {
        if (lhs == total && rhs.length == ri) {
            return total;
        } else if (rhs.length == ri) {
            return 0;
        } else if (total > lhs) {
            return 0;
        } else {
            long result = solve(lhs, total + rhs[ri], rhs, ri+1, ops);
            if (result == lhs) {
                return lhs;
            }
            result = solve(lhs, total * rhs[ri], rhs, ri+1, ops);
            if (result == lhs) {
                return lhs;
            }
            if (ops == 3) {
                return solve(lhs, Long.parseLong(total + "" + rhs[ri]), rhs, ri + 1, ops);
            } else {
                return 0;
            }
        }
    }
    
    public static void main(String[] args) throws Exception{
        List<String> input = Files.readAllLines(Path.of(Day07.class.getClassLoader().getResource("day.07.input").toURI()));
        long start = System.currentTimeMillis();
        long partOne = 0;
        long partTwo = 0;
        for (String line : input) {
            String[] S = line.split(": ");
            long lhs = Long.parseLong(S[0]);
            long[] rhs = Arrays.stream(S[1].split(" ")).mapToLong(Long::parseLong).toArray();
            partOne += solve(lhs, rhs[0], rhs, 1, 2);
            partTwo += solve(lhs, rhs[0], rhs, 1, 3);
        }
        long end = System.currentTimeMillis();
        
        System.out.println("Day 7 part 1: " + partOne);
        System.out.println("Day 7 part 2: " + partTwo);
        System.out.println("Day 7 time: " + (end - start) + " ms");
    }
}
