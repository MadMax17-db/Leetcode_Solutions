class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int[] diff = new int[n];
        long total = 0;
        int max = 0;
        long k = (long) k1 + k2;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            total += diff[i];
            max = Math.max(max, diff[i]);
        }

        if (total <= k) {
            return 0;
        }

        int low = 0, high = max;

        while (low < high) {
            int mid = low + (high - low) / 2;
            long needed = 0;

            for (int d : diff) {
                needed += Math.max(0, d - mid);
            }

            if (needed <= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        int limit = low;
        long remaining = k;
        long ans = 0;

        for (int i = 0; i < n; i++) {
            remaining -= Math.max(0, diff[i] - limit);
            diff[i] = Math.min(diff[i], limit);
        }

        for (int i = 0; i < n && remaining > 0; i++) {
            if (diff[i] == limit) {
                diff[i]--;
                remaining--;
            }
        }

        for (int d : diff) {
            ans += (long) d * d;
        }

        return ans;
    }
}