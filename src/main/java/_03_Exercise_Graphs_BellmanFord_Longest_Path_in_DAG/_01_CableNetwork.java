package _03_Exercise_Graphs_BellmanFord_Longest_Path_in_DAG;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class _01_CableNetwork {
  private static class Edge implements Comparable<Edge> {
    private final int u;
    private final int v;
    private final int w;

    public Edge(int u, int v, int w) {
      this.u = u;
      this.v = v;
      this.w = w;
    }

    public int getFrom() {
      return u;
    }

    public int getTo() {
      return v;
    }

    public int getWeight() {
      return w;
    }

    @Override
    public int compareTo(Edge edge) {
      return Integer.compare(this.w, edge.getWeight());
    }
  }

  private static final Map<Integer, List<Edge>> GRAPH = new LinkedHashMap<>();
  private static final Set<Integer> VISITED = new HashSet<>();

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int budget = Integer.parseInt(reader.readLine().split("\\s+")[1]);
    int nodes = Integer.parseInt(reader.readLine().split("\\s+")[1]);
    int edgesCount = Integer.parseInt(reader.readLine().split("\\s+")[1]);

    for(int i = 0; i < nodes; i++) {
      GRAPH.put(i, new ArrayList<>());
    }

    for (int i = 0; i < edgesCount; i++) {
      String[] tokens = reader.readLine().split("\\s+");

      int u = Integer.parseInt(tokens[0]);
      int v = Integer.parseInt(tokens[1]);
      int w = Integer.parseInt(tokens[2]);

      Edge edge = new Edge(u, v, w);

      if (tokens.length == 4) {
        VISITED.add(u);
        VISITED.add(v);
      }

      GRAPH.get(u).add(edge);
    }

    int cost = prim(budget);

    System.out.println("Budget used: " + cost);
  }

  private static int prim(int budget) {
    int cost = 0;

    PriorityQueue<Edge> queue = getEdges();

    while (!queue.isEmpty()) {
      Edge edge = queue.poll();

      int u = edge.getFrom();
      int v = edge.getTo();
      int w = edge.getWeight();

      if (budget - w < 0) {
        return cost;
      }

      int nonTreeNode = -1;

      if (onlyDestinationIsVisited(u, v)) {
        nonTreeNode = u;
      }

      if (onlySourceIsVisited(u, v)) {
        nonTreeNode = v;
      }

      if (nonTreeNode != -1) {
        budget -= w;
        cost += w;
        VISITED.add(nonTreeNode);
      }

      queue = getEdges();
    }
    return cost;
  }

  private static PriorityQueue<Edge> getEdges() {
    return GRAPH.values().stream()
        .flatMap(List::stream)
        .filter(edge -> onlyOneNodeVisited(edge.getFrom(), edge.getTo()))
        .collect(Collectors.toCollection(PriorityQueue::new));
  }

  private static boolean onlyOneNodeVisited(int from, int to) {
    return onlySourceIsVisited(from, to) || onlyDestinationIsVisited(from, to);
  }

  private static boolean onlyDestinationIsVisited(int from, int to) {
    return !VISITED.contains(from) && VISITED.contains(to);
  }

  private static boolean onlySourceIsVisited(int from, int to) {
    return VISITED.contains(from) && !VISITED.contains(to);
  }
}
