
import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        List<Integer>[] tree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        int[] ans = new int[n];
        int[] prev = new int[n];
        int[] last = new int[26];

        Arrays.fill(last, -1);
        Arrays.fill(ans, 1);

        Stack<Integer> stack = new Stack<>();
        stack.push(0);

        while (!stack.isEmpty()) {
            int node = stack.pop();

            if (node >= 0) {
                int c = s.charAt(node) - 'a';

                prev[node] = last[c];
                last[c] = node;

                stack.push(~node);

                for (int child : tree[node]) {
                    stack.push(child);
                }
            } else {
                node = ~node;

                int c = s.charAt(node) - 'a';
                last[c] = prev[node];

                int p = prev[node] != -1 ? prev[node] : parent[node];

                if (p != -1) {
                    ans[p] += ans[node];
                }
            }
        }

        return ans;
    }
}
