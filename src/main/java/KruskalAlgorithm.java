import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalAlgorithm {

    public static List<Edge> kruskal(int numberOfVertices, List<Edge> edges) {
        /*
        We can use PriorityQueue, but in cases with edges with equal weight
        the results will differ. Nevertheless, both will provide the minimum spanning tree.
        * */
        Collections.sort(edges);

        List<Edge> forest = new ArrayList<>();
        int[] parents = new int[numberOfVertices];

    for (int i = 0; i < parents.length; i++) {
        parents[i] = i;
    }

    while (!edges.isEmpty()) {
        Edge edge = edges.remove(0);

        int source = edge.getStartNode();
        int destination = edge.getEndNode();

        int firstRoot = findRoot(source, parents);
        int secondRoot = findRoot(destination, parents);

        if (firstRoot != secondRoot) {
            forest.add(edge);
            parents[firstRoot] = secondRoot;
        }
    }

        return forest;
    }

    public static int findRoot(int node, int[] parents) {
        while (parents[node] != node) {
            node = parents[node];
        }
        return node;
    }
}
