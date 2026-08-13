class Solution {
    int[] len, pref, suff, best;
    char[] pc, sc;

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        len = new int[4 * n];
        pref = new int[4 * n];
        suff = new int[4 * n];
        best = new int[4 * n];
        pc = new char[4 * n];
        sc = new char[4 * n];

        build(1, 0, n - 1, s);

        int[] ans = new int[queryIndices.length];

        for (int i = 0; i < queryIndices.length; i++) {
            update(1, 0, n - 1, queryIndices[i], queryCharacters.charAt(i));
            ans[i] = best[1];
        }

        return ans;
    }

    void build(int node, int l, int r, String s) {
        if (l == r) {
            len[node] = pref[node] = suff[node] = best[node] = 1;
            pc[node] = sc[node] = s.charAt(l);
            return;
        }

        int mid = (l + r) / 2;
        build(node * 2, l, mid, s);
        build(node * 2 + 1, mid + 1, r, s);
        merge(node);
    }

    void update(int node, int l, int r, int idx, char c) {
        if (l == r) {
            pc[node] = sc[node] = c;
            return;
        }

        int mid = (l + r) / 2;

        if (idx <= mid)
            update(node * 2, l, mid, idx, c);
        else
            update(node * 2 + 1, mid + 1, r, idx, c);

        merge(node);
    }

    void merge(int node) {
        int left = node * 2;
        int right = node * 2 + 1;

        len[node] = len[left] + len[right];
        pc[node] = pc[left];
        sc[node] = sc[right];

        pref[node] = pref[left];
        suff[node] = suff[right];
        best[node] = Math.max(best[left], best[right]);

        if (sc[left] == pc[right]) {
            best[node] = Math.max(best[node], suff[left] + pref[right]);

            if (pref[left] == len[left])
                pref[node] = len[left] + pref[right];

            if (suff[right] == len[right])
                suff[node] = len[right] + suff[left];
        }
    }
}