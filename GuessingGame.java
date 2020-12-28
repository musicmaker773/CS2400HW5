import java.util.Iterator;
import java.util.Scanner;

import TreePackage.DecisionTreeInterface;
import TreePackage.DecisionTree;

public class GuessingGame
{
    private DecisionTreeInterface<String> tree;

    Scanner key = new Scanner(System.in);

    public GuessingGame(String question, String noAnswer, String yesAnswer)
    {
        DecisionTree<String> no = new DecisionTree<>(noAnswer);
        DecisionTree<String> yes = new DecisionTree<>(yesAnswer);
        tree = new DecisionTree<>(question, no, yes);
    }

    public void play()
    {
        // TODO
        boolean again = true;

        System.out.println("Welcome to the Guessing Game!");

        while (again != false) {

            while (!tree.isAnswer()) {
                System.out.println(tree.getCurrentData());
                String answer = key.nextLine();
                if (answer.equalsIgnoreCase("yes")) {
                    tree.advanceToYes();
                }
                if (answer.equalsIgnoreCase("no")) {
                    tree.advanceToNo();
                }
            }
            if (tree.isAnswer()) {
                System.out.println("Is it a(n) " + tree.getCurrentData() + "?");
                String answer = key.nextLine();
                if (answer.equalsIgnoreCase("yes")) {
                    System.out.println("I win!");
                }
                if (answer.equalsIgnoreCase("no")) {
                    learn();
                }
            }
            System.out.println("What kind of traversal do you want to print out the tree with?: ");
            System.out.println("1 - Level Order");
            System.out.println("2 - Preorder");
            String choice = key.nextLine();
            if (choice.equals("1")) {
                Iterator<String> iterator = tree.getLevelOrderIterator();
                while(iterator.hasNext())
                {
                    System.out.print(iterator.next() + " ");
                }
                System.out.println();
            }
            else if (choice.equals("2")) {
                Iterator<String> iterator = tree.getPreorderIterator();
                while(iterator.hasNext())
                {
                    System.out.print(iterator.next() + " ");
                }
                System.out.println();
            }
            boolean answered = false;
            while (answered != true) {
                System.out.println("Do you wanna play again?");
                String playAgain = key.nextLine();
                if (playAgain.equalsIgnoreCase("yes")) {
                    answered = true;
                    tree.resetCurrentNode();
                }
                if (playAgain.equalsIgnoreCase("no")) {
                    answered = true;
                    again = false;
                }
            }

        }

    }

    public void learn()
    {
        // TODO
        System.out.println("Help build the game. Ask a question related to what you were looking for: ");
        String question = key.nextLine();
        tree.setCurrentData(question);
        System.out.println("Put a no answer: ");
        String no = key.nextLine();
        System.out.println("Put a yes answer: ");
        String yes = key.nextLine();
        tree.setResponses(no, yes);
    }
}
