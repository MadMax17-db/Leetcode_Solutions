class Solution {
    static class Node {
        int[] remain = new int[5];
        int prod = 1;
    }

    static class SegmentTree {
        int n;
        int k;
        Node[] tree;

        SegmentTree(int[] nums, int k) {
            this.n = nums.length;
            this.k = k;
            tree = new Node[4 * n];
            build(nums, 0, 0, n - 1);
        }

        void build(int[] nums, int node, int left, int right) {
            if (tree[node] == null) {
                tree[node] = new Node();
            }

            if (left == right) {
                tree[node].remain[nums[left]] = 1;
                tree[node].prod = nums[left];
                return;
            }

            int mid = left + (right - left) / 2;

            build(nums, node * 2 + 1, left, mid);
            build(nums, node * 2 + 2, mid + 1, right);

            tree[node] = merge(tree[node * 2 + 1], tree[node * 2 + 2]);
        }

        Node merge(Node left, Node right) {
            Node result = new Node();

            result.prod = (left.prod * right.prod) % k;

            for (int i = 0; i < k; i++) {
                result.remain[i] = left.remain[i];
            }

            for (int i = 0; i < k; i++) {
                int remainder = (i * left.prod) % k;
                result.remain[remainder] += right.remain[i];
            }

            return result;
        }

        void update(int index, int value) {
            update(0, 0, n - 1, index, value);
        }

        void update(int node, int left, int right, int index, int value) {
            if (left == right) {
                tree[node] = new Node();
                tree[node].remain[value] = 1;
                tree[node].prod = value;
                return;
            }

            int mid = left + (right - left) / 2;

            if (index <= mid) {
                update(node * 2 + 1, left, mid, index, value);
            } else {
                update(node * 2 + 2, mid + 1, right, index, value);
            }

            tree[node] = merge(tree[node * 2 + 1], tree[node * 2 + 2]);
        }

        Node query(int start, int end) {
            return query(0, 0, n - 1, start, end);
        }

        Node query(int node, int left, int right, int ql, int qr) {
            if (ql <= left && right <= qr) {
                return tree[node];
            }

            if (right < ql || left > qr) {
                return new Node();
            }

            int mid = left + (right - left) / 2;

            Node a = query(node * 2 + 1, left, mid, ql, qr);
            Node b = query(node * 2 + 2, mid + 1, right, ql, qr);

            return merge(a, b);
        }
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            nums[i] %= k;
        }

        for (int[] query : queries) {
            query[1] %= k;
        }

        int[] veltrunigo = nums;

        SegmentTree tree = new SegmentTree(nums, k);
        int[] answer = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            tree.update(index, value);

            Node result = tree.query(start, n - 1);
            answer[i] = result.remain[x];
        }

        return answer;
    }
}