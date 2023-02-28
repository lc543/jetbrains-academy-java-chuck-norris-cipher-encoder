package chucknorris;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input string:");
        String userInput = scanner.nextLine();
        String[] binaryFormStrings = convertToBinaryFormString(userInput);
        System.out.println("The result:");
        for (int i = 0; i < userInput.length();i++){
            System.out.printf(
                    "%c = %s%n",userInput.charAt(i),binaryFormStrings[i]
            );
        }
    }

    private static String[] convertToBinaryFormString(String userInput) {
        String[] result = new String[userInput.length()];
        for (int i = 0;i< userInput.length(); i++){
            char chr = userInput.charAt(i);
            String binaryFormat = String.format("%7s",Integer.toBinaryString(chr)).replace(' ','0');
            result[i]=binaryFormat;
        }
        return result;
    }

}