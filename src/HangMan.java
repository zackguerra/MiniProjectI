import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class HangMan {

    public static int CHANCE_LIMIT = 10;
    public static List<String> cities;

    private String city;
    private List<String> guess;
    private Scanner in;


    // loading cities data file

    static {
        File file = new File("./src/cities.txt");
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        cities = new ArrayList<String>();

        while (in.hasNextLine()) {
            cities.add(in.nextLine());
        }
    }
    /**
     * Default constructor
     */
    public HangMan(){
        in = new Scanner(System.in);
        selectCity();
    }
    /**
     * Picking random city from cities file
     */
    public void selectCity(){
        int index = new Random().nextInt(cities.size());
        city =   cities.get(index);
        guess = new ArrayList<String>();
        for(int i = 0 ; i < city.length(); i ++){
            if(city.charAt(i) != ' ')
                guess.add(" _ ");
            else
                guess.add(" - ");
        }
    }
    /**
     * @return return the typed char
     */
    public String showQuestion(){
        System.out.println("Here is que question ");
        guess.forEach(System.out::print);
        System.out.println("\n");
        System.out.print("Guess a letter: ");
        String letter = in.nextLine();
        return letter.toUpperCase();
    }
    /**
     * To start the game
     */
    public void  startGame(){
        int counter = 0;
        boolean win = false;
        String wrongLetters = "";
        while (counter < CHANCE_LIMIT && !win){
            String letter = showQuestion();
            if(city.toUpperCase().contains(letter)){
                for(int i = 0 ; i < city.length(); i ++){
                    String s=  String.valueOf(city.charAt(i));
                    if( !s.equals(" ") && s.toUpperCase().equals(letter) ){
                        guess.set(i,s);
                    }

                } // if not contains "_", you win.
                if(!guess.contains(" _ ")){
                    String winMessage = "You win!\nYou have guessed '%s' Correctly";
                    System.out.println(String.format(winMessage,city));
                    win = true;
                }

            }else{
                if (!wrongLetters.contains(letter)){
                    wrongLetters = wrongLetters.concat(letter + " ");
                    counter ++;
                }
            }
            System.out.println(String.format("You have guessed ( %d ) wrong letters: %s ",counter,wrongLetters));
            if(counter == CHANCE_LIMIT )
                System.out.println(String.format("You lose\nThe correct word was '%s'",city));
        }
    }

}