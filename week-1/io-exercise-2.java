import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;

public class Main2 {
    public static void main(String[] args) {
        String filePath = "";
        System.out.println("total: " + sum(binaryRead(filePath)));
    }

    public static ArrayList<Integer> binaryRead(String filePath) {
        ArrayList<Integer> list = new ArrayList<>();
        try (RandomAccessFile binaryFile = new RandomAccessFile(filePath, "r")) {
            binaryFile.seek(0);
            long valueLong = binaryFile.readLong();
            binaryFile.seek(valueLong);
            while (true) {
                try {
                    int valueInt = binaryFile.readInt();
                    list.add(valueInt);
                } catch (Exception e) {
                    break;
                }
            }
        } catch (IOException e) {
        }
        return list;
    }

    public static int sum(ArrayList<Integer> list) {
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }
        return sum;
    }
}
