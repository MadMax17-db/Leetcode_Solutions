class Solution {
    public int longestSubsequence(int[] nums) {
        int xor = 0;
        int zeros = 0;
        int n = nums.length;

        for (int x : nums) {
            xor ^= x;
            if (x == 0) {
                zeros++;
            }
        }

        if (xor != 0) {
            return n;
        }

        return zeros == n ? 0 : n - 1;
    }
}