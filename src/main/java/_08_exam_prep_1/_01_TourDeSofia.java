package _08_exam_prep_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class _01_TourDeSofia {

  private static final Map<Integer, List<Integer>> GRAPH = new HashMap<>();
  private static boolean[] visited;
  private static int[] distances;

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int nodes = Integer.parseInt(reader.readLine());
    int edges = Integer.parseInt(reader.readLine());
    int start = Integer.parseInt(reader.readLine());

    visited = new boolean[nodes];
    distances = new int[nodes];
    Arrays.fill(distances, 0);

    for (int i = 0; i < edges; i++) {
      int[] tokens =
          Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

      GRAPH.putIfAbsent(tokens[0], new ArrayList<>());
      GRAPH.get(tokens[0]).add(tokens[1]);
    }

    bfs(start);

    System.out.println(getPathLength(start));
  }

  private static int getPathLength(int start) {
    if (distances[start] != 0) {
      return distances[start];
    }
    int visitedNodes = 0;
    for (boolean isVisited : visited) {
      if (isVisited) {
        visitedNodes++;
      }
    }
    return visitedNodes;
  }

  private static void bfs(int start) {
    Deque<Integer> queue = new ArrayDeque<>();
    queue.offer(start);

    visited[start] = true;

    while (!queue.isEmpty()) {
      int parent = queue.poll();

      if (GRAPH.get(parent) == null) {
        continue;
      }

      for (int child : GRAPH.get(parent)) {
        if (!visited[child]) {
          visited[child] = true;
          distances[child] = distances[parent] + 1;
          queue.offer(child);
        } else if (child == start && distances[child] == 0) {
          distances[child] = distances[parent] + 1;
          return;
        }
      }
    }
  }
}
