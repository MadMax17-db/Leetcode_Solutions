class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int n = nums.length;

        if (n < 3) {
            return n;
        }

        int bits = (int) (Math.log(n) / Math.log(2));

        return 1 << (bits + 1);
    }
}