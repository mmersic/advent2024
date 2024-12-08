package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {
       
    public static int evalPartOne(String input) {
        int sum = 0;

        String regex = "mul\\([0-9]+,[0-9]+\\)"; 
        Pattern pattern = Pattern.compile(regex);
        String numregex = "[0-9]+";
        Pattern numpattern = Pattern.compile(numregex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String instruction = matcher.group(0);
            Matcher nummatcher = numpattern.matcher(instruction);
            nummatcher.find();
            int op1 = Integer.parseInt(nummatcher.group(0));
            nummatcher.find();
            int op2 = Integer.parseInt(nummatcher.group(0));
            sum += op1 * op2;
        }
        
        return sum;
    }

    record EvalPartTwoResult(int sum, boolean mulEnabled) {}
    public static EvalPartTwoResult evalPartTwo(String input, boolean mulEnabled) {
        int sum = 0;
        String insRegex = "mul\\([0-9]+,[0-9]+\\)|do\\(\\)|don't\\(\\)";
        Pattern insPattern = Pattern.compile(insRegex);
        String numregex = "[0-9]+";
        Pattern numpattern = Pattern.compile(numregex);
        Matcher insMatcher = insPattern.matcher(input);
        while (insMatcher.find()) {
            String instruction = insMatcher.group(0);
            if (instruction != null && instruction.startsWith("mul")) {
                if (!mulEnabled) {
                    continue;
                }
                Matcher nummatcher = numpattern.matcher(instruction);
                nummatcher.find();
                int op1 = Integer.parseInt(nummatcher.group(0));
                nummatcher.find();
                int op2 = Integer.parseInt(nummatcher.group(0));
                sum += op1 * op2;
            } else if ("do()".equals(instruction)) {
                mulEnabled = true;
            } else if ("don't()".equals(instruction)) {
                mulEnabled = false;
            }
        }

        return new EvalPartTwoResult(sum, mulEnabled);
    }


    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Path.of(Day03.class.getClassLoader().getResource("day.03.input").toURI()));

        int part1Sum = 0;
        int part2Sum = 0;
        boolean mulEnabled = true;
        for (String line : lines) {
            part1Sum += evalPartOne(line);
            EvalPartTwoResult evalResult = evalPartTwo(line, mulEnabled);
            part2Sum += evalResult.sum();
            mulEnabled = evalResult.mulEnabled();
        }
        
        System.out.println("Day 3 part 1: " + part1Sum);
        System.out.println("Day 3 part 2: " + part2Sum);
    }
}
