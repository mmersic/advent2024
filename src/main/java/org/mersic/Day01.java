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
        List<Integer> l1 = new ArrayList<Integer>();
        List<Integer> l2 = new ArrayList<Integer>();
        
        lines.stream().map(x->x.split(" +")).forEach(x->{
            l1.add(Integer.parseInt(x[0]));
            l2.add(Integer.parseInt(x[1]));
        });
        
        Collections.sort(l1);
        Collections.sort(l2);

        int sumDistance = 0;
        for (int i = 0; i < l1.size(); i++) {
            int d = l1.get(i) - l2.get(i);
            if (d < 0) {
                d *= -1;
            }
            sumDistance += d;
        }
        
        int sumSimScore = 0;
        int j = 0;
        for (int i = 0; i < l1.size();) {
            int count = 0;
            for (; j < l1.size(); j++) { 
                if (l2.get(j) < l1.get(i)) {
                    continue;
                } else if (Objects.equals(l2.get(j), l1.get(i))) {
                    count++;
                } else {
                    break;
                }
            }
            if (count > 0) {
                int num = l1.get(i);
                int k = i;
                for (; k < l1.size() && num == l1.get(k); k++) {
                    sumSimScore += (l1.get(i) * count);
                }
                i = k;
            } else {
                i++;
            }
        }
        
        System.out.println("Day 1 part 1: " + sumDistance);
        System.out.println("Day 1 part 2: " + sumSimScore);
    }
    
}
