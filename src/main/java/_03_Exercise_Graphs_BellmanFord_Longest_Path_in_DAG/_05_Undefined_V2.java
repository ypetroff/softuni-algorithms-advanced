package _03_Exercise_Graphs_BellmanFord_Longest_Path_in_DAG;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class _05_Undefined_V2 {
  private static class Edge {
    private final int u;
    private final int v;
    private final int w;

    public Edge(int u, int v, int w) {
      this.u = u;
      this.v = v;
      this.w = w;
    }

    public int from() {
      return this.u;
    }

    public int to() {
      return this.v;
    }

    public int weight() {
      return this.w;
    }
  }

  private static final Map<Integer, List<Edge>> GRAPH = new HashMap<>();
  private static int[] prev;
  private static int[] distance;

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int nodes = Integer.parseInt(reader.readLine());
    int edges = Integer.parseInt(reader.readLine());



    generateGraph(nodes);

    fillGraph(reader, edges);

    int startNode = Integer.parseInt(reader.readLine());
    int endNode = Integer.parseInt(reader.readLine());

    prev = new int[GRAPH.size() + 1];
    distance = new int[GRAPH.size() + 1];

    Arrays.fill(prev, -1);
    Arrays.fill(distance, Integer.MAX_VALUE);

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

  private static void generateGraph(int nodes) {
    for (int i = 1; i < nodes + 1; i++) {
      GRAPH.put(i, new ArrayList<>());
    }
  }

  private static void fillGraph(BufferedReader reader, int edges) throws IOException {
    for (int i = 0; i < edges; i++) {
      int[] tokens =
          Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

      GRAPH.get(tokens[0]).add(new Edge(tokens[0], tokens[1], tokens[2]));
      GRAPH.get(tokens[1]).add(new Edge(tokens[0], tokens[1], tokens[2]));
    }
  }

  private static boolean bellmanFord() {
    List<Edge> edges = GRAPH.values().stream().flatMap(List::stream).collect(Collectors.toList());

    for (int i = 1; i < GRAPH.size(); i++) {
      for (Edge edge : edges) {
        int u = edge.from();
        int v = edge.to();
        int w = edge.weight();
        if (distance[u] != Integer.MAX_VALUE && distance[u] + w < distance[v]) {
          distance[v] = distance[u] + w;
          prev[v] = u;
        }
      }
      for (Edge edge : edges) {
        int u = edge.from();
        int v = edge.to();
        int w = edge.weight();
        if (distance[u] != Integer.MAX_VALUE && distance[u] + w < distance[v]) {
          return true;
        }
      }
    }
    return false;
  }
}
