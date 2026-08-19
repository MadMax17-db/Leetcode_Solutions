import java.util.*;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];

            map.put(row, map.getOrDefault(row, 0) | (1 << (col - 1)));
        }

        int ans = (n - map.size()) * 2;

        for (int seats : map.values()) {
            if ((seats & 0b0111111110) == 0) {
                ans += 2;
            } else if ((seats & 0b0111100000) == 0 ||
                       (seats & 0b0001111000) == 0 ||
                       (seats & 0b0000011110) == 0) {
                ans += 1;
            }
        }

        return ans;
    }
}