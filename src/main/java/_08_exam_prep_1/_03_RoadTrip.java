package _08_exam_prep_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class _03_RoadTrip {

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int[] values =
        Arrays.stream(reader.readLine().split(", ")).mapToInt(Integer::parseInt).toArray();

    int[] spaces =
        Arrays.stream(reader.readLine().split(", ")).mapToInt(Integer::parseInt).toArray();

    int maxCapacity = Integer.parseInt(reader.readLine());

    int[][] dp = new int[values.length + 1][maxCapacity + 1];

    for (int i = 1; i <= values.length; i++) {
      for (int j = 1; j <= maxCapacity; j++) {
        if (spaces[i - 1] <= j) {
          dp[i][j] = Math.max(values[i - 1] + dp[i - 1][j - spaces[i - 1]], dp[i - 1][j]);
        } else {
          dp[i][j] = dp[i - 1][j];
        }
      }
    }
    System.out.println("Maximum value: " + dp[values.length][maxCapacity]);
  }
}
