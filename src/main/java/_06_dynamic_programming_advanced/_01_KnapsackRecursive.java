package _06_dynamic_programming_advanced;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class _01_KnapsackRecursive {

  private static final List<Integer> weights = new ArrayList<>();
  private static final List<Integer> prices = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int capacity = Integer.parseInt(reader.readLine());

    String line = reader.readLine();

    while (!line.equals("end")) {
      String[] tokens = line.split("\\s+");

      weights.add(Integer.parseInt(tokens[1]));
      prices.add(Integer.parseInt(tokens[2]));

      line = reader.readLine();
    }

    int result = recurrence(0, 0, capacity);

    System.out.println(result);
  }

  private static int recurrence(int valueIndex, int weightIndex, int capacity) {
    if (valueIndex >= prices.size()
        || weightIndex >= weights.size()
        || weights.get(weightIndex) > capacity) {
      return 0;
    }

    return Math.max(
        recurrence(valueIndex + 1, weightIndex + 1, capacity),
        recurrence(valueIndex + 1, weightIndex + 1, capacity - weights.get(weightIndex))
            + prices.get(valueIndex));
  }
}
