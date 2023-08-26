package regular_exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SocialMediaTracker {

  private static int[][] graph;
  private static boolean[] visited;
  private static int[] hops;

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int relationships = Integer.parseInt(reader.readLine());

    graph = new int[relationships][relationships];
    visited = new boolean[relationships];
    hops = new int[relationships];
    int[] influences = new int[relationships];

    Arrays.fill(influences, Integer.MIN_VALUE);
    Arrays.fill(hops, Integer.MIN_VALUE);

    Map<String, Integer> nodeToId = new HashMap<>();
    int idCounter = 0;

    for (int i = 0; i < relationships; i++) {
      String[] tokens = reader.readLine().split("\\s+");

      int source = idCounter;

      if (!nodeToId.containsKey(tokens[0])) {

        nodeToId.put(tokens[0], idCounter++);
      } else {
        source = nodeToId.get(tokens[0]);
      }

      int dest = idCounter;

      if (!nodeToId.containsKey(tokens[1])) {
        nodeToId.put(tokens[1], idCounter++);
      } else {
        dest = nodeToId.get(tokens[1]);
      }

      graph[source][dest] = Integer.parseInt(tokens[2]);
    }

    String start = reader.readLine();
    String end = reader.readLine();

    int startIndex = nodeToId.get(start);
    int endIndex = nodeToId.get(end);

    influences[startIndex] = 0;
    hops[startIndex] = 0;

    Deque<Integer> stack = new ArrayDeque<>();

    for (int i = 0; i < graph.length; i++) {
      topologicalSorting(i, stack);
    }

    while (!stack.isEmpty()) {
      int node = stack.pop();

      for (int i = 0; i < graph[node].length; i++) {
        int weight = graph[node][i];
        if (weight != 0 && influences[node] != Integer.MIN_VALUE && hops[node] != Integer.MIN_VALUE) {
          if (influences[node] + weight > influences[i] || influences[node] + weight == influences[i] && hops[node] + 1 < hops[i]) {
            influences[i] = influences[node] + weight;
            hops[i] = hops[node] + 1;
          }
        }
      }
    }
        System.out.printf("(%d, %d)", influences[endIndex], hops[endIndex]);
  }

  private static void topologicalSorting(int node, Deque<Integer> stack) {
    if (visited[node]) {
      return;
    }

    visited[node] = true;

    for (int i = 0; i < graph[node].length; i++) {
      if (graph[node][i] != 0) {
        topologicalSorting(i, stack);
      }
    }

    stack.push(node);
  }
}
