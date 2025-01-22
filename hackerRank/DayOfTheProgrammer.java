public static String dayOfProgrammer(int year) {
    int counter = 0;
    int desiredDay = 256;
    while (!(desiredDay < 28)) {
        counter++;
        switch (counter) {
            case 4:
            case 6:
            case 9:
            case 11:
                desiredDay = desiredDay - 30;
                break;
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                desiredDay = desiredDay - 31;
                break;
        }
        if (counter == 2) {
            if (year % 4 == 0) {
                desiredDay = desiredDay - 29;
            } else {
                desiredDay = desiredDay - 28;
            }
        }
    }
    counter++;
    if (counter < 10) {
        return desiredDay + ".0" + counter + "." + year;
    } else {
        return desiredDay + "." + counter + "." + year;
    }
}
