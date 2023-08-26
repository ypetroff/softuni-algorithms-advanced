package _08_exam_prep_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class _02_ChainLightning {

  private static final Map<Integer, List<int[]>> EDGES_BY_NODE = new HashMap<>();
  private static final Map<Integer, List<Integer>> FOREST = new HashMap<>();
  private static boolean[] visited;
  private static int[] damages;
  private static int maxDamage = Integer.MIN_VALUE;

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    int nodes = Integer.parseInt(reader.readLine());
    int edges = Integer.parseInt(reader.readLine());
    int numberOfLightnings = Integer.parseInt(reader.readLine());

    visited = new boolean[nodes];
    damages = new int[nodes];

    fillGraph(reader, edges);

    for (int node : EDGES_BY_NODE.keySet()) {
      if (!visited[node]) {
        prim(node);
      }
    }

    Arrays.fill(visited, false);

    chainLightning(reader, numberOfLightnings);

    System.out.println(maxDamage);
  }

  private static void chainLightning(BufferedReader reader, int numberOfLightnings)
      throws IOException {
    for (int i = 0; i < numberOfLightnings; i++) {
      int[] lightning =
          Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();
      int target = lightning[0];
      int power = lightning[1];

      Arrays.fill(visited, false);
      deliverDamage(target, power);
    }
  }

  private static void deliverDamage(int target, int power) {
    if (visited[target]) {
      return;
    }

    visited[target] = true;
    damages[target] += power;

    if (damages[target] > maxDamage) {
      maxDamage = damages[target];
    }

    if (power == 1) {
      return;
    }

    List<Integer> children = FOREST.get(target);

    if (children == null) {
      return;
    }

    for (Integer child : children) {
      deliverDamage(child, power / 2);
    }
  }

  private static void fillGraph(BufferedReader reader, int edges) throws IOException {
    for (int i = 0; i < edges; i++) {
      int[] tokens =
          Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

      EDGES_BY_NODE.putIfAbsent(tokens[0], new ArrayList<>());
      EDGES_BY_NODE.get(tokens[0]).add(new int[] {tokens[0], tokens[1], tokens[2]});
      EDGES_BY_NODE.putIfAbsent(tokens[1], new ArrayList<>());
      EDGES_BY_NODE.get(tokens[1]).add(new int[] {tokens[1], tokens[0], tokens[2]});
    }
  }

  private static void prim(int startNode) {
    visited[startNode] = true;
    PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));

    queue.addAll(EDGES_BY_NODE.get(startNode));

    while (!queue.isEmpty()) {
      int[] edge = queue.poll();

      int source = edge[0];
      int destination = edge[1];

      if (visited[source] && visited[destination]) {
        continue;
      }

        FOREST.putIfAbsent(source, new ArrayList<>());
        FOREST.get(source).add(destination);
        FOREST.putIfAbsent(destination, new ArrayList<>());
        FOREST.get(destination).add(source);

      if (!visited[source]) {
        visited[source] = true;
        queue.addAll(EDGES_BY_NODE.get(source));
      } else {
        visited[destination] = true;
        queue.addAll(EDGES_BY_NODE.get(destination));
      }

    }
  }
}
