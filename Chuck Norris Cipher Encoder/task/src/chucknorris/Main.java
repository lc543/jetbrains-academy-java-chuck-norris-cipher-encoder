package chucknorris;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input string:");
        String userInput = scanner.nextLine();
        String result = separateCharactersBySpace(userInput);
        System.out.println(result);
    }

    private static String separateCharactersBySpace(String userInput) {
        if (userInput.length() <= 1) {
            return userInput;
        }
        char[] stringChars = userInput.toCharArray();
        char[] temp = new char[stringChars.length*2-1];
        for (int i = 0; i < stringChars.length; i++){
            temp[2*i] = stringChars[i];
        }
        for (int i = 0; i < stringChars.length-1; i++){
            temp[2*i+1] = ' ';
        }
        return new String(temp);
    }
}