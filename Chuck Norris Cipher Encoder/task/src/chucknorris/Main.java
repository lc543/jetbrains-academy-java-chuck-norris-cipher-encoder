package chucknorris;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Please input operation (encode/decode/exit):");
            String operation = scanner.nextLine();
            switch (operation) {
                case "encode":
                    doEncoding();
                    break;
                case "decode":
                    doDecoding();
                    break;
                case "exit":
                    System.out.println("Bye");
                    isRunning = false;
                    break;
                default:
                    System.out.printf("There is no '%s' operation\n", operation);
                    break;
            }
        }
    }


    private static void doDecoding() {
        System.out.println("Input encoded string:");
        String text = scanner.nextLine();
        if (!isEncodedStringValid(text)) {
            System.out.println("Encoded string is not valid.");
            return;
        }
        String encoded = decryptChuckNorris(text);
        System.out.println("Decoded string::\n" + encoded);
    }

    private static boolean isEncodedStringValid(String encodedString) {
        String[] sequences = encodedString.split(" ");
        if (sequences.length % 2 == 1) {
            return false; // The number of blocks is odd;
        }
        int bitCount = 0;
        for (int i = 0; i < sequences.length / 2; i++) {
            if (!(sequences[2 * i].equals("00") || sequences[2 * i].equals("0"))) {
                return false; // The first block of each sequence is not 0 or 00;
            }
            String bitStreakBlock = sequences[2 * i + 1];
            for (int j = 0; j < bitStreakBlock.length(); j++) {
                if (bitStreakBlock.charAt(j) != '0') {
                    return false; //The encoded message includes characters other than 0 or spaces;
                }
            }
            bitCount += bitStreakBlock.length();
        }
        if (bitCount % 7 != 0) {
            return false; // The length of the decoded binary string is not a multiple of 7.
        }
        return true;
    }

    private static String decryptChuckNorris(String encryptedText) {
        String binaryString = decodeChuckNorris(encryptedText);
        return convertBinaryStringToString(binaryString);
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

    private static void doEncoding() {
        System.out.println("Input string:");
        String text = scanner.nextLine();
        String encoded = encryptChuckNorris(text);
        System.out.println("Input encoded string:\n" + encoded);
    }

    private static String encryptChuckNorris(String text) {
        String binaryString = String.join("", convertToBinaryString(text));
        List<String> encodedBitStreaks = encodeChuckNorris(binaryString);
        return String.join(" ", encodedBitStreaks);
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
}