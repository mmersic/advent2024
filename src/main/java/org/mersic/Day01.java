package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Day01 {
    
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Path.of(Day01.class.getClassLoader().getResource("day.01.input").toURI()));
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        
        lines.stream().map(x->x.split(" +")).forEach(x->{
            l1.add(Integer.parseInt(x[0]));
            l2.add(Integer.parseInt(x[1]));
        });
        
        Collections.sort(l1);
        Collections.sort(l2);

        int sumDistance = getSumDistance(l1, l2);
        int sumSimScore = getSumSimScore(l1, l2);

        System.out.println("Day 1 part 1: " + sumDistance);
        System.out.println("Day 1 part 2: " + sumSimScore);
    }

    private static int getSumSimScore(List<Integer> l1, List<Integer> l2) {
        int sumSimScore = 0;
        int j = 0;
        for (int i = 0; i < l1.size();) {
            int l2EqualCount = 0;
            for (; j < l1.size(); j++) { 
                if (Objects.equals(l2.get(j), l1.get(i))) {
                    l2EqualCount++;
                } else if (l2.get(j) > l1.get(i)) {
                    break;
                }
            }
            if (l2EqualCount > 0) {
                int num = l1.get(i);
                for (; i < l1.size() && num == l1.get(i); i++) {
                    sumSimScore += (l1.get(i) * l2EqualCount);
                }
            } else {
                i++;
            }
        }
        return sumSimScore;
    }

    private static int getSumDistance(List<Integer> l1, List<Integer> l2) {
        int sumDistance = 0;
        for (int i = 0; i < l1.size(); i++) {
            int d = l1.get(i) - l2.get(i);
            if (d < 0) {
                d *= -1;
            }
            sumDistance += d;
        }
        return sumDistance;
    }

}
