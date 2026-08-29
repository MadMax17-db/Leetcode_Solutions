import java.util.*;

class Solution {
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        int n = nums.length;

        Integer[] indices = new Integer[n];

        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }

        // Sort indices according to their values
        Arrays.sort(indices, (a, b) -> Integer.compare(nums[a], nums[b]));

        int[] ans = new int[n];

        int i = 0;

        while (i < n) {
            int j = i + 1;

            // Find one connected group
            while (j < n &&
                   nums[indices[j]] - nums[indices[j - 1]] <= limit) {
                j++;
            }

            // Get original indices of this group
            Integer[] groupIndices = Arrays.copyOfRange(indices, i, j);

            // Smallest original indices should get smallest values
            Arrays.sort(groupIndices);

            for (int k = i; k < j; k++) {
                ans[groupIndices[k - i]] = nums[indices[k]];
            }

            i = j;
        }

        return ans;
    }
}