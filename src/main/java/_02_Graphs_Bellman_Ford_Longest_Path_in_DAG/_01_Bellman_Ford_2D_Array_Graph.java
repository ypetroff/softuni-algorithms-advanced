package _02_Graphs_Bellman_Ford_Longest_Path_in_DAG;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class _01_Bellman_Ford_2D_Array_Graph {

  private static int[][] graph;
  private static int[] prev;
  private static int[] distances;

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int v =
        Integer.parseInt(reader.readLine()); // v for vertex, this is the number of vertices/nodes
    int e = Integer.parseInt(reader.readLine()); // e for edge, this is the number of edges

    graph = new int[v + 1][v + 1];
    prev = new int[v + 1]; // or graph.length
    distances = new int[v + 1]; // or graph.length

    Arrays.fill(prev, -1);
    Arrays.fill(distances, Integer.MAX_VALUE);

    getEdges(reader, e);

    int s = Integer.parseInt(reader.readLine()); // source/start node
    int d = Integer.parseInt(reader.readLine()); // destination/end node;

    distances[s] = 0;

    bellmanFord();

    try{
      negativeCycleDetection();
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
      return;
    }

    List<Integer> path = new ArrayList<>();

    path.add(d);

    int n = prev[d]; // node

    while (n != -1) {
      path.add(n);
      n = prev[n];
    }

    Collections.reverse(path);

    System.out.println(path.stream().map(String::valueOf).collect(Collectors.joining(" ")));
    System.out.println(distances[d]);

  }

  private static void negativeCycleDetection() {
      for (int row = 1; row < graph.length; row++) {
        for (int col = 1; col < graph[row].length; col++) {
          int w = graph[row][col];
          if (w != 0) {
            if (distances[row] != Integer.MAX_VALUE) {
              int newValue = distances[row] + w;
              if (newValue < distances[col]) {
                throw new IllegalArgumentException("Negative Cycle Detected");
              }
            }
          }
        }
      }
  }

  private static void bellmanFord() {
    for (int i = 1; i < graph.length - 1; i++) {
      for (int row = 1; row < graph.length; row++) {
        for (int col = 1; col < graph[row].length; col++) {
          int w = graph[row][col];
          if (w != 0) {
            if (distances[row] != Integer.MAX_VALUE) {
              int newValue = distances[row] + w;
              if (newValue < distances[col]) {
                distances[col] = newValue;
                prev[col] = row;
              }
            }
          }
        }
      }
    }
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
