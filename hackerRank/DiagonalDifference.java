package hackerRank;

import java.util.List;

public class DiagonalDifference {
    public static int diagonalDifference(List<List<Integer>> arr) {
        int leftToRight = 0;
        int rightToLeft = 0;
        int counter = arr.size() - 1;
        for (int i = 0; i < arr.size(); i++) {
            leftToRight += arr.get(i).get(i);
            rightToLeft += arr.get(i).get(counter);
            counter--;
        }

        int result = Math.abs((leftToRight - rightToLeft));
        return result;
    }
}
