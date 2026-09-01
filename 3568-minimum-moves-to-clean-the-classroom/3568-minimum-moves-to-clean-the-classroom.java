class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int[][] litter = new int[m][n];

        int startRow = 0;
        int startCol = 0;
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = classroom[i].charAt(j);

                if (c == 'S') {
                    startRow = i;
                    startCol = j;
                } else if (c == 'L') {
                    litter[i][j] = count++;
                }
            }
        }

        if (count == 0) {
            return 0;
        }

        int totalMasks = 1 << count;

        boolean[][][][] visited =
            new boolean[m][n][energy + 1][totalMasks];

        int fullMask = totalMasks - 1;

        // All litter is initially uncollected.
        // Using 1 means uncollected and removing the bit means collected.
        int startMask = fullMask;

        java.util.Queue<int[]> queue = new java.util.LinkedList<>();

        queue.offer(new int[] {
            startRow,
            startCol,
            energy,
            startMask
        });

        visited[startRow][startCol][energy][startMask] = true;

        int[] dir = {-1, 0, 1, 0, -1};
        int moves = 0;

        while (!queue.isEmpty()) {

            int size = queue.size();

            while (size-- > 0) {

                int[] state = queue.poll();

                int row = state[0];
                int col = state[1];
                int currentEnergy = state[2];
                int mask = state[3];

                // All litter collected
                if (mask == 0) {
                    return moves;
                }

                // No energy means we cannot move anymore
                if (currentEnergy == 0) {
                    continue;
                }

                for (int k = 0; k < 4; k++) {

                    int newRow = row + dir[k];
                    int newCol = col + dir[k + 1];

                    if (newRow < 0 || newRow >= m ||
                        newCol < 0 || newCol >= n) {
                        continue;
                    }

                    char cell = classroom[newRow].charAt(newCol);

                    if (cell == 'X') {
                        continue;
                    }

                    int newEnergy = currentEnergy - 1;

                    // Reset energy when entering R
                    if (cell == 'R') {
                        newEnergy = energy;
                    }

                    int newMask = mask;

                    // Collect litter
                    if (cell == 'L') {
                        newMask &= ~(1 << litter[newRow][newCol]);
                    }

                    if (!visited[newRow][newCol][newEnergy][newMask]) {

                        visited[newRow][newCol][newEnergy][newMask] = true;

                        queue.offer(new int[] {
                            newRow,
                            newCol,
                            newEnergy,
                            newMask
                        });
                    }
                }
            }

            moves++;
        }

        return -1;
    }
}