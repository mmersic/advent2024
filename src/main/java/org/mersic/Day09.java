package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day09 {
    
    public static long partOneChecksum(int[] disk) {
        int diskLength = disk.length;
        long checkSum = 0;
        for (int backIndex = diskLength - 1, forwardIndex = 0; backIndex >= 0; backIndex--) {
            int v = disk[backIndex];
            while (disk[forwardIndex] != -1) {
                checkSum += (forwardIndex * disk[forwardIndex]);
                forwardIndex++;
            }
            if (forwardIndex >= backIndex) {
                break;
            }
            disk[forwardIndex] = v;
            disk[backIndex] = -1;
        }
        
        return checkSum;
    }

    public static long partTwoChecksum(int[] disk) {
        int sizeNeeded = 0;
        int fileId = -1;
        for (int backIndex = disk.length - 1; backIndex >= 0; backIndex--) {
            if (disk[backIndex] != -1) {
                if (fileId == -1) {
                    fileId = disk[backIndex];
                    sizeNeeded++;
                    continue;
                } else if (fileId == disk[backIndex]) {
                    sizeNeeded++;
                    continue;
                } 
            }
            
            if (sizeNeeded == 0) {
                continue;
            }
            
            loop: for (int forwardIndex = 0; forwardIndex+sizeNeeded < disk.length && forwardIndex < backIndex; forwardIndex++) {
                if (disk[forwardIndex] == -1) {
                    for (int i = 0; i < sizeNeeded && forwardIndex+i < disk.length; i++) {
                        if (disk[forwardIndex+i] != -1) {
                            forwardIndex += i;
                            continue loop;
                        }
                    }
                    for (int i = 0; i < sizeNeeded; i++) {
                        disk[forwardIndex+i] = fileId;
                        disk[backIndex+1+i] = -1; 
                    }
                    break loop;
                }
            }
            
            backIndex++;
            sizeNeeded = 0;
            fileId = -1;
        }
        
        long checkSum = 0;
        for (int i = 0; i < disk.length; i++) {
            if (disk[i] != -1) {
                checkSum += disk[i] * i;
            }
        }
        
        return checkSum;
    }
    
    public static void main(String[] args) throws Exception{
        String input = Files.readString(Path.of(Day09.class.getClassLoader().getResource("day.09.input").toURI()));
        char[] diskMap = input.toCharArray();

        int diskLength = 0;
        for (char c : diskMap) {
            diskLength += c - '0';
        }
        
        int[] diskPart1 = new int[diskLength];
        int[] diskPart2 = new int[diskLength];
        
        boolean fill = false;
        int index = 0;
        int fileId = 0;
        for (char c : diskMap) {
            int  v = c - '0';
            fill = !fill;
            int fillValue = fill ? fileId++ : -1;
            for (int j = 0; j < v; j++, index++) {
                diskPart1[index] = fillValue;
                diskPart2[index] = fillValue;
            }
        }
        
        long partOneChecksum = partOneChecksum(diskPart1);
        long partTwoChecksum = partTwoChecksum(diskPart2);
        
        System.out.println("Day 9 part 1: " + partOneChecksum);
        System.out.println("Day 9 part 2: " + partTwoChecksum);
    }
}
