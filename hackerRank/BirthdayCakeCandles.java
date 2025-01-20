package hackerRank;

import java.util.Collections;
import java.util.List;

public class BirthdayCakeCandles {
    public static int birthdayCakeCandles(List<Integer> candles) {
        int max = candles.get(0);
        for (int i = 1; i < candles.size(); i++) {
            if (max < candles.get(i)) {
                max = candles.get(i);
            }
        }
        return Collections.frequency(candles, max);
    }
}
