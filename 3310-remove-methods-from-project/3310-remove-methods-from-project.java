class Solution {
    private List<Integer>[] graph;
    private List<Integer>[] reverse;
    private boolean[] suspicious;
    private boolean[] visited;

    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        graph = new ArrayList[n];
        reverse = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            reverse[i] = new ArrayList<>();
        }

        for (int[] edge : invocations) {
            graph[edge[0]].add(edge[1]);
            reverse[edge[1]].add(edge[0]);
        }

        suspicious = new boolean[n];
        dfsSuspicious(k);

        for (int i = 0; i < n; i++) {
            if (!suspicious[i]) {
                for (int next : graph[i]) {
                    if (suspicious[next]) {
                        List<Integer> ans = new ArrayList<>();
                        for (int j = 0; j < n; j++) {
                            ans.add(j);
                        }
                        return ans;
                    }
                }
            }
        }

        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!suspicious[i]) {
                ans.add(i);
            }
        }

        return ans;
    }

    private void dfsSuspicious(int node) {
        if (suspicious[node]) {
            return;
        }

        suspicious[node] = true;

        for (int next : graph[node]) {
            dfsSuspicious(next);
        }
    }
}