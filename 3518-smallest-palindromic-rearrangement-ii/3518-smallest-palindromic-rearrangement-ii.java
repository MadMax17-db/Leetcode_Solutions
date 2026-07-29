class Solution {
    private static final long MAX_K = 1000001;

    public String smallestPalindrome(String s, int k) {
        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        char mid = 0;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 == 1) {
                mid = (char) ('a' + i);
                freq[i]--;
                break;
            }
        }

        int[] half = new int[26];
        int halfLen = 0;

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
            halfLen += half[i];
        }

        long total = multinomial(half);

        if (k > total) {
            return "";
        }

        StringBuilder left = new StringBuilder();

        for (int i = 0; i < halfLen; i++) {
            for (int c = 0; c < 26; c++) {
                if (half[c] == 0) {
                    continue;
                }

                half[c]--;

                long ways = multinomial(half);

                if (ways >= k) {
                    left.append((char) ('a' + c));
                    break;
                } else {
                    k -= ways;
                    half[c]++;
                }
            }
        }

        String first = left.toString();
        String second = new StringBuilder(first).reverse().toString();

        return mid == 0 ? first + second : first + mid + second;
    }

    private long multinomial(int[] count) {
        int total = 0;

        for (int x : count) {
            total += x;
        }

        long res = 1;

        for (int x : count) {
            res *= binomial(total, x);

            if (res >= MAX_K) {
                return MAX_K;
            }

            total -= x;
        }

        return res;
    }

    private long binomial(int n, int r) {
        if (r > n) {
            return 0;
        }

        r = Math.min(r, n - r);

        long res = 1;

        for (int i = 1; i <= r; i++) {
            res = res * (n - i + 1) / i;

            if (res >= MAX_K) {
                return MAX_K;
            }
        }

        return res;
    }
}