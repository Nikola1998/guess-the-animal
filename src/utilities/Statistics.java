package animals.utilities;

import animals.main.AnimalTreeNode;

public class Statistics {
    private static String rootNode = "";
    private static int totalNumberOfNodes = 0;
    private static int totalNumberOfAnimals = 0;
    private static int totalNumberOfStatements = 0;
    private static int heightOfTheTree = 0;
    private static int currentDepth = 0;
    private static int minimumAnimalDepth = 0;
    private static int totalAnimalDepth = 0;
    private static double averageAnimalDepth = 0.0;

    public static void showStatistics(AnimalTreeNode root) {
        calculateStatistics(root);
        System.out.println("The Knowledge Tree stats\n" +
                "\n" +
                "- root node                    " + rootNode +
                "\n- total number of nodes        " + totalNumberOfNodes +
                "\n- total number of animals      " + totalNumberOfAnimals +
                "\n- total number of statements   " + totalNumberOfStatements +
                "\n- height of the tree           " + heightOfTheTree +
                "\n- minimum animal's depth       " + minimumAnimalDepth +
                "\n- average animal's depth       " + averageAnimalDepth);
    }





    private static void calculateStatistics(AnimalTreeNode root) {
        resetStatistics();
        rootNode = getRootNodeValue(root);
        root.checkTotalNumberOfNodes(root);
        for (int i = 1; i < totalNumberOfNodes; i *= 2) {
            heightOfTheTree++;
        }
        averageAnimalDepth = 1.0 * totalAnimalDepth / totalNumberOfAnimals;
    }
    private static void resetStatistics() {
        rootNode = "";
        totalNumberOfNodes = 0;
        totalNumberOfAnimals = 0;
        totalNumberOfStatements = 0;
        heightOfTheTree = 0;
        currentDepth = 0;
        minimumAnimalDepth = 0;
        totalAnimalDepth = 0;
        averageAnimalDepth = 0.0;

    }
    private static String getRootNodeValue(AnimalTreeNode node) {
        if (node.value.matches("Can it .*"))
            return ("It can " + node.value.replaceAll("Can it ", "").replaceAll("\\?", "."));
        else if (node.value.matches("Is it .*"))
            return ("It is " + node.value.replaceAll("Is it ", "").replaceAll("\\?", "."));
        else
            return ("It has " + node.value.replaceAll("Does it have ", "").replaceAll("\\?", "."));
    }
    public static void addTotalNumberOfNodes() {
        totalNumberOfNodes++;
    }
    public static void addTotalNumberOfAnimals() {
        totalNumberOfAnimals++;
    }
    public static void addTotalNumberOfStatements() {
        totalNumberOfStatements++;
    }
    public static void setMinimalAnimalDepth() {
        if (minimumAnimalDepth == 0 || currentDepth < minimumAnimalDepth) {
            minimumAnimalDepth = currentDepth;
        }
    }
    public static void setTotalAnimalDepth() {
        totalAnimalDepth += currentDepth;
    }
    public static void increaseCurrentDepth(int depth) {
        if (depth > currentDepth) currentDepth = depth;
    }
}
