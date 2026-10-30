import java.util.*;

class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {
        List<int[]>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(new int[]{e[1], e[2]});
        }

        if (k < 1) {
            return -1;
        }

        long[][] dist = new long[n][k + 1];
        for (long[] row : dist) {
            Arrays.fill(row, Long.MAX_VALUE);
        }

        PriorityQueue<long[]> pq =
            new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));

        dist[0][1] = 0;
        pq.offer(new long[]{0, 0, 1});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();

            long cost = cur[0];
            int u = (int) cur[1];
            int run = (int) cur[2];

            if (cost != dist[u][run]) {
                continue;
            }

            if (u == n - 1) {
                return (int) cost;
            }

            for (int[] e : graph[u]) {
                int v = e[0];
                int nextRun = labels.charAt(u) == labels.charAt(v)
                    ? run + 1 : 1;

                if (nextRun > k) {
                    continue;
                }

                long nextCost = cost + e[1];

                if (nextCost < dist[v][nextRun]) {
                    dist[v][nextRun] = nextCost;
                    pq.offer(new long[]{nextCost, v, nextRun});
                }
            }
        }

        return -1;
    }
}