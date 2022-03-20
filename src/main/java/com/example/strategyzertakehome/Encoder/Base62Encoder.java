package com.example.strategyzertakehome.Encoder;

public class Base62Encoder {
    private final String characterSet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
    private final int BASE_VALUE = 62;
    private final int BASE_LEN = 7;

    public String toBase62(Long number) {
        StringBuilder stringBuilder = new StringBuilder();

        while (number != 0) {
            stringBuilder.insert(0, characterSet.charAt((int) (number % BASE_VALUE)));
            number /= BASE_VALUE;
        }

        while (stringBuilder.length() != BASE_LEN) {
            stringBuilder.insert(0, '0');
        }

        return stringBuilder.toString();
    }
}
