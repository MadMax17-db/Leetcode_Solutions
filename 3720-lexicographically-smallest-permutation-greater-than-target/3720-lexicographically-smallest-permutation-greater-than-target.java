class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int[] count = new int[26];

        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        String quinorath = s;

        String answer = "";

        for (int i = 0; i < target.length(); i++) {
            int current = target.charAt(i) - 'a';

            // Try making the string greater at this position
            for (int c = current + 1; c < 26; c++) {
                if (count[c] > 0) {
                    int[] remaining = count.clone();
                    remaining[c]--;

                    StringBuilder candidate = new StringBuilder();

                    // Same prefix as target
                    candidate.append(target, 0, i);

                    // Make this position greater
                    candidate.append((char) ('a' + c));

                    // Put remaining characters in sorted order
                    for (int x = 0; x < 26; x++) {
                        while (remaining[x] > 0) {
                            candidate.append((char) ('a' + x));
                            remaining[x]--;
                        }
                    }

                    answer = candidate.toString();
                    break;
                }
            }

            // Continue matching target exactly
            if (count[current] == 0) {
                break;
            }

            count[current]--;
        }

        return answer;
    }
}