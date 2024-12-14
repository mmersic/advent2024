package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Day10 {
    
    record Point(int x, int y) {}
    
    public static int score(char[][] grid, int y, int x, Collection<Point> nines) {
        if (grid[y][x] == '9')
        {
            nines.add(new Point(x, y));
            return nines.size();
        }
        
        int nextStep = grid[y][x] + 1;
        if (y+1 < grid.length && grid[y+1][x] == nextStep) {
            score(grid, y+1, x, nines);
        }
        if (y-1 >= 0 && grid[y-1][x] == nextStep) {
            score(grid, y-1, x, nines);
        }
        if (x+1 < grid[y].length && grid[y][x+1] == nextStep) {
            score(grid, y, x+1, nines);
        }
        if (x-1 >= 0 && grid[y][x-1] == nextStep) {
            score(grid, y, x-1, nines);
        }
        
        return nines.size();
    }
    
    public static void main(String[] args) throws Exception {
        char[][] grid = Files.readAllLines(Path.of(Day08.class.getClassLoader().getResource("day.10.input").toURI()))
                .stream().map(String::toCharArray).toArray(char[][]::new);
        
        int partOneSumTrailheadScore = 0;
        int partTwoSumTrailheadScore = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '0') {
                    partOneSumTrailheadScore += score(grid, i, j, new HashSet<>());
                    partTwoSumTrailheadScore += score(grid, i, j, new ArrayList<>());
                }
            }
        }
        
        System.out.println("Day 10 part 1: " + partOneSumTrailheadScore);
        System.out.println("Day 10 part 2: " + partTwoSumTrailheadScore);
    }
}
