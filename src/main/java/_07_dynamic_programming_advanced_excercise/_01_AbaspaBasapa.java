package _07_dynamic_programming_advanced_excercise;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class _01_AbaspaBasapa {

  private static int[][] dp;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    String first = scanner.nextLine();
    String second = scanner.nextLine();

    dp = new int[first.length()][second.length()];

    int bestRow = -1;
    int bestCol = -1;
    int bestLength = -1;

    for (int firstIndex = 0; firstIndex < first.length(); firstIndex++) {
      for (int secondIndex = 0; secondIndex < second.length(); secondIndex++) {
        if (first.charAt(firstIndex) == second.charAt(secondIndex)) {
          dp[firstIndex][secondIndex] = getPrev(firstIndex, secondIndex) + 1;
        }

        if (dp[firstIndex][secondIndex] > bestLength) {
          bestLength = dp[firstIndex][secondIndex];
          bestRow = firstIndex;
          bestCol = secondIndex;
        }
      }
    }

    Deque<Character> result = new ArrayDeque<>();

    while (bestRow >= 0 && bestCol >= 0 && dp[bestRow][bestCol] != 0) {
      result.push(first.charAt(bestRow));
      bestRow--;
      bestCol--;
    }

    while (!result.isEmpty()) {
      System.out.print(result.pop());
    }
  }

  private static int getPrev(int firstIndex, int secondIndex) {
    if (firstIndex - 1 < 0 || secondIndex - 1 < 0) {
      return 0;
    }
    return dp[firstIndex - 1][secondIndex - 1];
  }
}
