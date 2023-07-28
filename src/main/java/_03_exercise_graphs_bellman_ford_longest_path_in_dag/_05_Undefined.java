package _03_exercise_graphs_bellman_ford_longest_path_in_dag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;

public class _05_Undefined {
  private static int[][] graph;
  private static int[] prev;
  private static int[] distance;

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int nodes = Integer.parseInt(reader.readLine());
    int edges = Integer.parseInt(reader.readLine());

    graph = new int[nodes + 1][nodes + 1];
    prev = new int[graph.length];
    distance  = new int[graph.length];

    Arrays.fill(prev, - 1);
    Arrays.fill(distance, Integer.MAX_VALUE);

    fillGraph(reader, edges);

    int startNode = Integer.parseInt(reader.readLine());
    int endNode = Integer.parseInt(reader.readLine());

    distance[startNode] = 0;

    boolean negativeCycle = bellmanFord();

    if (negativeCycle) {
      System.out.println("Undefined");
      return;
    }

    ArrayDeque<Integer> path = new ArrayDeque<>();

    path.push(endNode);

    int node = prev[endNode];

    while (node != -1) {
     path.push(node);
     node = prev[node];
    }

    StringBuilder sb = new StringBuilder();

    while (!path.isEmpty()) {
      sb.append(path.pop()).append(" ");
    }

    System.out.println(sb.toString().trim());
    System.out.println(distance[endNode]);
  }

  private static void fillGraph(BufferedReader reader, int edges) throws IOException {
    for (int i = 0; i < edges; i++) {
      int[] tokens =
          Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

      graph[tokens[0]][tokens[1]] = tokens[2];
    }
  }

  private static boolean bellmanFord() {
    for (int i = 1; i < graph.length - 1; i++) {
      for (int row = 1; row < graph.length; row++) {
        for (int col = 1; col < graph.length; col++) {
          int w = graph[row][col];
          if (w == 0) {
            continue;
          }
          if (distance[row] != Integer.MAX_VALUE) {
            int newValue = distance[row] + w;
            if (newValue < distance[col]) {
              distance[col] = newValue;
              prev[col] = row;
            }
          }
        }
      }
    }
    for (int row = 1; row < graph.length; row++) {
      for (int col = 1; col < graph.length; col++) {
        int w = graph[row][col];
        if (w != 0) {
          if (distance[row] != Integer.MAX_VALUE) {
            int newValue = distance[row] + w;
            if (newValue < distance[col]) {
              return true;
            }
          }
        }
      }
    }

    return false;
  }
}
