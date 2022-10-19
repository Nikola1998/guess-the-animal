package animals.main;

import animals.utilities.Mapper;
import animals.utilities.Statistics;
import animals.utilities.TextRecognition;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Computer {
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();
    private final TextRecognition textRecognition = new TextRecognition();
    private final String[] goodByeMessage = {"See ya later, dawg!",
            "Remember, kids, don't do cocaine!",
            "Dowidzenia!",
            "Have a great rest of your life!"};
    private static final ArrayList<String> arrayOfAnimalNames = new ArrayList<>();
    private static final ArrayList<String> arrayOfAnimalFacts = new ArrayList<>();
    private static int counter = 0;
    private String[] question;
    private String questionForNode;
    private AnimalTreeNode endNode;
    private boolean keepPlaying = true;
    private final Mapper mapper;
    private AnimalTreeNode root;

    public Computer(String fileExtension) {
        this.mapper = new Mapper(fileExtension);
        root = mapper.readFile();
    }

    public Computer() {
        this.mapper = new Mapper();
        root = mapper.readFile();
    }



    public void startGame() {
        greetingBasedOnTime();
        if (root == null) rootSetup();
        System.out.println("\nWelcome to the animal expert system!\n");
        showMenu();
        goodByeMessage();
    }



    private void rootSetup(){
        System.out.println("I want to learn about animals.\n" +
                "Which animal do you like most?");
        root = new AnimalTreeNode(textRecognition.animalInput());
    }

    public void startConversation() {
        while (keepPlaying) {
            System.out.println("Wonderful! I've learned so much about animals!\n" +
                    "Let's play a game!\n" +
                    "You think of an animal, and I guess it.\n" +
                    "Press enter when you're ready.");
            pressEnterToContinue();
            if(guess(root)){
                guessedCorrectly();
            } else {
                guessedIncorrectly();
            }
            askToContinue();
        }
        mapper.mapperMap(root);
        showMenu();
        }




    private void showMenu() {
        System.out.println("What do you want to do:\n" +
                "\n" +
                "1. Play the guessing game\n" +
                "2. List of all animals\n" +
                "3. Search for an animal\n" +
                "4. Calculate statistics\n" +
                "5. Print the Knowledge Tree\n" +
                "0. Exit");
        boolean pressedExit = false;
        while (!pressedExit) {
            int input = scanner.nextInt();
            switch (input) {
                case 1: startConversation(); break;
                case 2: returnAnimals(); break;
                case 3: searchForAnAnimal(); break;
                case 4: Statistics.showStatistics(root); break;
                case 5: root.printKnowledgeTree(root); break;
                case 0: goodByeMessage(); System.exit(0);
                default: System.out.println("Invalid command.");
            }
            showMenu();
        }
    }





    private static void printAnimalNames() {
        String[] array = new String[arrayOfAnimalNames.size()];
        for (int i = 0; i < arrayOfAnimalNames.size(); i++) {
            array[i] = arrayOfAnimalNames.get(i);
        }
        Arrays.sort(array);
        for (String s : array) {
            System.out.println(" - " + s);
        }
        arrayOfAnimalNames.clear();
    }

    private static void showArrayOfFacts() {
        for (int i = arrayOfAnimalFacts.size() - 1; i >= 0; i--) {
            System.out.println(arrayOfAnimalFacts.get(i));
        }
        arrayOfAnimalFacts.clear();
    }
    public static void addToArrayOfFacts(String value) {
        arrayOfAnimalFacts.add(value);
    }
    public static void addToArrayOfAnimals(String value) {
        arrayOfAnimalNames.add(value);
    }

    private void returnAnimals() {
        System.out.println("Here are the animals I know:");
        root.returnAnimals(root);
        printAnimalNames();
    }
    private void searchForAnAnimal() {
        System.out.println("Enter the animal:");
        String input = textRecognition.animalInput();
        System.out.println("Facts about " + input + ":");
        root.searchForAnAnimal(root, input);
        showArrayOfFacts();
        if (counter == 0) System.out.println("No facts about " + input);
        resetCounter();
    }

    public static void increaseCounter() {
        counter++;
    }
    private static void resetCounter() {
        counter = 0;
    }

    private boolean guess(AnimalTreeNode node){
        if(node.isLeaf()){
            System.out.println("Is it " + node.getValue() + "?");
            endNode = node;
            return textRecognition.yesOrNoInput();
        } else {
            System.out.println(node.getValue());
            if(textRecognition.yesOrNoInput()){
                return guess(node.getYesChild());
            } else {
                return guess(node.getNoChild());
            }
        }
    }

    private void guessedCorrectly() {
        System.out.println("Yay!!! I did it! I'm so smart! :)\n");
    }

    private void guessedIncorrectly(){
        System.out.println("I give up. What animal do you have in mind?");
        String newAnimal = textRecognition.animalInput();
        specifyFact(newAnimal);
        System.out.println("Is the statement correct for " + newAnimal + "?");
        boolean isFactTrueForNewAnimal = learnedFacts(endNode.getValue(), newAnimal);
        if(isFactTrueForNewAnimal){
            endNode.insertYesChild(newAnimal);
            endNode.insertNoChild(endNode.getValue());
        } else {
            endNode.insertNoChild(newAnimal);
            endNode.insertYesChild(endNode.getValue());
        }
        endNode.setValue(questionForNode);
        System.out.println("I can distinguish these animals by asking the question:");
        System.out.println(" - " + questionForNode);
        System.out.println("Nice! I've learned so much about animals!\n");
    }

    private void specifyFact(String newAnimal) {
        question = textRecognition.compareAnimalsQuestion(endNode.getValue(), newAnimal);
        questionForNode = question[0].equals("has") ? "Does it have " + question[1] + "?" : question[0].substring(0, 1).toUpperCase() + question[0].substring(1) +
                " it " + question[1] + "?";
    }


    private void askToContinue(){
        System.out.println("Would you like to play again?");
        if(!textRecognition.yesOrNoInput()) {
            keepPlaying = false;
        }
    }


    private void goodByeMessage() {
        System.out.println("\n" + goodByeMessage[random.nextInt(goodByeMessage.length)]);
    }


    private void greetingBasedOnTime() {
        LocalTime time = LocalTime.now();
        if (time.isBefore(LocalTime.parse("18:00")))
            System.out.println(time.isBefore(LocalTime.parse("12:00")) ? "Good morning!" : "Good afternoon!");
        else System.out.println("Good evening!");
    }





    private boolean learnedFacts(String firstAnimal, String secondAnimal) {
        String first = firstAnimal.replaceFirst("a |an ", "The ");
        String second = secondAnimal.replaceFirst("a |an ", "The ");
        if (textRecognition.yesOrNoInput()) {
            System.out.println(" - " + first + " " +
                    (question[0].equals("is") ? "isn't" : question[0].equals("can") ? "can't" : "doesn't have") + " " + question[1] + ".");
            System.out.println(" - " + second + " " + question[0] + " " + question[1] + ".");
            return true;
        }
        else {
            System.out.println(" - " + first + " " + question[0] + " " + question[1] + ".");
            System.out.println(" - " + second + " " +
                    (question[0].equals("is") ? "isn't" : question[0].equals("can") ? "can't" : "doesn't have") + " " + question[1] + ".");
            return false;
        }
    }

    private void pressEnterToContinue() {
        try{System.in.read();}
        catch(Exception ignored){}
    }
}
