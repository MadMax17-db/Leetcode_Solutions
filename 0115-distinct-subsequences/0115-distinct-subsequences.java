class Solution {
    public int numDistinct(String s, String t) {
        int n = t.length();

        int[] dp = new int[n + 1];
        dp[0] = 1;

        for (char c : s.toCharArray()) {
            for (int j = n; j > 0; j--) {
                if (c == t.charAt(j - 1)) {
                    dp[j] += dp[j - 1];
                }
            }
        }

        return dp[n];
    }
}