package regular_exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class eshop {

  private static final Map<Integer, String[]> MAPPED_ITEMS = new HashMap<>();
  private static int[][] pairs;

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int numberOfItems = Integer.parseInt(reader.readLine());

    for (int i = 1; i <= numberOfItems; i++) {
      String[] tokens = reader.readLine().split("\\s+");
      MAPPED_ITEMS.put(i, tokens);
    }

    int numberOfPairs = Integer.parseInt(reader.readLine());

    pairs = new int[numberOfItems + 1][numberOfItems + 1];

    for (int i = 0; i < numberOfPairs; i++) {
      String[] pair = reader.readLine().split("\\s+");
      int firstItemIndex = getId(pair[0]);
      int secondItemIndex = getId(pair[1]);
      pairs[firstItemIndex][secondItemIndex] = 1;
      pairs[secondItemIndex][firstItemIndex] = 1;
    }

    int capacity = Integer.parseInt(reader.readLine());

    int[][] dp = new int[MAPPED_ITEMS.size() + 1][capacity + 1];

    fillDp(capacity, dp);

    Set<String> addedItems = backtracking(capacity, dp);

    StringJoiner joiner = new StringJoiner(System.lineSeparator());
    addedItems.forEach(joiner::add);
    System.out.println(joiner);
  }

  private static Set<String> backtracking(int capacity, int[][] dp) {
    Set<String> addedItems = new TreeSet<>(String::compareTo);
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(MAPPED_ITEMS.size());

    while (!stack.isEmpty()) {
      int index = stack.pop();

      int weight = Integer.parseInt(MAPPED_ITEMS.get(index)[1]);
      int value = Integer.parseInt(MAPPED_ITEMS.get(index)[2]);

      if (dp[index][capacity] == value + dp[index - 1][capacity - weight]) {
        addedItems.add(MAPPED_ITEMS.get(index)[0]);
        capacity -= weight;
      }

      if (index > 1) {
        stack.push(index - 1);
      }
    }

    return addedItems;
  }

  private static void fillDp(int capacity, int[][] dp) {
    for (int i = 1; i <= MAPPED_ITEMS.size(); i++) {
      for (int j = 1; j <= capacity; j++) {
        int weight = getTotalWeight(i);
        if (weight <= j) {
          int value = Integer.parseInt(MAPPED_ITEMS.get(i)[2]);
          dp[i][j] = Math.max(value + dp[i - 1][j - weight], dp[i - 1][j]);
        } else {
          dp[i][j] = dp[i - 1][j];
        }
      }
    }
  }

  private static int getTotalWeight(int itemIndex) {
    int weightOfPairedItems = 0;

    for (int i = 1; i < pairs[itemIndex].length; i++) {
      if (pairs[itemIndex][i] == 1) {

        weightOfPairedItems += Integer.parseInt(MAPPED_ITEMS.get(i)[1]);
      }
    }
    int weightOfCurrentItem = Integer.parseInt(MAPPED_ITEMS.get(itemIndex)[1]);
    return weightOfPairedItems + weightOfCurrentItem;
  }

  private static int getId(String pairedItem) {
    return MAPPED_ITEMS.entrySet().stream()
        .filter(item -> item.getValue()[0].equals(pairedItem))
        .map(Map.Entry::getKey)
        .findFirst()
        .orElse(-1);
  }
}
