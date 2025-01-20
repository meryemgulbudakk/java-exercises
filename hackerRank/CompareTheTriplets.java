package hackerRank;

import java.util.ArrayList;
import java.util.List;

public class CompareTheTriplets {

    public static List<Integer> compareTriplets(List<Integer> a, List<Integer> b) {
        List<Integer> list = new ArrayList<Integer>(2);
        list.add(0);
        list.add(0);
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) > b.get(i)) {
                list.set(0, list.get(0) + 1);
            } else if (a.get(i) < b.get(i)) {
                list.set(1, list.get(1) + 1);
            }
        }
        return list;
    }
}
