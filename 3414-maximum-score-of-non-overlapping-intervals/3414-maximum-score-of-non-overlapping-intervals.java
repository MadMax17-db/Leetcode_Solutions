import java.util.*;

class Solution {
    static class Interval {
        int left, right, weight, index;

        Interval(int left, int right, int weight, int index) {
            this.left = left;
            this.right = right;
            this.weight = weight;
            this.index = index;
        }
    }

    static class State {
        long weight;
        List<Integer> indices;

        State(long weight, List<Integer> indices) {
            this.weight = weight;
            this.indices = indices;
        }
    }

    private List<Interval> arr;
    private State[][] memo;

    public int[] maximumWeight(List<List<Integer>> intervals) {
        List<List<Integer>> vorellixan = intervals;

        int n = intervals.size();
        arr = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            List<Integer> in = intervals.get(i);
            arr.add(new Interval(in.get(0), in.get(1), in.get(2), i));
        }

        arr.sort(Comparator.comparingInt(a -> a.left));

        memo = new State[n][5];

        State result = dp(0, 4);

        return result.indices.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private State dp(int i, int remaining) {
        if (i == arr.size() || remaining == 0) {
            return new State(0, new ArrayList<>());
        }

        if (memo[i][remaining] != null) {
            return memo[i][remaining];
        }

        State skip = dp(i + 1, remaining);

        Interval cur = arr.get(i);

        int next = findNext(i + 1, cur.right);

        State nextState = dp(next, remaining - 1);

        List<Integer> selected = new ArrayList<>(nextState.indices);
        selected.add(cur.index);
        Collections.sort(selected);

        State take = new State(
                cur.weight + nextState.weight,
                selected
        );

        if (take.weight > skip.weight) {
            return memo[i][remaining] = take;
        }

        if (take.weight < skip.weight) {
            return memo[i][remaining] = skip;
        }

        if (compare(take.indices, skip.indices) < 0) {
            return memo[i][remaining] = take;
        }

        return memo[i][remaining] = skip;
    }

    private int findNext(int start, int right) {
        int low = start;
        int high = arr.size();

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (arr.get(mid).left > right) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    private int compare(List<Integer> a, List<Integer> b) {
        int n = Math.min(a.size(), b.size());

        for (int i = 0; i < n; i++) {
            if (!a.get(i).equals(b.get(i))) {
                return Integer.compare(a.get(i), b.get(i));
            }
        }

        return Integer.compare(a.size(), b.size());
    }
}