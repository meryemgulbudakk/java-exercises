import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
        HashMap<Character, Integer> letters = new HashMap<>(26);
        for (char i = 'A'; i <= 'Z'; i++) {
            letters.put(i, 0);
        }
        String filePath = "/home/meryem/İndirilenler/Telegram Desktop/moby_dick.txt";
        System.out.println("enter letter");
        String letter = scan.nextLine().toUpperCase();
        displayList(letters, filePath);
        searchChar(letters, letter);
    }

    public static void searchChar(HashMap<Character, Integer> letters, String letter) {
        char key = letter.charAt(0);
        if (letters.containsKey(key)) {
            System.out.println("-------------\nfrequency for " + letter + ": " + letters.get(key));
        } else {
            System.out.println("key not found");
        }
    }

    public static void displayList(HashMap<Character, Integer> letters, String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    char key = Character.toUpperCase(line.charAt(i));
                    if (letters.containsKey(key)) {
                        letters.put(key, letters.get(key) + 1);
                    }
                }
            }
        } catch (IOException e) {
        }
        letters.forEach((character, integer) -> System.out.println("letter: " + character + " frequency " + integer));
    }
}