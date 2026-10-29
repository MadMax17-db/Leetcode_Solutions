import java.util.*;

class Solution {
    private int[] head, to, next;
    private int[][] up;
    private int[] tin, tout, depth;
    private int timer, log, n;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        this.n = n;
        log = 1;
        while ((1 << log) <= n) {
            log++;
        }

        head = new int[n];
        Arrays.fill(head, -1);

        int m = edges.length;
        to = new int[2 * m];
        next = new int[2 * m];

        int idx = 0;
        for (int[] e : edges) {
            int u = e[0], v = e[1];

            to[idx] = v;
            next[idx] = head[u];
            head[u] = idx++;

            to[idx] = u;
            next[idx] = head[v];
            head[v] = idx++;
        }

        up = new int[log][n];
        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        timer = 0;

        dfs(0, 0);

        BIT bit = new BIT(n + 1);
        char[] chars = s.toCharArray();

        for (int i = 0; i < n; i++) {
            int mask = 1 << (chars[i] - 'a');
            bit.update(tin[i], mask);
            bit.update(tout[i] + 1, mask);
        }

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");

            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                char c = p[2].charAt(0);

                if (chars[u] != c) {
                    int change = (1 << (chars[u] - 'a')) ^ (1 << (c - 'a'));

                    bit.update(tin[u], change);
                    bit.update(tout[u] + 1, change);

                    chars[u] = c;
                }
            } else {
                int u = Integer.parseInt(p[1]);
                int v = Integer.parseInt(p[2]);

                int l = lca(u, v);
                int mask = bit.query(tin[u]) ^ bit.query(tin[v])
                         ^ (1 << (chars[l] - 'a'));

                ans.add((mask & (mask - 1)) == 0);
            }
        }

        return ans;
    }

    private void dfs(int u, int parent) {
        tin[u] = timer++;
        up[0][u] = parent;

        for (int j = 1; j < log; j++) {
            up[j][u] = up[j - 1][up[j - 1][u]];
        }

        for (int i = head[u]; i != -1; i = next[i]) {
            int v = to[i];

            if (v == parent) {
                continue;
            }

            depth[v] = depth[u] + 1;
            dfs(v, u);
        }

        tout[u] = timer - 1;
    }

    private int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < log; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) {
            return u;
        }

        for (int j = log - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return up[0][u];
    }

    private static class BIT {
        private final int[] tree;
        private final int n;

        BIT(int n) {
            this.n = n;
            tree = new int[n + 1];
        }

        void update(int index, int value) {
            for (int i = index + 1; i <= n; i += i & -i) {
                tree[i] ^= value;
            }
        }

        int query(int index) {
            int result = 0;

            for (int i = index + 1; i > 0; i -= i & -i) {
                result ^= tree[i];
            }

            return result;
        }
    }
}