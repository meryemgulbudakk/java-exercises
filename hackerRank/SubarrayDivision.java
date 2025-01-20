package hackerRank;

import java.util.List;

public class SubarrayDivision {
    public static int birthday(List<Integer> s, int d, int m) {
        int counter = 0;

        for (int i = 0; i <= (s.size() - m); i++) {
            int total = 0;
            for (int j = 0; j < m; j++) {
                total += s.get(i + j);
            }
            if (total == d) {
                counter++;
            }
        }
        return counter;
    }
}
