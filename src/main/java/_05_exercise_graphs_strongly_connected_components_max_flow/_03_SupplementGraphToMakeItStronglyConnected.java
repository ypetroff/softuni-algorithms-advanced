package _05_exercise_graphs_strongly_connected_components_max_flow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class _03_SupplementGraphToMakeItStronglyConnected {

  private static boolean[][] graph;
  public static boolean[][] reversedGraph;
  private static boolean[] visited;
  private static List<List<Integer>> components = new ArrayList<>();

  private static Deque<Integer> stack = new ArrayDeque<>();

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int nodes = Integer.parseInt(reader.readLine().split("\\s+")[1]);
    int edges = Integer.parseInt(reader.readLine().split("\\s+")[1]);

    graph = new boolean[nodes][nodes];
    reversedGraph = new boolean[nodes][nodes];
    visited = new boolean[nodes];

    for (int i = 0; i < edges; i++) {
      int[] tokens =
          Arrays.stream(reader.readLine().split(" -> ")).mapToInt(Integer::parseInt).toArray();

      int source = tokens[0];
      int destination = tokens[1];

      graph[source][destination] = true;
      reversedGraph[destination][source] = true;
    }
    for (int node = 0; node < nodes; node++) {
      if (!visited[node]) {
        dfs(node);
      }
    }

    Arrays.fill(visited, false);

    while (!stack.isEmpty()) {
      int node = stack.pop();

      if (!visited[node]) {
        components.add(new ArrayList<>());
        reversedDfs(node);
      }
    }

    boolean[][] DAG = new boolean[components.size()][components.size()];

    for (int i = 0; i < components.size(); i++) {
      List<Integer> subGraph = components.get(i);
      for (Integer element : subGraph) {
        for (int j = 0; j < nodes; j++) {
          if (graph[element][j] && j != element) {
            for (int k = 0; k < components.size(); k++) {
              if (components.get(k).contains(j) && i != k) {
                DAG[i][k] = true;
              }
            }
          }
        }
      }
    }
    int zeroIncomingDegree = 0;

    // No incoming edges
    for (int col = 0; col < DAG.length; col++) {
      boolean hasEdge = false;
      for (int row = 0; row < DAG[col].length; row++) {
        hasEdge = DAG[row][col];
        if (hasEdge) {
          break;
        }
      }
      if (!hasEdge) {
        zeroIncomingDegree++;
      }
    }
    // No outgoing edges
    int zeroOutgoingDegree = 0;
    for (int row = 0; row < DAG.length; row++) {
      boolean hasEdge = false;
      for (int col = 0; col < DAG[row].length; col++) {
        hasEdge = DAG[row][col];

        if (hasEdge) {
          break;
        }
      }
      if (!hasEdge) {
        zeroOutgoingDegree++;
      }
    }

    System.out.println(Math.max(zeroIncomingDegree, zeroOutgoingDegree));
  }

  public static void dfs(int node) {
    if (!visited[node]) {
      visited[node] = true;

      for (int child = 0; child < graph[node].length; child++) {
        if (graph[node][child]) {
          dfs(child);
        }
      }

      stack.push(node);
    }
  }

  private static void reversedDfs(int node) {
    if (!visited[node]) {
      visited[node] = true;

      components.get(components.size() - 1).add(node);

      for (int child = 0; child < reversedGraph[node].length; child++) {
        if (reversedGraph[node][child]) {
          reversedDfs(child);
        }
      }
    }
  }
}
