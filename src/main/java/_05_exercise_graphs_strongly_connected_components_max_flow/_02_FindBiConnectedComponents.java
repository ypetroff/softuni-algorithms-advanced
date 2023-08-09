package _05_exercise_graphs_strongly_connected_components_max_flow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _02_FindBiConnectedComponents {

  private static int[][] graph;
  private static int[] depths;
  private static int[] lowpoints;
  private static int[] parents;
  private static int[]  reachableCount;

  private static final List<List<Integer>> SUB_GRAPHS = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int nodes = Integer.parseInt(reader.readLine().split("\\s+")[1]);
    int edges = Integer.parseInt(reader.readLine().split("\\s+")[1]);

    graph = new int[nodes][nodes];
    depths = new int[nodes];
    lowpoints = new int[nodes];
    parents = new int[nodes];
    reachableCount = new int[nodes];

    Arrays.fill(parents, -1);

    for (int i = 0; i < edges; i++) {
      int[] tokens =
          Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

      int source = tokens[0];
      int destination = tokens[1];

      graph[source][reachableCount[source]++] = destination;
      graph[destination][reachableCount[destination]++] = source;
    }

    findArticulationPoints(0, 0, new ArrayList<>());

    System.out.println("Number of bi-connected components: " + SUB_GRAPHS.size());
  }

  private static void findArticulationPoints(int node, int depth, List<Integer> subset) {
    depths[node] = depth;
    lowpoints[node] = depth;

    for (int i = 0; i < reachableCount[node]; i++) {
      int child = graph[node][i];

      if (depths[child] == 0) {
        parents[child] = node;

        List<Integer> components = new ArrayList<>();

        findArticulationPoints(child, depth + 1, components);

        if (lowpoints[child] >= depths[node] || parents[child] == -1) {
          components.add(node);
          SUB_GRAPHS.add(components);
        } else {
          subset.addAll(components);
        }

        lowpoints[node] = Math.min(lowpoints[node], lowpoints[child]);

      } else if (child != parents[node]) {
        lowpoints[node] = Math.min(lowpoints[node], depths[child]);
      }
    }
    subset.add(node);
  }
}
