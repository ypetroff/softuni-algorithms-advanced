package _03_Exercise_Graphs_BellmanFord_Longest_Path_in_DAG;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.PriorityQueue;

public class _03_MostReliablePath {
  private static double[][] graph;
  private static boolean[] visited;
  private static double[] reliability;

  private static int[] prev;

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int nodes = Integer.parseInt(reader.readLine().split("\\s+")[1]);
    String[] tokens = reader.readLine().split("\\s+");
    int startNode = Integer.parseInt(tokens[1]);
    int endNode = Integer.parseInt(tokens[3]);
    int edges = Integer.parseInt(reader.readLine().split("\\s+")[1]);

    graph = new double[nodes][nodes];
    visited = new boolean[nodes];
    reliability = new double[nodes];
    prev = new int[nodes];

    Arrays.fill(reliability, Double.MIN_VALUE);
    reliability[startNode] = 1;

    Arrays.fill(prev, -1);

    fillGraph(reader, edges);

    dijkstraSafestRoad(startNode);

    ArrayDeque<Integer> stack = constructPath(endNode);

    System.out.printf("Most reliable path reliability: %.2f%%%n", reliability[endNode] * 100);

    StringBuilder sb = new StringBuilder();

    formatPath(sb, stack);

    System.out.println(sb);
  }

  private static void formatPath(StringBuilder sb, ArrayDeque<Integer> stack) {
    while (!stack.isEmpty()) {
      sb.append(stack.pop()).append(" -> ");
    }

    sb.setLength(sb.length() - 4);
  }

  private static ArrayDeque<Integer> constructPath(int node) {
    ArrayDeque<Integer> stack = new ArrayDeque<>();

    while (node != -1) {
      stack.push(node);
      node = prev[node];
    }

    return stack;
  }

  private static void dijkstraSafestRoad(int startNode) {
    PriorityQueue<Integer> pq =
        new PriorityQueue<>((f, s) -> Double.compare(reliability[s], reliability[f]));

    pq.offer(startNode);

    while (!pq.isEmpty()) {
      int parentNode = pq.poll();

      visited[parentNode] = true;

      if (reliability[parentNode] == Double.MIN_VALUE) {
        break;
      }

      double[] children = graph[parentNode];

      for (int childNode = 0; childNode < children.length; childNode++) {
        if (children[childNode] != 0 && !visited[childNode]) {

          double newReliabilityScore = reliability[parentNode] * children[childNode];

          if (newReliabilityScore > reliability[childNode]) {
            reliability[childNode] = newReliabilityScore;
            prev[childNode] = parentNode;
          }

          pq.offer(childNode);
        }
      }
    }
  }

  private static void fillGraph(BufferedReader reader, int edges) throws IOException {
    String[] tokens;
    for (int i = 0; i < edges; i++) {
      tokens = reader.readLine().split("\\s+");

      graph[Integer.parseInt(tokens[0])][Integer.parseInt(tokens[1])] =
          Double.parseDouble(tokens[2]) / 100;

      graph[Integer.parseInt(tokens[1])][Integer.parseInt(tokens[0])] =
          Double.parseDouble(tokens[2]) / 100;
    }
  }
}
