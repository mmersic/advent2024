package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;

public class Day13 {
    
    public static int solve(String[] Q) {
        String[] eq1 = Q[0].replaceAll("[:,+]", " ").replace("  ", " ").split(" ");
        String[] eq2 = Q[1].replaceAll("[:,+]", " ").replace("  ", " ").split(" ");
        String[] eq3 = Q[2].replaceAll("[:,+=]", " ").replace("  ", " ").split(" ");

        int a = Integer.parseInt(eq1[3]);
        int b = Integer.parseInt(eq2[3]);
        int c = Integer.parseInt(eq1[5]);
        int d = Integer.parseInt(eq2[5]);
        int d1 = Integer.parseInt(eq3[2]);
        int d2 = Integer.parseInt(eq3[4]);
        
        int detA = a*d - c*b;
        
        if (detA != 0) {
            int detX1 = (d1*d-d2*b);
            int detX2 = (a*d2-c*d1);
            if (detX1 % detA != 0 || detX2 % detA != 0) {
                return 0;
            }
            int x1 = detX1 / detA;
            int x2 = detX2 / detA;
            if (x1 <= 100 && x2 <= 100 && x1 >= 0 && x2 >= 0) {
                return 3 * x1 + x2;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
        
    }

    public static long solvePart2(String[] Q) {
        String[] eq1 = Q[0].replaceAll("[:,+]", " ").replace("  ", " ").split(" ");
        String[] eq2 = Q[1].replaceAll("[:,+]", " ").replace("  ", " ").split(" ");
        String[] eq3 = Q[2].replaceAll("[:,+=]", " ").replace("  ", " ").split(" ");

        long a = Integer.parseInt(eq1[3]);
        long b = Integer.parseInt(eq2[3]);
        long c = Integer.parseInt(eq1[5]);
        long d = Integer.parseInt(eq2[5]);
        long d1 = Integer.parseInt(eq3[2]) + 10000000000000l;
        long d2 = Integer.parseInt(eq3[4]) + 10000000000000l;

        long detA = a*d - c*b;

        if (detA != 0) {
            long detX1 = (d1*d-d2*b);
            long detX2 = (a*d2-c*d1);
            if (detX1 % detA != 0 || detX2 % detA != 0) {
                return 0;
            }
            long x1 = detX1 / detA;
            long x2 = detX2 / detA;
            if (x1 >= 0 && x2 >= 0) {
                return 3 * x1 + x2;
            } else {
                return 0;
           }
        } else {
            return 0;
        }

    }
    
    public static void main(String[] args) throws Exception{
        String input = Files.readString(Path.of(Day08.class.getClassLoader().getResource("day.13.input").toURI()));
        String[] problems = input.split("\n\n");
        
        int part1 = 0;
        long part2 = 0;
        for (String problem : problems) {
            String[] I = problem.split("\n");
            part1 += solve(I);
            part2 += solvePart2(I);
        }
        
        System.out.println("Day 13 part 1: " + part1);
        System.out.println("Day 13 part 2: " + part2);
    }
}
