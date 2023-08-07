package _05_exercise_graphs_strongly_connected_components_max_flow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class _01_MaximumTasksAssignment {

  private static boolean[][] graph;
  private static int[] parents;

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int people = Integer.parseInt(reader.readLine().split("\\s+")[1]);
    int tasks = Integer.parseInt(reader.readLine().split("\\s+")[1]);

    int nodes = people + tasks + 2;

    graph = new boolean[nodes][nodes];
    parents = new int[nodes];

    Arrays.fill(parents, -1);

    for (int i = 0; i < people; i++) {
      String line = reader.readLine();
      for (int task = 0; task < tasks; task++) {
        char letter = line.charAt(task);
        graph[i][people + task] = letter == 'Y';
      }
    }

    for (int person = 0; person < people; person++) {
      graph[nodes - 2][person] = true;
    }

    for (int task = 0; task < tasks; task++) {
      graph[people + task][nodes - 1] = true;
    }

    while (bfs(nodes - 2, nodes - 1)) {
      int node = nodes - 1;
      while (node != nodes - 2) {
        graph[parents[node]][node] = false;
        graph[node][parents[node]] = true;
        node = parents[node];
      }
    }

    for (int person = 0; person < people; person++) {
      for (int task = 0; task < tasks; task++) {
        if (graph[people + task][person]) {
          System.out.printf("%s-%d%n", Character.toString(person + 65), task + 1);
        }
      }
    }
  }

  private static boolean bfs(int source, int destination) {
    boolean[] visited = new boolean[graph.length];

    Deque<Integer> queue = new ArrayDeque<>();
    queue.offer(source);
    visited[source] = true;

    while (!queue.isEmpty()) {
      int node = queue.poll();

      for (int child = graph[node].length - 1; child >= 0; child--) {
        if (graph[node][child] && !visited[child]) {
          visited[child] = true;
          parents[child] = node;
          queue.offer(child);
        }
      }
    }
    return visited[destination];
  }
}
