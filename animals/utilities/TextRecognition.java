package animals.utilities;

import java.util.Random;
import java.util.Scanner;

public class TextRecognition {
    private final Scanner scanner = new Scanner(System.in);
    private final String[] yesOrNoRetake = {"I'm not sure I caught you: was it yes or no?",
            "Funny, I still don't understand, is it yes or no?",
            "Oh, it's too complicated for me: just tell me yes or no.",
            "Could you please simply say yes or no?",
            "Oh, no, don't try to confuse me: say yes or no."};
    private final Random random = new Random();


    public String animalInput() {
        String input = scanner.nextLine().toLowerCase();
        String result;
        if (input.toLowerCase().matches("a unicorn")) return "a unicorn";
        if (input.toLowerCase().matches("a .+|the .+|an .+")) {
            String[] array = input.split(" ");
            array[0] = array[1].matches("[aeiouyAEIUOY].+|xeme") ? "an" : "a";
            result = String.join(" ", array);
        }
        else result = input.toLowerCase().matches("[aeiouy].+|xeme") ? "an " + input : "a " + input;
        return result.toLowerCase();
    }

    public boolean yesOrNoInput() {
        while (true) {
            String input = scanner.nextLine().trim();
            String yesRegex = "y[.?!]?|yes[.?!]?|yeah[.?!]?|yep[.?!]?|sure[.?!]?|right[.?!]?|affirmative[.?!]?|correct[.?!]?|indeed[.?!]?|you bet[.?!]?|exactly[.?!]?|you said it[.?!]?";
            if (input.toLowerCase().matches
                    (yesRegex))
                return true;
            else {
                String noRegex = "n[.?!]?|no[.?!]?|no way[.?!]?|nah[.?!]?|nope[.?!]?|negative[.?!]?|i don't think so[.?!]?|yeah no[.?!]?";
                if (input.toLowerCase().matches(noRegex))
                    return false;
                else System.out.println(yesOrNoRetake[random.nextInt(yesOrNoRetake.length)]);
            }
        }
    }

    public String[] compareAnimalsQuestion(String firstAnimal, String secondAnimal) {
        String input;
        String[] result = new String[2];
        while (true) {
            System.out.println("Specify a fact that distinguishes " + firstAnimal + " from " + secondAnimal + ".");
            System.out.println("The sentence should be of the format: 'It can/has/is ...'.");
            input = scanner.nextLine();
            if (input.toLowerCase().matches("it has.*|it is.*|it can.*")) {
                result[0] = input.toLowerCase().substring(3, 6).trim();
                result[1] = input.toLowerCase().substring(6).trim();
                return result;
            }
            else System.out.println("The examples of a statement:\n" +
                    " - It can fly\n" +
                    " - It has horn\n" +
                    " - It is a mammal");
        }
    }
}
