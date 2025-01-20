package hackerRank;

import java.util.List;

public class BreakingTheRecords {
    public static List<Integer> breakingRecords(List<Integer> scores) {
        int minCounter = 0;
        int maxCounter = 0;
        int min = scores.get(0);
        int max = scores.get(0);

        for (int i = 1; i < scores.size(); i++) {
            if (scores.get(i) < min) {
                min = scores.get(i);
                minCounter++;
            } else if (scores.get(i) > max) {
                max = scores.get(i);
                maxCounter++;
            }
        }

        scores.clear();
        scores.add(maxCounter);
        scores.add(minCounter);
        return scores;
    }
}
