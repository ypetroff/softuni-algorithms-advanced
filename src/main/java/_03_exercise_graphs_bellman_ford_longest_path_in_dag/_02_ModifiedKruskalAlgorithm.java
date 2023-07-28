package _03_exercise_graphs_bellman_ford_longest_path_in_dag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class _02_ModifiedKruskalAlgorithm {
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

  private static final Map<Integer, List<Edge>> GRAPH = new HashMap<>();
  private static int[] parents;

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int nodes = Integer.parseInt(reader.readLine().split("\\s+")[1]);
    int edges = Integer.parseInt(reader.readLine().split("\\s+")[1]);

    for (int i = 0; i < nodes; i++) {
      GRAPH.put(i, new ArrayList<>());
    }

    parents = new int[nodes];

//    for(int i = 0; i < nodes; i++) {
//      parents[i] = i;
//    }

    Arrays.fill(parents, - 1 );

    for (int i = 0; i < edges; i++) {
      int[] tokens =
          Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

      GRAPH.get(tokens[0]).add(new Edge(tokens[0], tokens[1], tokens[2]));
    }

    PriorityQueue<Edge> pq =
        GRAPH.values().stream()
            .flatMap(List::stream)
            .collect(Collectors.toCollection(PriorityQueue::new));

    int forestWeight = 0;
    
    while (!pq.isEmpty()) {
      Edge edge = pq.poll();

      int firstRoot = findRoot(edge.getFrom());
      int secondRoot = findRoot(edge.getTo());
      
      if (firstRoot != secondRoot) {
        forestWeight += edge.getWeight();

        parents[secondRoot] = firstRoot;

        for (int i = 0; i < parents.length; i++) {
          if (parents[i] == secondRoot) {
            parents[i] = firstRoot;
          }
        }
      }
    }

    System.out.println("Minimum spanning forest weight: " + forestWeight);
  }

  private static int findRoot(int node) {
    while (parents[node] != -1) {
      node = parents[node];
    }

    return node;
  }
}
