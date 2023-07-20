package _01_Graphs_Topologica_Sorting_MST;

import java.util.*;
/*
* This is sample code using Prim algorithm for finding Minimum Spanning Tree(MST)
* */
public class Prim {
  /*
  Expected weight 45 for the hardcoded graph
  * */
  private static final List<Edge> GRAPH = new ArrayList<>();
  private static final Set<Integer> VISITED = new HashSet<>();
  private static final Map<Integer, List<Edge>> EDGES_BY_NODE = new HashMap<>();

  public static void main(String[] args) {
    fillGraph();

    List<Edge> forest = new ArrayList<>();

    for (Edge edge : GRAPH) {
      EDGES_BY_NODE.putIfAbsent(edge.getStartNode(), new ArrayList<>());
      EDGES_BY_NODE.get(edge.getStartNode()).add(edge);

      EDGES_BY_NODE.putIfAbsent(edge.getEndNode(), new ArrayList<>());
      EDGES_BY_NODE.get(edge.getEndNode()).add(edge);
    }

    for (Integer node : EDGES_BY_NODE.keySet()) {
      if (!VISITED.contains(node)) {
        prim(node, forest);
      }
    }

    forest.sort(Comparator.comparingInt(Edge::getWeight));

    int sum = forest.stream()
                    .map(Edge::getWeight)
                            .mapToInt(Integer::intValue)
                            .sum();

    System.out.printf("Expected value of the weight all edges is 45%nActual value is %d%n", sum);
    System.out.println("********************************************");
    System.out.println("All edges are:");

    forest.forEach(e -> System.out.println(e.toString()));
  }

  private static void fillGraph() {
    GRAPH.add(new Edge(0, 3, 9));
    GRAPH.add(new Edge(0, 5, 4));
    GRAPH.add(new Edge(0, 8, 5));
    GRAPH.add(new Edge(1, 4, 8));
    GRAPH.add(new Edge(1, 7, 7));
    GRAPH.add(new Edge(2, 6, 12));
    GRAPH.add(new Edge(3, 5, 2));
    GRAPH.add(new Edge(3, 6, 8));
    GRAPH.add(new Edge(3, 8, 20));
    GRAPH.add(new Edge(4, 7, 10));
    GRAPH.add(new Edge(6, 8, 7));
  }

  public static void prim(int startNode, List<Edge> forest) {
    VISITED.add(startNode);

    PriorityQueue<Edge> queue = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));

    queue.addAll(EDGES_BY_NODE.get(startNode));

    while (!queue.isEmpty()) {
      Edge edge = queue.poll();

      int source = edge.getStartNode();
      int destination = edge.getEndNode();

      int nonTreeNode = -1;

      if (!VISITED.contains(source) && VISITED.contains(destination)) {
        nonTreeNode = source;
      }

      if (VISITED.contains(source) && !VISITED.contains(destination)) {
        nonTreeNode = destination;
      }

      if (nonTreeNode != -1) {
        forest.add(edge);
        VISITED.add(nonTreeNode);
        queue.addAll(EDGES_BY_NODE.get(nonTreeNode));
      }
    }
  }
}
