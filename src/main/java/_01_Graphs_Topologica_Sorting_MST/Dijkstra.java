package _01_Graphs_Topologica_Sorting_MST;

import java.util.*;

public class Dijkstra {

  public static List<Integer> dijkstraAlgorithm(
      int[][] graph, int sourceNode, int destinationNode) {
    int[] distance = new int[graph.length];
    boolean[] visited = new boolean[graph.length];
    int[] prev = new int[graph.length];

    Arrays.fill(prev, -1);

    Arrays.fill(distance, Integer.MAX_VALUE);
    distance[sourceNode] = 0;

    PriorityQueue<Integer> queue =
        new PriorityQueue<>(Comparator.comparingInt(node -> distance[node]));
    queue.offer(sourceNode);

    while (!queue.isEmpty()) {
      int parentNode = queue.poll();
      visited[parentNode] = true;

      if (distance[parentNode] == Integer.MAX_VALUE) {
        break;
      }

      int[] children = graph[parentNode];

      for (int childNode = 0; childNode < children.length; childNode++) {
        if (!visited[childNode] && children[childNode] != 0) {
          queue.offer(childNode);
          int newDistance = distance[parentNode] + graph[parentNode][childNode];

          if (newDistance < distance[childNode]) {
            distance[childNode] = newDistance;
            prev[childNode] = parentNode;
          }
        }
      }
    }

    List<Integer> path = new ArrayList<>();

    path.add(destinationNode);

    int n = prev[destinationNode];

    while (n != -1) {
      path.add(n);
      n = prev[n];
    }

    Collections.reverse(path);
    return path.size() > 1 ? path : null;
  }
}
