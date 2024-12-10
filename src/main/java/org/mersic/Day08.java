package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Day08 {
    
    record Point(int x, int y) {}

    public static Set<Point> partTwoAntinodes(Map<Integer, LinkedHashSet<Point>> aMap, char[][] grid, boolean partOne) {
        Set<Point> antinodes = new HashSet<>();

        for (int k : aMap.keySet()) {
            if (!partOne) {
                antinodes.addAll(aMap.get(k));
            }
            Point[] P = aMap.get(k).toArray(new Point[0]);
            for (int i = 0; i < P.length; i++) {
                for (int j = i+1; j < P.length; j++) {
                    Point p1 = P[i];
                    Point p2 = P[j];
                    int x = p1.x - p2.x;
                    int y = p1.y - p2.y;
                    int offx = x;
                    int offy = y;
                    try {
                        while (grid[p1.x+offx][p1.y+offy] > 0) {
                            antinodes.add(new Point(p1.x+offx,p1.y+offy));
                            if (partOne) {
                                break;
                            }
                            offx += x;
                            offy += y;
                        }
                    } catch (Exception e) {}
                    try {
                        offx = x;
                        offy = y;
                        while (grid[p2.x+(-1*offx)][p2.y+(-1*offy)] > 0) {
                            antinodes.add(new Point(p2.x+(-1*offx),p2.y+(-1*offy)));
                            if (partOne) {
                                break;
                            }
                            offx += x;
                            offy += y;
                        }
                    } catch (Exception e) {}
                }
            }
        }
        return antinodes;
    }    
    
    public static void main(String[] args) throws Exception {
        char[][] grid = Files.readAllLines(Path.of(Day08.class.getClassLoader().getResource("day.08.input").toURI()))
                .stream().map(String::toCharArray).toArray(char[][]::new);
        
        Map<Integer, LinkedHashSet<Point>> aMap = new HashMap<>();
        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != '.') {
                    LinkedHashSet<Point> set = aMap.getOrDefault((int)grid[i][j], new LinkedHashSet<>());
                    set.add(new Point(j, i));
                    aMap.put((int) grid[i][j], set);
                }
            }
        }

        long start = System.currentTimeMillis();
        int partOne = partTwoAntinodes(aMap, grid, true).size();
        int partTwo = partTwoAntinodes(aMap, grid, false).size();
        long end = System.currentTimeMillis();
        
        System.out.println("Day 08 part 1: " + partOne);
        System.out.println("Day 08 part 2: " + partTwo);
        System.out.println("Time taken: " + (end - start) + "ms");
    }
}
