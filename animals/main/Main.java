package animals.main;

public class Main {
    public static void main(String[] args) {
        Computer computer;
        if (args.length == 2) computer = new Computer(args[1]);
        else if (args.length == 3) computer = new Computer(args[2]);
        else computer = new Computer();
        computer.startGame();
    }
}
