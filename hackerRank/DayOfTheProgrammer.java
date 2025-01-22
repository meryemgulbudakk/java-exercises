public static String dayOfProgrammer(int year) {
    String result = null;
    if (year >= 1700 && year <= 1917) {
        if (year % 4 == 0) {
            result = "12.09." + year;
        } else {
            result = "13.09." + year;
        }
    } else if (year > 1918 && year <= 2700) {
        if (year % 4 == 0 & !(year % 100 == 0)) {
            result = "12.09." + year;
        } else if (year % 400 == 0) {
            result = "12.09." + year;
        } else {
            result = "13.09." + year;
        }
    } else {
        result = "26.09." + year;
    }
    return result;

}
