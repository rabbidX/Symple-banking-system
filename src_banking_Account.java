package banking;

import java.util.Random;
class Account {
    private final String cardNumber;
    private final String PIN;

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPIN() {
        return PIN;
    }

    public Account() {
        Random random = new Random();
        int begin = random.nextInt(1_000_000);
        int end = random.nextInt(1_000);
        StringBuilder numberBegin = new StringBuilder(Integer.toString(begin));
        while (numberBegin.length() < 6) {
            numberBegin.insert(0, "0");
        }
        StringBuilder numberEnd = new StringBuilder(Integer.toString(end));
        while (numberEnd.length() < 3) {
            numberEnd.insert(0, "0");
        }
        String cardNumber = new StringBuilder().append("400000")
                .append(numberBegin)
                .append(numberEnd).toString();
        String controlSum = String.valueOf(controlSum(cardNumber));
        this.cardNumber = new StringBuilder().append(cardNumber).append(controlSum).toString();
        String PIN = "";
        for (int i = 0; i < 4; i++) {
            PIN += String.valueOf(random.nextInt(10));
        }
        this.PIN = PIN;
    }

    public static int controlSum(String number) {
        char[] array = number.toCharArray();
        int sum = 0;
        for (int i = 0; i < 15; i ++) {
            int digit = Integer.parseInt(String.valueOf(array[i]));
            if (i % 2 == 0) {
                digit *= 2;
            }
            if (digit > 9) {
                digit -= 9;
            }
            sum += digit;
        }

        return (10 - (sum % 10)) % 10;
    }
}
