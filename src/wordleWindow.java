
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
//import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * wordleWindow class creates a graphical user interface (GUI) using the Java Swing library.
 * 
 * The GUI consists of a 5x5 grid of JTextFields and a JTextField at the bottom of the window. 
 * 
 * The user can input a word into the bottom JTextField and press enter to submit their guess.
 * 
 * The program checks if the word is the same length as a randomly chosen word from a word bank 
 * stored in a file named "valid-wordle-words.txt" and if the guess is valid, it will fill in the 
 * grid of JTextFields with the characters of the user's guess. 
 * 
 * The color of the JTextField will be set to green if the character matches the corresponding character
 * in the word, yellow if the character is in the word but not in the correct position, and gray if 
 * the character is not in the word.
 */
public class wordleWindow {
    //image opened upon win or loss
    final static ImageIcon winIcon = new ImageIcon("C:/Users/ckien/OneDrive/Desktop/wordle_image.png");
    final static ImageIcon lossIcon = new ImageIcon("C:/Users/ckien/OneDrive/Desktop/lossImage.png");

    //keeps track of what text box we are in. 0-29
    static int sectionNum = 0;

    static String theWord = "";  //the word to be guessed
    
    //boolean to check if the word has been guessed correctly
    static boolean guessed = false;

    //declare the list to store valid words
    static ArrayList<String> wordBank = new ArrayList<String>();

    public static void main(String[] args) {
        //calls getWord() to get a random word from the word bank
        theWord = getWord();
        System.out.println("The word is: " + theWord);
        System.out.println(wordBank.get(0));
       

        //create the window
        JFrame frame = new JFrame("Wordle");
        frame.setSize(700, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //add a border on the edges of the window
        Border border = BorderFactory.createMatteBorder( 0,60,0,60,Color.BLUE);
        frame.getRootPane().setBorder(border);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 5));
        panel.setBackground(Color.WHITE);

        frame.setLocationRelativeTo(null); // centers the frame on the screen

        //initialize blank text boxes for the guesses to be placed
        for (int i = 0; i < 30; i++) {
            JTextField textField = new JTextField(1);
            Border textBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
            textField.setBackground(Color.lightGray);
            textField.setEditable(false);
            textField.setText(null);
            textField.setHorizontalAlignment(JTextField.CENTER);
            textField.setFont(new Font("Arial", Font.BOLD, 50));
            textField.setBorder(textBorder);
            panel.add(textField);
        }
        
        //create the bottom text box
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLUE);
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setPreferredSize(new Dimension(200, 60));
        JTextField textField = new JTextField(10);
        textField.setSize(50,100);
        textField.setFont(new Font("Arial", Font.BOLD, 40));
        textField.setHorizontalAlignment(JTextField.CENTER);
        bottomPanel.add(textField);

        //add the panels to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        panel.setLayout(new GridLayout(5, 5, 5, 5)); // add some padding between the text fields
        panel.setLayout(new GridLayout(6, 6, 5, 5)); // add some padding between the text fields


        /**
        * The key listener for the text field.
        * @param e The key event.
         */
        textField.requestFocusInWindow();
        textField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                //if the user presses enter
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String userText = textField.getText();  //get the user's guess and store as a string
                    frame.dispose();
                    guessed = checkGuess(userText, theWord);  //validate the guess

                    //if the guess is valid, fill in the text boxes with the characters of the guess
                    if (validGuess(userText, theWord) && !guessed && sectionNum < 30){
                        //loop through the user's guess and fill in the text boxes
                        for(int i = 0; i < userText.length(); i++) {
                            JTextField currentTextField = (JTextField) panel.getComponent(sectionNum);
                            sectionNum++;  //increment the section number
                            currentTextField.setText(String.valueOf(userText.charAt(i)));
                            currentTextField.setBackground(determineCorrect(userText.charAt(i), i, theWord));
                            textField.setText(null);
                            frame.show();   
                            textField.requestFocusInWindow();                  
                        }
                    }
                    //if the guess is valid and the word has been guessed correctly, display a win message
                    else if(guessed){
                         JOptionPane.showMessageDialog(null, null, "Wordle", JOptionPane.INFORMATION_MESSAGE, winIcon);
                    }
                    //if the guess is invalid, display an error message
                    else if (!validGuess(userText, theWord)){
                        JOptionPane.showMessageDialog(null, "invalid guess, try again", "Wordle", JOptionPane.INFORMATION_MESSAGE, null);
                        frame.show();
                        textField.setText(null);
                        textField.requestFocusInWindow();
                    }  
                    //if the user has run out of guesses, display loss message  
                    else{
                        frame.dispose();
                        JOptionPane.showMessageDialog(null,null, "Wordle", JOptionPane.INFORMATION_MESSAGE, lossIcon);
                        frame.show();
                    }    
                        
                        
                }
            }                               
        });      
    }

    /*
     * checks if the user's guess is in valid format
     * @param userText the user's guess
     * @param word the randomly chosen word
     */
    private static boolean validGuess(String userText, String word) {
       
        if (userText.length() != 5) {
            return false;
        }

        //check if user's guess is a valid word
        else if(!(wordBank.contains(userText))){
            return false;
        }
        //check if user's guess contains only letters
        for (int i = 0; i < userText.length(); i++) {
            if (!Character.isLetter(userText.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /*
     * checks if the user's guess is correct
     * @param userText the user's guess
     * @param word the randomly chosen word
     */
    private static boolean checkGuess(String userText, String word) {
        if (userText.equals(word)) {
            return true;
        }
        return false;
    }

    /*
     * determines the color of the text box based on the user's guess
     * @param c the character of the user's guess
     * @param index the index of the character in the user's guess
     * @param theWord the randomly chosen word
     */
    private static Color determineCorrect(char c, int index, String theWord) {
        if(c == theWord.charAt(index)){
            return Color.GREEN;
        }
        if(theWord.contains(String.valueOf(c))){
            return Color.YELLOW;
        }
        else{
            return Color.GRAY;}
    }

    /*
     * gets a random word from the word bank
     */
    private static String getWord(){
        wordBank = new ArrayList<>();   //List to store valid words
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
        return wordBank.get(((int) (Math.random() * 599))+1);  //Access random word from bank
    }
}
