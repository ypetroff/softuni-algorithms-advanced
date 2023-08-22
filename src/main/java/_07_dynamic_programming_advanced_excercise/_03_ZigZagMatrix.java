package _07_dynamic_programming_advanced_excercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class _03_ZigZagMatrix {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int rows = Integer.parseInt(reader.readLine());
        int cols = Integer.parseInt(reader.readLine());

        int[][] matrix = new int[rows][cols];

        for(int row = 0; row < rows; row++) {
            matrix[row] = Arrays.stream(reader.readLine().split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        int[][] dp = new int[rows][cols];
        int[][] prevs = new int[rows][cols];

        for(int row = 1; row < rows; row++) {
            dp[row][0] = matrix[row][0];
        }

        for(int col = 1; col < cols; col++) {
            for(int row = 0; row < rows; row++) {
                int prevMax = 0;

                if (col % 2 != 0) {
                   for(int i = row + 1; i < rows; i++) {
                       if (dp[i][col - 1] > prevMax) {
                           prevMax = dp[i][col - 1];
                           prevs[row][col] = i;
                       }
                   }
                } else {
                    for(int i = 0; i < row; i++) {
                        if (dp[i][col - 1] > prevMax) {
                            prevMax = dp[i][col - 1];
                            prevs[row][col] = i;
                        }
                    }
                }
                dp[row][col] = prevMax + matrix[row][col];
            }
        }

        List<Integer> result = new ArrayList<>();

        int index = cols - 1;
        int rowIndex = 0;
        int max = -1;

        for(int row = 0; row < dp.length; row++) {
            if (dp[row][index] > max) {
                rowIndex = row;
                max = dp[row][index];
            }
        }

        while (index >= 0) {
            result.add(matrix[rowIndex][index]);
            rowIndex = prevs[rowIndex][index];
            index--;
            }

        Collections.reverse(result);

        System.out.println(result.stream()
                                 .mapToInt(Integer::intValue)
                .sum() + " = " + result.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" + ")));
        }
    }
