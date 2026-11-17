
import java.util.*;

class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();

        List<Integer>[] divisors = new ArrayList[n + 1];

        for (int i = 0; i <= n; i++) {
            divisors[i] = new ArrayList<>();
        }

        for (int d = 1; d < n; d++) {
            for (int i = d * 2; i <= n; i += d) {
                divisors[i].add(d);
            }
        }

        int[][] cost = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(cost[i], n);
            for (int j = i + 1; j < n; j++) {
                int len = j - i + 1;

                for (int d : divisors[len]) {
                    int changes = 0;

                    for (int offset = 0; offset < d; offset++) {
                        int l = i + offset;
                        int r = j - d + 1 + offset;

                        while (l < r) {
                            if (s.charAt(l) != s.charAt(r)) {
                                changes++;
                            }
                            l += d;
                            r -= d;
                        }
                    }

                    cost[i][j] = Math.min(cost[i][j], changes);
                }
            }
        }

        int INF = 1000000;
        int[][] dp = new int[n + 1][k + 1];

        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }

        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int parts = 1; parts <= k; parts++) {
                for (int j = i - 2; j >= (parts - 1) * 2; j--) {
                    dp[i][parts] = Math.min(
                        dp[i][parts],
                        dp[j][parts - 1] + cost[j][i - 1]
                    );
                }
            }
        }

        return dp[n][k];
    }
}
