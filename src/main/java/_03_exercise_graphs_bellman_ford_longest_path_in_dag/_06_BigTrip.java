package _03_exercise_graphs_bellman_ford_longest_path_in_dag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class _06_BigTrip {
  private static int[][] graph;
  private static boolean[] visited;

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int v =
        Integer.parseInt(reader.readLine()); // v for vertex, this is the number of vertices/nodes
    int e = Integer.parseInt(reader.readLine()); // e for edge, this is the number of edges

    graph = new int[v + 1][v + 1];
    int[] distances = new int[v + 1]; // or graph.size
    visited = new boolean[v + 1];

    Arrays.fill(distances, Integer.MIN_VALUE);

    for (int i = 0; i < e; i++) {
      int[] tokens =
          Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

      graph[tokens[0]][tokens[1]] = tokens[2];
    }

    int s = Integer.parseInt(reader.readLine()); // source/start node
    distances[s] = 0;
    int d = Integer.parseInt(reader.readLine());

    ArrayDeque<Integer> stack = new ArrayDeque<>();

    for (int i = 1; i < graph.length; i++) {
      topologicalSorting(i, stack);
    }

    int[] prev = new int[v + 1];
    Arrays.fill(prev, - 1);

    while (!stack.isEmpty()) {
      int node = stack.pop();

      for (int i = 1; i < graph[node].length; i++) {
        int weight = graph[node][i];
        if (weight != 0 && distances[node] != Integer.MIN_VALUE) {
          if (distances[node] + weight > distances[i]) {
            distances[i] = distances[node] + weight;
            prev[i] = node;
          }
        }
      }
    }

    System.out.println(distances[d]);

    List<Integer> path = new ArrayList<>();
    path.add(d);

    int current = prev[d];

    while (current != -1) {
      path.add(current);
      current = prev[current];
    }

    StringBuilder sb = new StringBuilder();

    for (int i = path.size() - 1; i >= 0; i--) {
    sb.append(path.get(i)).append(" ");
    }

    System.out.println(sb.toString().trim());
  }

  private static void topologicalSorting(int parentNode, ArrayDeque<Integer> stack) {
    if (visited[parentNode]) {
      return;
    }

    visited[parentNode] = true;

    for (int childNode = 1; childNode < graph.length; childNode++) {
      if (graph[parentNode][childNode] != 0) {
        topologicalSorting(childNode, stack);
      }
    }
    stack.push(parentNode);
  }
}
