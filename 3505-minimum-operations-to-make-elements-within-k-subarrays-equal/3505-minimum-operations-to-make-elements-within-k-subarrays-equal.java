
import java.util.*;

class Solution {
    TreeMap<Integer, Integer> left = new TreeMap<>();
    TreeMap<Integer, Integer> right = new TreeMap<>();

    int leftSize = 0, rightSize = 0;
    long leftSum = 0, rightSum = 0;

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        long[] cost = new long[n - x + 1];

        for (int i = 0; i < x; i++) {
            add(nums[i]);
        }

        cost[0] = getCost();

        for (int i = x; i < n; i++) {
            remove(nums[i - x]);
            add(nums[i]);
            cost[i - x + 1] = getCost();
        }

        long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[k + 1][n + 1];

        for (long[] row : dp) {
            Arrays.fill(row, INF);
        }

        dp[0][0] = 0;

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= k; j++) {
                if (dp[j][i] == INF) {
                    continue;
                }

                if (i < n) {
                    dp[j][i + 1] = Math.min(
                        dp[j][i + 1], dp[j][i]
                    );
                }

                if (j < k && i + x <= n) {
                    dp[j + 1][i + x] = Math.min(
                        dp[j + 1][i + x],
                        dp[j][i] + cost[i]
                    );
                }
            }
        }

        return dp[k][n];
    }

    private void add(int num) {
        if (left.isEmpty() || num <= left.lastKey()) {
            left.merge(num, 1, Integer::sum);
            leftSize++;
            leftSum += num;
        } else {
            right.merge(num, 1, Integer::sum);
            rightSize++;
            rightSum += num;
        }

        balance();
    }

    private void remove(int num) {
        if (left.containsKey(num)) {
            decrease(left, num);
            leftSize--;
            leftSum -= num;
        } else {
            decrease(right, num);
            rightSize--;
            rightSum -= num;
        }

        balance();
    }

    private void decrease(TreeMap<Integer, Integer> map, int num) {
        int count = map.get(num);

        if (count == 1) {
            map.remove(num);
        } else {
            map.put(num, count - 1);
        }
    }

    private void balance() {
        while (leftSize > rightSize + 1) {
            int num = left.lastKey();
            decrease(left, num);
            leftSize--;
            leftSum -= num;

            right.merge(num, 1, Integer::sum);
            rightSize++;
            rightSum += num;
        }

        while (leftSize < rightSize) {
            int num = right.firstKey();
            decrease(right, num);
            rightSize--;
            rightSum -= num;

            left.merge(num, 1, Integer::sum);
            leftSize++;
            leftSum += num;
        }
    }

    private long getCost() {
        long median = left.lastKey();

        return median * leftSize - leftSum
             + rightSum - median * rightSize;
    }
}
