package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day14 {
    
    record Point(int x, int y) {}

    private static int getPart1(List<String> input) {
        int[] q = {0, 0, 0, 0};

        int maxX = 100;
        int maxY = 102;
        for (String line : input) {
            String[] split = line.split("[=, ]");

            Point p = new Point(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
            Point v = new Point(Integer.parseInt(split[4]), Integer.parseInt(split[5]));

            int nextX = p.x;
            int nextY = p.y;

            for (int i = 0; i < 100; i++) {
                nextX += v.x;
                nextY += v.y;
                if (nextX < 0) nextX += (maxX+1);
                if (nextY < 0) nextY += (maxY+1);
                if (nextX >= maxX+1) nextX -= (maxX+1);
                if (nextY >= maxY+1) nextY -= (maxY+1);
            }

            if (nextX < maxX/2 && nextY < maxY/2) { q[0]++; }
            else if (nextX > maxX/2 && nextY < maxY/2) { q[1]++; }
            else if (nextX < maxX/2 && nextY > maxY/2) { q[2]++; }
            else if (nextX > maxX/2 && nextY > maxY/2) { q[3]++; }

        }

        int part1 = (q[0]*q[1]*q[2]*q[3]);
        return part1;
    }
    
    
    static class Robot {
        private final int vx;
        private final int vy;
        private int y;
        private int x;

        public Robot(int x, int y, int vx, int vy) {
            this.x = x;
            this.y = y;
            this.vy = vy;
            this.vx = vx;
        }
    }
    
    public static int tick(List<Robot> robots, int[][] grid) {
        int border = 0;
        for (Robot robot : robots) {
            grid[robot.y][robot.x]--;
            int nextX = robot.x + robot.vx;
            int nextY = robot.y + robot.vy;
            if (nextX < 0) nextX += (101);
            if (nextY < 0) nextY += (103);
            if (nextX >= 101) nextX -= 101;
            if (nextY >= 103) nextY -= 103;
            robot.x = nextX;
            robot.y = nextY;
            grid[robot.y][robot.x]++;
            if (robot.y == 79 || robot.y == 47) {
                border++;
            }
        }
        
        return border;
    }
    
    public static void print(int[][] grid) {
        for(int y = 0; y < grid.length; y++) {
            if (y < 10) {
                System.out.print('0');
            }
            System.out.print(y);
            for(int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] > 0) {
                    System.out.print('*');
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
    }
    
    public static int getPart2(List<String> input) throws Exception {
        List<Robot> robots = new ArrayList<>();
        int[][] grid = new int[103][101];
        
        for (String line : input) {
            String[] split = line.split("[=, ]");
            Robot R = new Robot(Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[4]), Integer.parseInt(split[5]));
            robots.add(R);
            grid[R.y][R.x]++;
        }
        
        int tick = 0 ;
        
        while (true) {
            int border = tick(robots, grid);
            tick++;
            if (border > 60) {
                print(grid);
                System.out.println("tick: " + tick);
                Thread.sleep(100);
            }
            if (tick > 10000000) {
                break;
            }
        }
        
        return -1;
    }
    
    
    public static void main(String[] args) throws Exception {
        List<String> input = Files.readAllLines(Path.of(Day14.class.getClassLoader().getResource("day.14.input").toURI()));
        
        int part1 = getPart1(input);
        int part2 = getPart2(input);
        
        
        System.out.println("Day 14 part 1: " + part1);
        System.out.println("Day 14 part 2: " + part2);
    }


}

