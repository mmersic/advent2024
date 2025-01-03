package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day15 {
    
    record Point(int x, int y) {
        public Point add(Point move) {
            return new Point(x + move.x, y + move.y);
        }
    }
    
    static Map<Integer, Point> deltas = new HashMap<>();
    
    static {
        deltas.put((int) '^', new Point(0, -1));
        deltas.put((int) 'v', new Point(0, 1));
        deltas.put((int) '<', new Point(-1, 0));
        deltas.put((int) '>', new Point(1, 0));
    }

    public static void print(char[][] grid) {
        for(int y = 0; y < grid.length; y++) {
            System.out.println(new String(grid[y]).replace('.',' '));
        }
    }

    private static boolean tryMovePartOne(char[][] grid, Point item, Point move) {
        Point dest = item.add(move);
        if (grid[dest.y][dest.x] == '.') { //can move this item to empty space.
            grid[dest.y][dest.x] = grid[item.y][item.x];
            grid[item.y][item.x] = '.';
            return true;
        } else if (grid[dest.y][dest.x] == '#') {
            return false; //can't move the walls.
        } else {
            boolean didMove = tryMovePartOne(grid, item.add(move), move); //try to move whatever is blocking this item.
            if (didMove) {
                grid[dest.y][dest.x] = grid[item.y][item.x];
                grid[item.y][item.x] = '.';
                return true;
            } else {
                return false;
            }
        }
    }

    private static int calcPartOne(char[][] grid) {
        int partOne = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] == 'O') {
                    partOne += y*100 + x;
                }
            }
        }
        return partOne;
    }
    
    private static int getPartOne(char[][] grid, char[] moves) {
        Point robot = null;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] == '@') {
                    robot = new Point(x, y);
                }
            }
        }

        for (int i = 0; i < moves.length; i++) {
            Point move = deltas.get((int) moves[i]);
            if (move == null) continue;
            boolean didMove = tryMovePartOne(grid, robot, move);
            if (didMove) {
                robot = robot.add(move);
            }
        }

        int partOne = calcPartOne(grid);
        return partOne;
    }

    private static char[][] gridPartTwo(char[][] grid) {
        char[][] G = new char[grid.length][grid[0].length*2];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                int xx = x*2;
                switch (grid[y][x]) {
                    case 'O': {
                        G[y][xx] =   '[';
                        G[y][xx+1] = ']';
                        break;
                    }
                    case '.': {
                        G[y][xx] =   '.';
                        G[y][xx+1] = '.';
                        break;
                    }
                    case '#': {
                        G[y][xx] =   '#';
                        G[y][xx+1] = '#';
                        break;
                    }
                    case '@': {
                        G[y][xx] =   '@';
                        G[y][xx+1] = '.';
                        break;
                    }
                }
            }
        }
        return G;
    }
    
    private static boolean allSpaces(char[][] grid, Set<Point> items) {
        for (Point item : items) {
            if (grid[item.y][item.x] != '.') {
                return false;
            }
        }
        return true;
    }

    private static boolean anyWalls(char[][] grid, Set<Point> items) {
        for (Point item : items) {
            if (grid[item.y][item.x] == '#') {
                return true;
            }
        }
        return false;
    }
    
    private static boolean moveBoxes(char[][] grid, Set<Point> items, Point move) {
        if (allSpaces(grid, items)) {
            return true;
        }
        if (anyWalls(grid, items)) {
            return false;
        }
        
        Set<Point> nextRow = new HashSet<>();
        for (Point item : items) {
            Point dest = item.add(move);
            if (grid[dest.y][dest.x] == '[') {
                nextRow.add(item.add(move));
                nextRow.add(item.add(new Point(1,0)).add(move));
            } else if (grid[dest.y][dest.x] == ']') {
                nextRow.add(item.add(move));
                nextRow.add(item.add(new Point(-1,0)).add(move));
            } else if (grid[dest.y][dest.x] == '.') {
                //do nothing
            } else {
                nextRow.add(dest);
            }
        }
        
        boolean didMove = moveBoxes(grid, nextRow, move);
        if (didMove) {
            for (Point item : items) {
                Point d = item.add(move);
                grid[d.y][d.x] = grid[item.y][item.x];
                grid[item.y][item.x] = '.';
            }
            return true;
        } else {
            return false;
        }
    }

    private static boolean tryMovePartTwo(char[][] grid, Point item, Point move) {
        if (grid[item.y][item.x] == '@' || move.y == 0) {
            Point dest = item.add(move);
            if (grid[dest.y][dest.x] == '.') {
                grid[dest.y][dest.x] = grid[item.y][item.x];
                grid[item.y][item.x] = '.';
                return true;
            } else if (grid[dest.y][dest.x] == '#') {
                return false;
            } else {
                boolean didMove = tryMovePartTwo(grid, item.add(move), move);
                if (didMove) {
                    grid[dest.y][dest.x] = grid[item.y][item.x];
                    grid[item.y][item.x] = '.';
                    return true;
                } else{
                    return false;
                }
            }
        } else {
            boolean left = grid[item.y][item.x] == '[';
            Set<Point> items = new HashSet<>();
            Point item2 = left ? item.add(new Point(1, 0)) : item.add(new Point(-1, 0));
            items.add(item);
            items.add(item2);
            return moveBoxes(grid, items, move);
        }
    }

    private static int calcPartTwo(char[][] grid) {
        int partTwo = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] == '[') {
                    partTwo += y*100 + x;
                }
            }
        }
        
        return partTwo;
    }


    private static int getPartTwo(char[][] grid, char[] moves) {
        Point robot = null;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] == '@') {
                    robot = new Point(x, y);
                }
            }
        }

        for (int i = 0; i < moves.length; i++) {
            Point move = deltas.get((int) moves[i]);
            if (move == null) continue;
            boolean didMove = tryMovePartTwo(grid, robot, move);
            if (didMove) {
                robot = robot.add(move);
            }
        }
        
        return calcPartTwo(grid);
    }



    public static void main(String[] args) throws Exception {
        String[] input = Files.readString(Path.of(Day15.class.getClassLoader().getResource("day.15.input").toURI())).split("\n\n");
        String[] map = input[0].split("\n");
        char[] moves = input[1].toCharArray();
        char[][] grid = Arrays.stream(map).map(String::toCharArray).toArray(char[][]::new);

        char[][] gridPartTwo = gridPartTwo(grid);
        
        int partOne = getPartOne(grid, moves);
        int partTwo = getPartTwo(gridPartTwo, moves);
        
        System.out.println("Day 15 part one: " + partOne);
        System.out.println("Day 15 part two: " + partTwo);
    }
}
