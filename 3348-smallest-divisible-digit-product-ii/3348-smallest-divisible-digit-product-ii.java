class Solution {
    public String smallestNumber(String num, long t) {
        long temp = t;
        int[] req = new int[10];
        int[] primes = {2, 3, 5, 7};
        for (int p : primes) {
            while (temp % p == 0) {
                req[p]++;
                temp /= p;
            }
        }
        if (temp > 1) return "-1";

        int n = num.length();
        
        int zeroIdx = num.indexOf('0');
        if (zeroIdx != -1) {
            StringBuilder sb = new StringBuilder();
            sb.append(num.substring(0, zeroIdx));
            sb.append((char) (num.charAt(zeroIdx) + 1));
            while (sb.length() < n) {
                sb.append('1');
            }
            num = sb.toString();
        }

        int[] pref2 = new int[n + 1];
        int[] pref3 = new int[n + 1];
        int[] pref5 = new int[n + 1];
        int[] pref7 = new int[n + 1];

        for (int i = 0; i < n; i++) {
            int d = num.charAt(i) - '0';
            pref2[i + 1] = pref2[i] + getFactor(d, 2);
            pref3[i + 1] = pref3[i] + getFactor(d, 3);
            pref5[i + 1] = pref5[i] + getFactor(d, 5);
            pref7[i + 1] = pref7[i] + getFactor(d, 7);
        }

        if (canForm(0, req[2] - pref2[n], req[3] - pref3[n], req[5] - pref5[n], req[7] - pref7[n])) {
            return num;
        }

        for (int i = n - 1; i >= 0; i--) {
            int currentDigit = num.charAt(i) - '0';
            for (int d = currentDigit + 1; d <= 9; d++) {
                int r2 = req[2] - pref2[i] - getFactor(d, 2);
                int r3 = req[3] - pref3[i] - getFactor(d, 3);
                int r5 = req[5] - pref5[i] - getFactor(d, 5);
                int r7 = req[7] - pref7[i] - getFactor(d, 7);

                int remLen = n - 1 - i;
                if (canForm(remLen, r2, r3, r5, r7)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(num, 0, i);
                    sb.append(d);
                    sb.append(fill(remLen, r2, r3, r5, r7));
                    return sb.toString();
                }
            }
        }

        int minLen = getMinLen(req[2], req[3], req[5], req[7]);
        int targetLen = Math.max(n + 1, minLen);
        return fill(targetLen, req[2], req[3], req[5], req[7]);
    }

    private int getFactor(int d, int p) {
        int count = 0;
        while (d > 0 && d % p == 0) {
            count++;
            d /= p;
        }
        return count;
    }

    private boolean canForm(int len, int r2, int r3, int r5, int r7) {
        int needed = getMinLen(r2, r3, r5, r7);
        return len >= needed;
    }

    private int getMinLen(int r2, int r3, int r5, int r7) {
        r2 = Math.max(0, r2);
        r3 = Math.max(0, r3);
        r5 = Math.max(0, r5);
        r7 = Math.max(0, r7);

        int count7 = r7;
        int count5 = r5;
        int count9 = r3 / 2;
        r3 %= 2;
        int count8 = r2 / 3;
        r2 %= 3;

        int count6 = 0;
        if (r3 == 1 && r2 == 1) {
            count6 = 1;
            r3 = 0;
            r2 = 0;
        } else if (r3 == 1 && r2 == 2) {
            count6 = 1;
            r3 = 0;
            r2 = 1;
        }

        int count4 = r2 / 2;
        r2 %= 2;

        int count3 = r3;
        int count2 = r2;

        return count7 + count5 + count9 + count8 + count6 + count4 + count3 + count2;
    }

    private String fill(int len, int r2, int r3, int r5, int r7) {
        r2 = Math.max(0, r2);
        r3 = Math.max(0, r3);
        r5 = Math.max(0, r5);
        r7 = Math.max(0, r7);

        int count7 = r7;
        int count5 = r5;
        int count9 = r3 / 2;
        r3 %= 2;
        int count8 = r2 / 3;
        r2 %= 3;

        int count6 = 0;
        if (r3 == 1 && r2 == 1) {
            count6 = 1;
            r3 = 0;
            r2 = 0;
        } else if (r3 == 1 && r2 == 2) {
            count6 = 1;
            r3 = 0;
            r2 = 1;
        }

        int count4 = r2 / 2;
        r2 %= 2;

        int count3 = r3;
        int count2 = r2;

        int totalUsed = count2 + count3 + count4 + count5 + count6 + count7 + count8 + count9;
        int count1 = len - totalUsed;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count1; i++) sb.append('1');
        for (int i = 0; i < count2; i++) sb.append('2');
        for (int i = 0; i < count3; i++) sb.append('3');
        for (int i = 0; i < count4; i++) sb.append('4');
        for (int i = 0; i < count5; i++) sb.append('5');
        for (int i = 0; i < count6; i++) sb.append('6');
        for (int i = 0; i < count7; i++) sb.append('7');
        for (int i = 0; i < count8; i++) sb.append('8');
        for (int i = 0; i < count9; i++) sb.append('9');

        return sb.toString();
    }
}