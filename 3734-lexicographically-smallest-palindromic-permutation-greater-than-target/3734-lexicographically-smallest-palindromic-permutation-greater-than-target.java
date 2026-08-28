class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] cnt = new int[26];

        for (char c : s.toCharArray()) {
            cnt[c - 'a']++;
        }

        // A palindrome can have at most one odd-frequency character.
        int odd = 0;
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            if (cnt[i] % 2 == 1) {
                odd++;
                middle = (char) ('a' + i);
            }
        }

        if (odd > 1) {
            return "";
        }

        // Only the left half needs to be constructed.
        int halfLen = n / 2;
        int[] halfCnt = new int[26];

        for (int i = 0; i < 26; i++) {
            halfCnt[i] = cnt[i] / 2;
        }

        String targetHalf = target.substring(0, halfLen);

        // First try to construct the palindrome whose left half
        // is exactly target's left half.
        if (canMake(halfCnt, targetHalf)) {
            String candidate = build(targetHalf, halfCnt, middle, n);

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        // We need to make the left half just larger than targetHalf.
        for (int pos = halfLen - 1; pos >= 0; pos--) {
            int[] remaining = halfCnt.clone();

            // Match target before this position.
            boolean possible = true;

            for (int i = 0; i < pos; i++) {
                int c = targetHalf.charAt(i) - 'a';

                if (remaining[c] == 0) {
                    possible = false;
                    break;
                }

                remaining[c]--;
            }

            if (!possible) {
                continue;
            }

            int current = targetHalf.charAt(pos) - 'a';

            // Choose the smallest character greater than target[pos].
            for (int c = current + 1; c < 26; c++) {
                if (remaining[c] > 0) {
                    remaining[c]--;

                    StringBuilder left = new StringBuilder();

                    for (int i = 0; i < pos; i++) {
                        left.append(targetHalf.charAt(i));
                    }

                    left.append((char) ('a' + c));

                    // Fill the rest in ascending order.
                    for (int x = 0; x < 26; x++) {
                        while (remaining[x] > 0) {
                            left.append((char) ('a' + x));
                            remaining[x]--;
                        }
                    }

                    return build(left.toString(), null, middle, n);
                }
            }
        }

        return "";
    }

    private boolean canMake(int[] cnt, String s) {
        int[] temp = cnt.clone();

        for (char c : s.toCharArray()) {
            if (temp[c - 'a'] == 0) {
                return false;
            }
            temp[c - 'a']--;
        }

        return true;
    }

    private String build(String left, int[] unused, char middle, int n) {
        StringBuilder ans = new StringBuilder();

        ans.append(left);

        if (n % 2 == 1) {
            ans.append(middle);
        }

        ans.append(new StringBuilder(left).reverse());

        return ans.toString();
    }
}