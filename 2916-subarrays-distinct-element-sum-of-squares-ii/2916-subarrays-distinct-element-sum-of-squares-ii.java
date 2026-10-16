import java.util.*;

class Solution {
    static final int MOD = 1_000_000_007;

    class SegmentTree {
        long[] sum;
        long[] square;
        long[] lazy;
        int n;

        SegmentTree(int n) {
            this.n = n;
            sum = new long[4 * n];
            square = new long[4 * n];
            lazy = new long[4 * n];
        }

        void apply(int node, int left, int right, long val) {
            long len = right - left + 1L;
            square[node] = (square[node] + 2 * val * sum[node] % MOD
                    + len * val % MOD * val % MOD) % MOD;
            sum[node] = (sum[node] + len * val) % MOD;
            lazy[node] = (lazy[node] + val) % MOD;
        }

        void push(int node, int left, int right) {
            if (lazy[node] == 0 || left == right) return;

            int mid = (left + right) / 2;
            apply(node * 2, left, mid, lazy[node]);
            apply(node * 2 + 1, mid + 1, right, lazy[node]);
            lazy[node] = 0;
        }

        void update(int node, int left, int right, int ql, int qr) {
            if (ql > right || qr < left) return;

            if (ql <= left && right <= qr) {
                apply(node, left, right, 1);
                return;
            }

            push(node, left, right);

            int mid = (left + right) / 2;
            update(node * 2, left, mid, ql, qr);
            update(node * 2 + 1, mid + 1, right, ql, qr);

            sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
            square[node] = (square[node * 2] + square[node * 2 + 1]) % MOD;
        }

        void update(int left, int right) {
            if (left <= right) {
                update(1, 0, n - 1, left, right);
            }
        }

        long getSquareSum() {
            return square[1];
        }
    }

    public int sumCounts(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> last = new HashMap<>();
        SegmentTree tree = new SegmentTree(n);
        long ans = 0;

        for (int i = 0; i < n; i++) {
            int prev = last.getOrDefault(nums[i], -1);
            tree.update(prev + 1, i);
            ans = (ans + tree.getSquareSum()) % MOD;
            last.put(nums[i], i);
        }

        return (int) ans;
    }
}