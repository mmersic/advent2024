package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day12 {

    record Point(int x, int y) {
        public Point add(Point other) {
            return new Point(other.x() + x, other.y() + y);
        }
        
    }
    
    record Region(int area, int perimeter) {
    }

    static Region ZERO = new Region(0, 0);

    public static Region visitPartOne(char[][] grid, int y, int x, char veg, Set<Point> visited) {
        if (grid[y][x] != veg) {
            return ZERO;
        }

        Point point = new Point(x, y);
        if (visited.contains(point)) {
            return ZERO;
        }

        visited.add(point);
        int totalArea = 1;
        int totalPerimeter = 0;

        if (x > 0 && grid[y][x - 1] != veg) {
            totalPerimeter++;
        } else if (x > 0) {
            Region R = visitPartOne(grid, y, x - 1, veg, visited);
            totalArea += R.area;
            totalPerimeter += R.perimeter;
        } else {
            totalPerimeter++;
        }
        if (y > 0 && grid[y - 1][x] != veg) {
            totalPerimeter++;
        } else if (y > 0) {
            Region R = visitPartOne(grid, y - 1, x, veg, visited);
            totalArea += R.area;
            totalPerimeter += R.perimeter;
        } else {
            totalPerimeter++;
        }
        if (x < grid[0].length - 1 && grid[y][x + 1] != veg) {
            totalPerimeter++;
        } else if (x < grid[0].length - 1) {
            Region R = visitPartOne(grid, y, x + 1, veg, visited);
            totalArea += R.area;
            totalPerimeter += R.perimeter;
        } else {
            totalPerimeter++;
        }
        if (y < grid.length - 1 && grid[y + 1][x] != veg) {
            totalPerimeter++;
        } else if (y < grid.length - 1) {
            Region R = visitPartOne(grid, y + 1, x, veg, visited);
            totalArea += R.area;
            totalPerimeter += R.perimeter;
        } else {
            totalPerimeter++;
        }

        return new Region(totalArea, totalPerimeter);
    }


    private static boolean[] borders(char[][] grid, int x, int y, char veg) {
        int UP = 0;
        int DOWN = 1;
        int LEFT = 2;
        int RIGHT = 3;
        boolean[] nextDirs = new boolean[4];

        if (x == 0 || (x > 0 && grid[y][x - 1] != veg)) {
            nextDirs[LEFT] = true;
        }
        if (x == grid[0].length -1 || (x < grid[0].length - 1 && grid[y][x + 1] != veg)) {
            nextDirs[RIGHT] = true;
        }
        if (y == grid.length - 1 || (y < grid.length - 1 && grid[y + 1][x] != veg)) {
            nextDirs[DOWN] = true;
        }
        if (y == 0 || (y > 0 && grid[y - 1][x] != veg)) {
            nextDirs[UP] = true;
        }

        return nextDirs;
    }
    
    static Point dUP = new Point(0, -1);
    static Point dDOWN = new Point(0, 1);
    static Point dLEFT = new Point(-1, 0);
    static Point dRIGHT = new Point(1, 0);
    
    static Point[] deltas = new Point[] { dUP, dDOWN, dLEFT, dRIGHT };

    static class PointComparator implements Comparator<Point> {
        @Override
        public int compare(Point A, Point B) {
            if (A.x < B.x) {
                return -1;
            } else if (A.x > B.x) {
                return 1;
            } else if (A.y < B.y) {
                return -1;
            } else if (A.y > B.y) {
                return 1;
            } else {
                return 0;
            }
        }
    }    
    
    private static final PointComparator pointComparator = new PointComparator();
    
    public static Region visitPartTwo(char[][] grid, int x, int y, Set<Point> allVisited) {
        char veg = grid[y][x];
        Set<Point> visited = new HashSet<>();
        int perimeterTotal = 0;
        
        List<Point> list = new ArrayList<>();
        list.add(new Point(x, y));
        
        while (!list.isEmpty()) {
            list.sort(pointComparator);
            Point here = list.removeFirst();
            
            if (visited.contains(here)) {
                continue;
            }
            
            for (int i = 0; i < 4; i++) {
                Point other = here.add(deltas[i]);
                try {
                    if (grid[other.y][other.x] == veg && !visited.contains(other)) {
                        list.add(other);
                    }
                } catch (Exception e) {}
            }
            
            visited.add(here);
            allVisited.add(here);
                    
            boolean[] hereBorders = borders(grid, here.x, here.y, veg);
            for (int i = 0; i < 4; i++) {
                 Point therePoint = here.add(deltas[i]);                        
                 if (visited.contains(therePoint)) {
                    boolean[] thereBorders = borders(grid, therePoint.x, therePoint.y, veg);
                    for (int m = 0; m < 4; m++) {
                         if (thereBorders[m]) {
                             hereBorders[m] = false;
                         }
                    }
                 }
            }

            for (int k = 0; k < 4; k++) {
                if (hereBorders[k]) {
                    perimeterTotal += 1;
                }
            }
        }
        
        return new Region(visited.size(), perimeterTotal);
    }
    
    public static void main(String[] args) throws Exception {
        char[][] grid = Files.readAllLines(Path.of(Day08.class.getClassLoader().getResource("day.12.input").toURI()))
                .stream().map(String::toCharArray).toArray(char[][]::new);

        Set<Point> partOneVisited = new HashSet<>();
        Set<Point> partTwoVisited = new HashSet<>();

        int partOneScore = 0;
        int partTwoScore = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                Point point = new Point(x, y);
                if (!partOneVisited.contains(point)) {
                    Region R = visitPartOne(grid, y, x, grid[y][x], partOneVisited);
                    partOneScore += (R.area * R.perimeter);
                }
                if (!partTwoVisited.contains(point)) {
                    Region R = visitPartTwo(grid, x, y, partTwoVisited);
                    partTwoScore += (R.area * R.perimeter);
                }
            }
        }
        
        System.out.println("Day 12 part 1: " + partOneScore);
        System.out.println("Day 12 part 2: " + partTwoScore);
    }

}

