package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day07 {
    
    public static long partOneSolve(long lhs, long total, long[] rhs, int ri) {
        if (lhs == total && rhs.length == ri) {
            return total;
        } else if (rhs.length == ri) {
            return 0;
        } else if (total > lhs) {
            return 0;
        } else {
            long result = partOneSolve(lhs, total + rhs[ri], rhs, ri+1);
            if (result == lhs) {
                return lhs;
            } else {
                return partOneSolve(lhs, total * rhs[ri], rhs, ri+1);
            }
        }
    }

    public static long partTwoSolve(long lhs, long total, long[] rhs, int ri) {
        if (lhs == total && rhs.length == ri) {
            return total;
        } else if (rhs.length == ri) {
            return 0;
        } else if (total > lhs) {
            return 0;
        } else {
            long result = partTwoSolve(lhs, total + rhs[ri], rhs, ri+1);
            if (result == lhs) {
                return lhs;
            } else {
                result = partTwoSolve(lhs, total * rhs[ri], rhs, ri+1);
                if (result == lhs) {
                    return lhs;
                } else {
                    return partTwoSolve(lhs, Long.parseLong(total + "" + rhs[ri]), rhs, ri+1);
                }
            }
        }
    }
    
    public static void main(String[] args) throws Exception{
        List<String> input = Files.readAllLines(Path.of(Day07.class.getClassLoader().getResource("day.07.input").toURI()));
        
        long partOne = 0;
        long partTwo = 0;

        for (String line : input) {
            String[] S = line.split(": ");
            long lhs = Long.parseLong(S[0]);
            long[] rhs = Arrays.stream(S[1].split(" ")).mapToLong(Long::parseLong).toArray();
            partOne += partOneSolve(lhs, 0, rhs, 0);
            partTwo += partTwoSolve(lhs, 0, rhs, 0);
        }

        System.out.println("Day 7 part 1: " + partOne);
        System.out.println("Day 7 part 2: " + partTwo);
    }
}
