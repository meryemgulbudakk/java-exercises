package hackerRank;

import java.util.List;

public class AVeryBigSum {
    public static long aVeryBigSum(List<Long> ar) {
        long total = 0;
        for (int i = 0; i < ar.size(); i++) {
            total += ar.get(i);
        }
        return total;
    }
}
