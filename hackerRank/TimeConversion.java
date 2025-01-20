package hackerRank;

public class TimeConversion {
    public static String timeConversion(String s) {
        String hour = s.substring(0, 2);
        String minute = s.substring(3, 5);
        String second = s.substring(6, 8);
        char format = s.charAt(8);
        Integer intHour = Integer.parseInt(hour) + 12;
        if (Integer.parseInt(hour) == 12) {
            if (format == 'A') {
                return "00" + ":" + minute + ":" + second;
            } else {
                return "12" + ":" + minute + ":" + second;
            }
        } else {
            if (format == 'A') {
                return hour + ":" + minute + ":" + second;
            } else {
                return Integer.toString(intHour) + ":" + minute + ":" + second;
            }
        }
    }
}
