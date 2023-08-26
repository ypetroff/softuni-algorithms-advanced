package regular_exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class EcoFriendlyHighwayConstruction {

  private static class Edge implements Comparable<Edge> {

    private final String startNode;
    private final String endNode;
    private final int weight;
    private final int envCost;

    public Edge(String startNode, String endNode, int weight) {
      this.startNode = startNode;
      this.endNode = endNode;
      this.weight = weight;
      this.envCost = 0;
    }

    public Edge(String startNode, String endNode, int weight, int envCost) {
      this.startNode = startNode;
      this.endNode = endNode;
      this.weight = weight;
      this.envCost = envCost;
    }

    @Override
    public int compareTo(Edge o) {
      return Integer.compare(this.weight + this.envCost, o.weight + o.envCost);
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int connections = Integer.parseInt(reader.readLine());

    List<Edge> edges = new ArrayList<>();
    Map<String, Integer> nodeToId = new HashMap<>();
    int idCounter = 0;

    for (int i = 0; i < connections; i++) {
      String[] tokens = reader.readLine().split("\\s+");

      if (!nodeToId.containsKey(tokens[0])) {
        nodeToId.put(tokens[0], idCounter++);
      }
      if (!nodeToId.containsKey(tokens[1])) {
        nodeToId.put(tokens[1], idCounter++);
      }

      if (tokens.length == 4) {
        edges.add(
            new Edge(
                tokens[0], tokens[1], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3])));
      } else {
        edges.add(new Edge(tokens[0], tokens[1], Integer.parseInt(tokens[2])));
      }
    }

    Collections.sort(edges);
    int[] parents = new int[idCounter];

    for (int i = 0; i < parents.length; i++) {
      parents[i] = i;
    }

    List<Edge> forest = new ArrayList<>();

    while (!edges.isEmpty()) {
      Edge edge = edges.remove(0);

      int sourceId = nodeToId.get(edge.startNode);
      int destId = nodeToId.get(edge.endNode);

      int firstRoot = findRoot(sourceId, parents);
      int secondRoot = findRoot(destId, parents);

      if (firstRoot != secondRoot) {
        forest.add(edge);
        parents[firstRoot] = secondRoot;
      }
    }

    int totalCost =
        forest.stream().mapToInt(edge -> edge.weight + edge.envCost).reduce(0, Integer::sum);

    System.out.printf("Total cost of building highways: %d", totalCost);
  }

  private static int findRoot(int node, int[] parents) {
    while (parents[node] != (node)) {
      node = parents[node];
    }
    return node;
  }
}
