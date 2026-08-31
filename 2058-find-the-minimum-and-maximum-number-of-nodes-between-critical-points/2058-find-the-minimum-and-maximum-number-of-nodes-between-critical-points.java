class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int[] ans = {-1, -1};

        if (head == null || head.next == null || head.next.next == null) {
            return ans;
        }

        ListNode prev = head;
        ListNode curr = head.next;

        int index = 1;
        int first = -1;
        int last = -1;

        int minDistance = Integer.MAX_VALUE;
        int maxDistance = 0;

        while (curr.next != null) {
            if ((curr.val > prev.val && curr.val > curr.next.val) ||
                (curr.val < prev.val && curr.val < curr.next.val)) {

                if (first == -1) {
                    first = index;
                } else {
                    minDistance = Math.min(minDistance, index - last);
                    maxDistance = index - first;
                }

                last = index;
            }

            prev = curr;
            curr = curr.next;
            index++;
        }

        if (first == -1 || first == last) {
            return ans;
        }

        ans[0] = minDistance;
        ans[1] = maxDistance;

        return ans;
    }
}