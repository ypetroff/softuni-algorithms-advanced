package _02_graphs_bellman_ford_longest_path_in_dag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;

public class _02_Longest_Path_in_DAG {
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

    getEdges(reader, e);

    int s = Integer.parseInt(reader.readLine()); // source/start node
    distances[s] = 0;
    int d = Integer.parseInt(reader.readLine()); // destination/end node;

    ArrayDeque<Integer> stack = new ArrayDeque<>();

    for (int i = 1; i < graph.length; i++) {
      topologicalSorting(i, stack);
    }

    while (!stack.isEmpty()) {
      int node = stack.pop();

      for (int i = 1; i < graph[node].length; i++) {
        int weight = graph[node][i];
        if (weight != 0 && distances[node] != Integer.MIN_VALUE) {
          if (distances[node] + weight > distances[i]) {
            distances[i] = distances[node] + weight;
          }
        }
      }
    }

    System.out.println(distances[d]);
  }

  private static void topologicalSorting(int node, ArrayDeque<Integer> stack) {
    if (visited[node]) {
      return;
    }

    visited[node] = true;

    for (int i = 1; i < graph[node].length; i++) {
      if (graph[node][i] != 0) {
        topologicalSorting(i, stack);
      }
    }

    stack.push(node);
  }

  private static void getEdges(BufferedReader reader, int e) throws IOException {
    for (int i = 0; i < e; i++) {
      int[] tokens =
          Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

      int u = tokens[0]; // source vertex
      int v = tokens[1]; // destination vertex
      int w = tokens[2]; // weight of the edge

      graph[u][v] = w;
    }
  }
}
