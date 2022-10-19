package animals.main;


import animals.utilities.Statistics;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnimalTreeNode {
    public String value;
    public AnimalTreeNode yesNode = null;
    public AnimalTreeNode noNode = null;
    @JsonIgnore
    public AnimalTreeNode parentNode = null;
    @JsonIgnore
    int depth = 0;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AnimalTreeNode getYesChild() {
        return this.yesNode;
    }

    public void setYesChild(AnimalTreeNode yesNode) {
        this.yesNode = yesNode;
    }

    public AnimalTreeNode getNoChild() {
        return this.noNode;
    }

    public void setNoChild(AnimalTreeNode noNode) {
        this.noNode = noNode;
    }

    public AnimalTreeNode(String value){
        this.value = value;
    }

    public AnimalTreeNode(){}

    public void insertYesChild(String value){
        setYesChild(new AnimalTreeNode(value));
    }
    
    public void insertNoChild(String value){
        setNoChild(new AnimalTreeNode(value));
    }

    public void setParentNode(AnimalTreeNode parentNode) {
        this.parentNode = parentNode;
    }

    public AnimalTreeNode getParentNode() {
        return parentNode;
    }

    @JsonIgnore
    public boolean isLeaf() {
        return (yesNode == null) && (noNode == null);
    }
    @JsonIgnore
    public void returnAnimals(AnimalTreeNode node) {
        if (node.isLeaf()) Computer.addToArrayOfAnimals(node.value.replaceFirst("a |an ", ""));
        else {
            returnAnimals(node.yesNode);
            returnAnimals(node.noNode);
        }
    }
    @JsonIgnore
    public void searchForAnAnimal(AnimalTreeNode node, String input) {
        if (!node.isLeaf()) {
            setParentTrees(node);
            searchForAnAnimal(node.getNoChild(), input);
            searchForAnAnimal(node.getYesChild(), input);
        }
        else if (node.getValue().equals(input)) {
            returnFactsAboutAnimal(node);
            Computer.increaseCounter();
        }
    }
    @JsonIgnore
    private void returnFactsAboutAnimal(AnimalTreeNode node) {
        if (node.getParentNode() != null) {
            if (node == node.getParentNode().getNoChild()) {
                if (node.getParentNode().value.matches("Can it .*"))
                    Computer.addToArrayOfFacts(" - It can't " + node.getParentNode().value.replaceAll("Can it ", "").replaceAll("\\?", "."));
                else if (node.getParentNode().value.matches("Is it .*"))
                    Computer.addToArrayOfFacts(" - It isn't " + node.getParentNode().value.replaceAll("Is it ", "").replaceAll("\\?", "."));
                else if (node.getParentNode().value.matches("Does it have .*"))
                    Computer.addToArrayOfFacts(" - It doesn't have " + node.getParentNode().value.replaceAll("Does it have ", "").replaceAll("\\?", "."));
            }
            if (node == node.getParentNode().getYesChild()) {
                if (node.getParentNode().value.matches("Can it .*"))
                    Computer.addToArrayOfFacts(" - It can " + node.getParentNode().value.replaceAll("Can it ", "").replaceAll("\\?", "."));
                else if (node.getParentNode().value.matches("Is it .*"))
                    Computer.addToArrayOfFacts(" - It is " + node.getParentNode().value.replaceAll("Is it ", "").replaceAll("\\?", "."));
                else if (node.getParentNode().value.matches("Does it have .*"))
                    Computer.addToArrayOfFacts(" - It has " + node.getParentNode().value.replaceAll("Does it have ", "").replaceAll("\\?", "."));
            }
            returnFactsAboutAnimal(node.getParentNode());
        }
    }
    @JsonIgnore
    private void setParentTrees(AnimalTreeNode node) {
        if (!node.isLeaf()) {
            node.yesNode.setParentNode(node);
            node.noNode.setParentNode(node);
        }
    }

    @JsonIgnore
    public void checkTotalNumberOfNodes(AnimalTreeNode node) {
        Statistics.addTotalNumberOfNodes();
        if (!node.isLeaf()) {
            node.getYesChild().depth = node.depth + 1;
            node.getNoChild().depth = node.depth + 1;
            checkTotalNumberOfNodes(node.getNoChild());
            checkTotalNumberOfNodes(node.getYesChild());
            Statistics.addTotalNumberOfStatements();
            Statistics.increaseCurrentDepth(node.depth);
        }
        else {
            Statistics.addTotalNumberOfAnimals();
            Statistics.increaseCurrentDepth(node.depth);
            Statistics.setMinimalAnimalDepth();
            Statistics.setTotalAnimalDepth();
        }
    }
    @JsonIgnore
    public void printKnowledgeTree(AnimalTreeNode node) {
        System.out.println(node.getValue());
        if (!node.isLeaf()) {
            printKnowledgeTree(node.getYesChild());
            printKnowledgeTree(node.getNoChild());
        }
    }
}