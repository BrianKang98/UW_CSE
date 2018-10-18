//import java.util.Scanner;
//import java.util.Random;

public class Test {

    public static void main(String[] args) {
        undouble("odegaard");

    }

    public static String undouble(String word) {
        String string = "";
        int number = word.length();
        if (number > 0) {
            char character = word.charAt(0);
            string += character;
            for (int i = 1; i < number; i++) {
                character = word.charAt(i);
                if (word.charAt(i) == word.charAt(i +1)) {
                    character = '';
                }
                string += character;
            }
        }
        return string;
    }
}
