package _04_graphs_strongly_connected_components_max_flow;

import java.util.*;

/**
 * In this code we use Kosaraju-Sharir's algorithm to find strongly connected components (SCC)
 * for more info https://en.wikipedia.org/wiki/Kosaraju%27s_algorithm
 * */
public class StronglyConnectedComponents {

  private static boolean[] visited;
  private static final Deque<Integer> stack = new ArrayDeque<>();

  public static List<List<Integer>> findStronglyConnectedComponents(List<Integer>[] targetGraph) {
    visited = new boolean[targetGraph.length];

    for (int node = 0; node < targetGraph.length; node++) {
      if (!visited[node]) {
        dfs(node, targetGraph);
      }
    }

    List<Integer>[] reverseGraph = setReversedGraph(targetGraph);

    List<List<Integer>> stronglyConnectedComponents = new ArrayList<>();

    Arrays.fill(visited, false);

    while (!stack.isEmpty()) {
      int node = stack.pop();

      if (!visited[node]) {
        stronglyConnectedComponents.add(new ArrayList<>());
        reverseDfs(node, reverseGraph, stronglyConnectedComponents);
      }
    }
    return stronglyConnectedComponents;
  }

  private static void dfs(int node, List<Integer>[] targetGraph) {
    if (visited[node]) {
      return;
    }
    visited[node] = true;

    for (int child : targetGraph[node]) {
      dfs(child, targetGraph);
    }
    stack.push(node);

  }

  /**
   * We know that the correct type is provided to this method and the method won't produce issues.
   */
  @SuppressWarnings("unchecked")
  private static List<Integer>[] setReversedGraph(List<Integer>[] targetGraph) {
    List<Integer>[] reversedGraph = new ArrayList[targetGraph.length];

    for (int i = 0; i < reversedGraph.length; i++) {
      reversedGraph[i] = new ArrayList<>();
    }

    for (int node = 0; node < targetGraph.length; node++) {
      for (int child = 0; child < targetGraph[node].size(); child++) {
        int parent = targetGraph[node].get(child);
        reversedGraph[parent].add(node);
      }
    }
    return reversedGraph;
  }

  private static void reverseDfs(
      int node, List<Integer>[] reverseGraph, List<List<Integer>> stronglyConnectedComponents) {
    if (visited[node]) {
      return;
    }

    visited[node] = true;

    stronglyConnectedComponents.get(stronglyConnectedComponents.size() - 1).add(node);

    for (int child : reverseGraph[node]) {
      reverseDfs(child, reverseGraph, stronglyConnectedComponents);
    }
  }
}
