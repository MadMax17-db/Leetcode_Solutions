
class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] dp = new int[budget + 1];
        int minPrice = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            int factor = items[i][0];
            int price = items[i][1];

            minPrice = Math.min(minPrice, price);

            int free = 0;

            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % factor == 0) {
                    free++;
                }
            }

            for (int j = budget; j >= price; j--) {
                dp[j] = Math.max(dp[j], dp[j - price] + free + 1);
            }
        }

        int ans = 0;

        for (int i = 0; i <= budget; i++) {
            ans = Math.max(ans, dp[i] + (budget - i) / minPrice);
        }

        return ans;
    }
}
