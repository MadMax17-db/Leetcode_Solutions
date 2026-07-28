class Solution {
    public String smallestPalindrome(String s) {
        int[] count = new int[26];

        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        StringBuilder half = new StringBuilder();
        String middle = "";

        for (char c = 'a'; c <= 'z'; c++) {
            int idx = c - 'a';
            int times = count[idx] / 2;

            for (int i = 0; i < times; i++) {
                half.append(c);
            }

            if (count[idx] % 2 == 1) {
                middle = String.valueOf(c);
            }
        }

        String firstHalf = half.toString();
        String secondHalf = new StringBuilder(firstHalf).reverse().toString();

        return firstHalf + middle + secondHalf;
    }
}