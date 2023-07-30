package _04_graphs_strongly_connected_components_max_flow;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Solution to the max flow problem using Edmonds–Karp algorithm
 * For more info: https://en.wikipedia.org/wiki/Edmonds%E2%80%93Karp_algorithm
 * */
public class EdmondsKarp {

    private static int[][] graph;
    private static int[] parents;
    private static boolean[] visited;
    public static int findMaxFlow(int[][] targetGraph) {
        graph = targetGraph;
        parents = new int[targetGraph.length];
        visited = new boolean[targetGraph.length];

        Arrays.fill(parents, - 1);

        int maxFlow = 0;

        while(bfs()) {
            int node = graph.length - 1;
            int flow = Integer.MAX_VALUE;

            while (node != 0) {
                flow = Math.min(flow, graph[parents[node]][node]);
                node = parents[node];
            }

            maxFlow += flow;

            node = graph.length - 1;

            while (node != 0) {
                graph[parents[node]][node] -= flow;
                graph[node][parents[node]] += flow;

                node = parents[node];
            }
        }
        return maxFlow;
    }

    private static boolean bfs() {
        Deque<Integer> queue = new ArrayDeque<>();
        Arrays.fill(visited, false);
        Arrays.fill(visited, false);

        int start = 0;

        queue.offer(start);

        while (!queue.isEmpty()) {
            int node = queue.poll();

            for(int child = 0; child < graph[node].length; child++) {
                if (graph[node][child] > 0 && !visited[child]) {
                    visited[child] = true;
                    parents[child] = node;
                    queue.offer(child);
                }
            }
        }
        return visited[visited.length - 1];
    }
}
