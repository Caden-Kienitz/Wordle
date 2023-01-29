import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Wordle {
    //add together two integers
    public static void main(String[] args) {
        ArrayList<String> wordBank = new ArrayList<>();   //List to store valid words
        ArrayList<Character> incorrectList = new ArrayList<>();
        File readIn = new File("valid-wordle-words.txt");
        Scanner bankScan = null;
        try {
            bankScan = new Scanner(readIn);
            //read in data from file
            while(bankScan.hasNext()){
                wordBank.add(bankScan.nextLine());
            }

        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        } finally {
            bankScan.close();
        }

        boolean guessed = false;   //if word is guessed correctly
        String chosenWord = wordBank.get(((int) (Math.random() * 599))+1);  //Access random word from bank
        char[] word = chosenWord.toCharArray();   //Convert word to array
        Set<Character> wordList = new HashSet<Character>();
        Scanner getGuess = new Scanner(System.in);   //Scan for user guess
        char[] guessArray= null;   //Array to convert user guess
        int tracker = 0;

        //Set colors for printout
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_RESET = "\u001B[0m";

        wordConvert(wordList, word);
        boolean validWord;

        while(tracker < 6 && !guessed){
            tracker ++;
            String userGuess = null;   //Reset user guess
            validWord = false;
            while(!validWord) {
                System.out.println("Enter guess: ");
                System.out.println("Letters not in word: " + incorrectList);
                userGuess = getGuess.nextLine();

                if (userGuess.length() < 5 || userGuess.length() > 5) {
                    System.out.println("Guess must be 5 letters");
                }
                else{
                    validWord = true;
                }
            }
            guessArray = userGuess.toCharArray();

            for(int x = 0; x <=4; x++){
                if(word[x] == guessArray[x]){
                    System.out.print(ANSI_GREEN + "x" + ANSI_RESET);
                }
                else if(wordList.contains(guessArray[x])){
                    System.out.print(ANSI_YELLOW + "x" + ANSI_RESET);
                }
                else{
                    System.out.print("x");
                    incorrectList.add(guessArray[x]);
                }
            }
            System.out.println();
            if(chosenWord.compareTo(userGuess) == 0){
                guessed = true;
            }
            System.out.println();
        }
        if(guessed){
            System.out.println("Correct! You guessed correctly in " + tracker + " tries");
        }
        else{
            System.out.println("You did not guess the word, the word was " + chosenWord);
        }
        getGuess.close();
    }

    private static void wordConvert(Set<Character> wordList, char[] word){
        for (int i = 0; i < word.length; i++){
            wordList.add(word[i]);
        }
    }
}
