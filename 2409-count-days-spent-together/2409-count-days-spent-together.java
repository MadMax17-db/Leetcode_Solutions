class Solution {
    public int countDaysTogether(String arriveAlice, String leaveAlice,
                                 String arriveBob, String leaveBob) {
        int[] days = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        int start = Math.max(toDay(arriveAlice, days), toDay(arriveBob, days));
        int end = Math.min(toDay(leaveAlice, days), toDay(leaveBob, days));

        return Math.max(0, end - start + 1);
    }

    private int toDay(String date, int[] days) {
        int month = Integer.parseInt(date.substring(0, 2));
        int day = Integer.parseInt(date.substring(3));

        for (int i = 1; i < month; i++) {
            day += days[i];
        }

        return day;
    }
}