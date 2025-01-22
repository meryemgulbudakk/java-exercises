public static void bonAppetit(List<Integer> bill, int k, int b) {
    int total = 0, trueResult, falseResult;
    for (int i = 0; i < bill.size(); i++) {
        total = total + bill.get(i);
    }
    falseResult = total / 2;
    trueResult = (total - bill.get(k)) / 2;
    if (trueResult == b) {
        System.out.println("Bon Appetit");
    } else {
        System.out.println(falseResult - trueResult);
    }
}
