package _02_graphs_bellman_ford_longest_path_in_dag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class _01_Bellman_Ford_Map_Graph {

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
  private static int[] distances;

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int v =
        Integer.parseInt(reader.readLine()); // v for vertex, this is the number of vertices/nodes
    int e = Integer.parseInt(reader.readLine()); // e for edge, this is the number of edges

    prev = new int[v + 1]; // or graph.size
    distances = new int[v + 1]; // or graph.size

    Arrays.fill(prev, -1);
    Arrays.fill(distances, Integer.MAX_VALUE);

    getEdges(reader, e);

    int s = Integer.parseInt(reader.readLine()); // source/start node
    int d = Integer.parseInt(reader.readLine()); // destination/end node;

    distances[s] = 0;

    try {
      bellmanFord(v, e);
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

  private static void bellmanFord(int v, int e) {
    for (int i = 1; i < v - 1; i++) {
      for (int j = 1; j < e; j++) {

        List<Edge> edges = GRAPH.getOrDefault(j, Collections.emptyList());

        for (Edge edge : edges) {
          int from = edge.from();
          if (distances[from] != Integer.MAX_VALUE) {
            int to = edge.to();
            int newValue = distances[from] + edge.weight();
            if (newValue < distances[to]) {
              distances[to] = newValue;
              prev[to] = from;
            }
          }
        }
      }
    }

    for (List<Edge> edges : GRAPH.values()) {
      for (Edge edge : edges) {
        int from = edge.from();
        if (distances[from] != Integer.MAX_VALUE) {
          int newValue = distances[from] + edge.weight();
          int to = edge.to();
          if (newValue < distances[to]) {
            throw new IllegalArgumentException("Negative Cycle Detected");
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

      GRAPH.putIfAbsent(u, new ArrayList<>());
      GRAPH.get(u).add(new Edge(u, v, w));
    }
  }
}
