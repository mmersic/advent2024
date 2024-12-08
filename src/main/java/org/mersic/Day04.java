package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day04 {
    
    //XMAS in any orientation
    public static int partOneXmasAt(char[][] grid, int y, int x) {
        int xmasCount = 0;
        
        try {
            if (grid[y][x] == 'X' && grid[y][x+1] == 'M' && grid[y][x+2] == 'A' && grid[y][x+3] == 'S') {
                xmasCount++;
            }
        } catch (Exception e) {}
        try {
            if (grid[y][x] == 'X' && grid[y][x-1] == 'M' && grid[y][x-2] == 'A' && grid[y][x-3] == 'S') {
                xmasCount++;
            }
        } catch (Exception e) {}
        try {
            if (grid[y][x] == 'X' && grid[y-1][x] == 'M' && grid[y-2][x] == 'A' && grid[y-3][x] == 'S') {
                xmasCount++;
            }
        } catch (Exception e) {}
        try {
            if (grid[y][x] == 'X' && grid[y+1][x] == 'M' && grid[y+2][x] == 'A' && grid[y+3][x] == 'S') {
                xmasCount++;
            }
        } catch (Exception e) {}
        try {
            if (grid[y][x] == 'X' && grid[y+1][x+1] == 'M' && grid[y+2][x+2] == 'A' && grid[y+3][x+3] == 'S') {
                xmasCount++;
            }
        } catch (Exception e) {}
        try {
            if (grid[y][x] == 'X' && grid[y-1][x-1] == 'M' && grid[y-2][x-2] == 'A' && grid[y-3][x-3] == 'S') {
                xmasCount++;
            }
        } catch (Exception e) {}
        try {
            if (grid[y][x] == 'X' && grid[y+1][x-1] == 'M' && grid[y+2][x-2] == 'A' && grid[y+3][x-3] == 'S') {
                xmasCount++;
            }
        } catch (Exception e) {}
        try {
            if (grid[y][x] == 'X' && grid[y-1][x+1] == 'M' && grid[y-2][x+2] == 'A' && grid[y-3][x+3] == 'S') {
                xmasCount++;
            }
        } catch (Exception e) {}
        
        return xmasCount;
    }

    //M M
    // A
    //S S
    //Spelled forwards or backwards with A at position (x, y).
    public static int partTwoXmasAt(char[][] grid, int y, int x) {
        try {
            if (grid[y][x] == 'A') {
                if ((grid[y - 1][x - 1] == 'M' && grid[y + 1][x + 1] == 'S') || (grid[y - 1][x - 1] == 'S' && grid[y + 1][x + 1] == 'M')) {
                    if ((grid[y - 1][x + 1] == 'M' && grid[y + 1][x - 1] == 'S') || (grid[y - 1][x + 1] == 'S' && grid[y + 1][x - 1] == 'M')) {
                        return 1;
                    }
                }
            }
        } catch (Exception e) {}
        
        return 0;
    }

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Path.of(Day04.class.getClassLoader().getResource("day.04.input").toURI()));
        char[][] grid = new char[lines.size()][lines.getFirst().length()];
        
        int xlen = lines.getFirst().length();
        int ylen = lines.size();
        for (int i = 0; i < ylen; i++) {
            for (int j = 0; j < xlen; j++) {
                grid[i][j] = lines.get(i).charAt(j);                
            }
        }
        
        int partOneXmasCount = 0;
        int partTwoXmasCount = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                partOneXmasCount += partOneXmasAt(grid, i, j);
                partTwoXmasCount += partTwoXmasAt(grid, i, j);
            }
        }

        System.out.println("Day 4 part 1: " + partOneXmasCount);
        System.out.println("Day 4 part 2: " + partTwoXmasCount);
    }
}
