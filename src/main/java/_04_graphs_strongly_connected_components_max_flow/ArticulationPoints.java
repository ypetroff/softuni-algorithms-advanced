package _04_graphs_strongly_connected_components_max_flow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Finding articulation points using Hopcroft-Tarjan algorithm
 * https://en.wikipedia.org/wiki/Biconnected_component#Algorithms
 * */
public class ArticulationPoints {

  private static List<Integer>[] graph;
  private static List<Integer> points;
  private static boolean[] visited;
  private static int[] parents;
  private static int[] depths;
  private static int[] lowPoints;

  public static List<Integer> findArticulationPoints(List<Integer>[] targetGraph) {
    graph = targetGraph;
    points = new ArrayList<>();
    visited = new boolean[targetGraph.length];
    parents = new int[targetGraph.length];
    depths = new int[targetGraph.length];
    lowPoints = new int[targetGraph.length];

    Arrays.fill(parents, -1);

    discoverArticulationPoints(0, 1);

    return points;
  }

  private static void discoverArticulationPoints(int node, int depth) {
    visited[node] = true;
    depths[node] = depth;
    lowPoints[node] = depth;

    int children = 0;

    boolean isArticulationPoint = false;

    for (int child : graph[node]) {
      if (!visited[child]) {
        parents[child] = node;
        children++;
        discoverArticulationPoints(child, depth + 1);
        if (lowPoints[child] >= depth) {
          isArticulationPoint = true;
        }
        lowPoints[node] = Math.min(lowPoints[node], lowPoints[child]);
      } else if (parents[node] != child) { // if there's new path
        lowPoints[node] = Math.min(lowPoints[node], depths[child]);
      }
    }

    if ((parents[node] == -1 && children > 1) || (parents[node] != -1 && isArticulationPoint)) {
      points.add(node);
    }
  }
}
