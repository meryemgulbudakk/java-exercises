package hackerRank;

import java.util.ArrayList;
import java.util.List;

public class AppleAndOrange {
    public static void countApplesAndOranges(int s, int t, int a, int b, List<Integer> apples, List<Integer> oranges) {

        int d = 0, appleCount = 0, orangeCount = 0;

        for (int i = 0; i < apples.size(); i++) {
            d = apples.get(i) + a;
            if (d <= t && s <= d) {
                appleCount++;
            }
        }

        System.out.println(appleCount);

        for (int j = 0; j < oranges.size(); j++) {
            d = oranges.get(j) + b;
            if (d <= t && s <= d) {
                orangeCount++;
            }
        }

        System.out.println(orangeCount);
    }
}
