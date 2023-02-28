package chucknorris;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input encoded string:");
        String userInput = scanner.nextLine();
        String encodedText = decryptChuckNorris(userInput);
        System.out.println("The result:\n" + encodedText);
    }

    private static String decryptChuckNorris(String encryptedText) {
        String binaryString = decodeChuckNorris(encryptedText);
        return convertBinaryStringToString(binaryString);;
    }


    private static String decodeChuckNorris(String encryptedText) {
        StringBuilder sb = new StringBuilder();
        String[] tokens = encryptedText.split(" ");
        for (int i = 0; i < tokens.length; i += 2) {
            String bit = tokens[i].length() == 1 ? "1" : "0";
            int streakSize = tokens[i + 1].length();
            sb.append(bit.repeat(streakSize));
        }
        return sb.toString();
    }

    private static String convertBinaryStringToString(String binaryString) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < binaryString.length(); i += 7) {
            String charBinaryString = binaryString.substring(i, i + 7);
            char c = (char) Integer.parseInt(charBinaryString, 2);
            sb.append(c);
        }
        return sb.toString();
    }


    private static String encryptChuckNorris(String text) {
        String binaryString = String.join("", convertToBinaryString(text));
        List<String> encodedBitStreaks = encodeChuckNorris(binaryString);
        return String.join(" ", encodedBitStreaks);
    }

    private static List<String> encodeChuckNorris(String binaryString) {
        List<String> encodedBitStreaks = new ArrayList<>();
        char previousBit = binaryString.charAt(0);
        int streakSize = 1;
        for (int i = 1; i < binaryString.length(); i++) {
            char currentBit = binaryString.charAt(i);
            if (currentBit == previousBit) {
                streakSize++;
            } else {
                encodedBitStreaks.add(encodeBitStreak(previousBit, streakSize));
                previousBit = currentBit;
                streakSize = 1;
            }
        }
        encodedBitStreaks.add(encodeBitStreak(previousBit, streakSize)); // process the last character
        return encodedBitStreaks;
    }

    private static String encodeBitStreak(char bit, int streakSize) {
        String firstBlock = bit == '0' ? "00" : "0";
        String secondBlock = "0".repeat(streakSize);
        return firstBlock + " " + secondBlock;
    }


    private static String[] convertToBinaryString(String text) {
        String[] result = new String[text.length()];
        for (int i = 0; i < text.length(); i++) {
            char chr = text.charAt(i);
            String binaryFormat = String.format("%7s", Integer.toBinaryString(chr)).replace(' ', '0');
            result[i] = binaryFormat;
        }
        return result;
    }
}