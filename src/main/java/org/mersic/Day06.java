package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day06 {
    
    private static int walk(char[][] grid, int x, int y) {
        char facing = 'U';
        int posCount = 0;
        
        while(true) {
            if (grid[y][x] != 'X') {
                posCount++;
                grid[y][x] = 'X';
            }
            try {
                switch (facing) {
                    case 'U': {
                        if (grid[y-1][x] == '#') {
                            facing = 'R';
                        } else {
                            y--;
                        }
                        break;
                    }
                    case 'L': {
                        if (grid[y][x-1] == '#') {
                            facing = 'U';
                        } else {
                            x--;
                        }
                        break;
                    }
                    case 'R': {
                        if (grid[y][x+1] == '#') {
                            facing = 'D';
                        } else {
                            x++;
                        }
                        break;
                    }
                    case 'D': {
                        if (grid[y+1][x] == '#') {
                            facing = 'L';
                        } else {
                            y++;
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                break;
            }
        }
        
        return posCount;
    }

    private static boolean isLoop(char[][] grid, int x, int y) {
        int[] pos = new int[] { x, y, 'U', x, y, 'U' };
        int count = 0;
        while (true) {
            try {
                count++;
                for (int i = 0; i < 6; i+=3) {
                    x = pos[i];
                    y = pos[i+1];
                    switch (pos[i+2]) {
                        case 'U': {
                            if (grid[y - 1][x] == '#') {
                                pos[i+2] = 'R';
                                i-=3;
                                continue;
                            } else {
                                y--;
                            }
                            break;
                        }
                        case 'L': {
                            if (grid[y][x - 1] == '#') {
                                pos[i+2] = 'U';
                                i-=3;
                                continue;
                            } else {
                                x--;
                            }
                            break;
                        }
                        case 'R': {
                            if (grid[y][x + 1] == '#') {
                                pos[i+2] = 'D';
                                i-=3;
                                continue;
                            } else {
                                x++;
                            }
                            break;
                        }
                        case 'D': {
                            if (grid[y + 1][x] == '#') {
                                pos[i+2] = 'L';
                                i-=3;
                                continue;
                            } else {
                                y++;
                            }
                            break;
                        }
                    }
                    pos[i] = x;
                    pos[i+1] = y;
                    if (i == 0 && pos[0] == pos[3] && pos[1] == pos[4] && pos[2] == pos[5]) {
                        return true;
                    }
                    if (count % 2 == 0) {
                        break;
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }
    }
    
    private static int loopCount(char[][] grid, int x, int y) {
        int loopCount = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'X') {
                    grid[i][j] = '#';
                    if (isLoop(grid, x, y)) {
                        loopCount++;
                    }
                    grid[i][j] = 'X';
                } 
            }
        }
        
        return loopCount;
    }    
    
    public static void main(String[] args) throws Exception {
        List<String> input = Files.readAllLines(Path.of(Day06.class.getClassLoader().getResource("day.06.input").toURI()));
        
        int x = -1;
        int y = -1;
        char[][] grid = new char[input.size()][input.getFirst().length()];
        for (int i = 0; i < input.size(); i++) {
            grid[i] = input.get(i).toCharArray();
            if (input.get(i).indexOf('^') > -1) {
                x = input.get(i).indexOf('^');
                y = i;
            }
        }

        int partOne = walk(grid, x, y);
        int partTwo = loopCount(grid, x, y);
        
        System.out.println("Day 6 part 1: " + partOne);
        System.out.println("Day 6 part 2: " + partTwo);
    }
}
