/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;
import java.util.Random;

public class Hangman extends ConsoleProgram {
	RandomGenerator getIndex=RandomGenerator.getInstance();
	HangmanLexicon lexicon=new HangmanLexicon();
	public HangmanCanvas canvas;
	private static final int chance=8; //chance is for connecting lives and switch cases from canvas class.
	int life;
	
	
	public void init(){
		canvas=new HangmanCanvas();
		add(canvas);
	}
	
    public void run() {
    	canvas.setBackground(Color.BLACK);
    	canvas.reset();
		guessTheCharacter();
    }	
    
    //this is a process of adding new characters
    private void guessTheCharacter(){
    	 life=chance;
    	//word is chosen randomly from lexicon
    	int index=getIndex.nextInt(0,lexicon.getWordCount());
    	String str=lexicon.getWord(index);
    	
    	//secret word is a beginning state of chosen word
    	//where every letter will be replaced with "-".
    	
    	String SecretWord="";
		for(int i=0; i<str.length(); i++){
			SecretWord+='-';
		}
		canvas.displayWord(SecretWord);
		
		println("The world now looks like this: " + SecretWord);
		println("You have " + life + " geusses left.");
		
		
    	
		while(life>0){
			//s is string which consists of 1 letter
			String s=readLine("Your guess:");
			
			//we should make every new letter, which will be written
			//iin lower case, upper case.
			if(s==s.toLowerCase()){
				s=s.toUpperCase();
			}
			
			//if new letter is empty, game must be continued
			if(s.length()==0){
				continue;
			}
			
			//if the letter is already written, if shouldn't be written again
			if(SecretWord.contains(s)){
				continue;
			}
			
			//if new letter is some other symbol,other than english letters,lower case
			//is already changed.
			//game should continue
			if(s.charAt(0)<'A' || s.charAt(0)>'Z'){
				println("Problem detected.Try again.");
				continue;
			}
			
			//secret word which was written with "-"'s 
			//should change on every guessed letter.which we can do with substrings
			for(int i=0; i<str.length(); i++){
				if(s.charAt(0)==str.charAt(i)){
					SecretWord=SecretWord.substring(0, i)+str.charAt(i)+SecretWord.substring(i+1);
					canvas.displayWord(SecretWord);
					}
			}
			
			if(SecretWord.contains(s)){
				println("The guess is correct.");
				println("The word now looks like this: " +SecretWord);
			}
			
			
			//if the letter isn't in the word we will have -1 lives, than we had before.
			if(!SecretWord.contains(s)){
				canvas.noteIncorrectGuess(s.charAt(0));
				life--;
				canvas.startPlaying(chance-life);
				println("There are no "+s.charAt(0)+"'s in the word.");
				println("The word now looks like this: "+SecretWord);
				println("You have "+life+" guesses left.");
			}
			
			//if life is 0 game should stop
			if(life==0){
				println("You are complitely hung.");
				println("The word was "+str);
				println("You lose.");
			}
			//if all the letters are guessed game is won.
			if(SecretWord.equals(str)){
				println("You guessed the word.");
				println("You win.");
				break;
			}
		}
    }
}
    
    
