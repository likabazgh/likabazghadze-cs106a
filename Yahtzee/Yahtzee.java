import java.util.Arrays;

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {

	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int[] dice = new int[N_DICE];
    int [][] scoreingTable;
    int total=0;
    
    
	private void playGame() {
		//create matrix similar to the scoring table
		scoreingTable=new int [N_CATEGORIES][nPlayers];
		//cyrcle for every category that is on the table.after all categories are full game ends.
		for (int i = 0; i < N_SCORING_CATEGORIES; i++) {
			for (int j = 0; j < nPlayers; j++) {
				firstRoll(j); //first roll
				secondThirdRoll(j); //second and third rolls
				
				int category=display.waitForPlayerToSelectCategory();
			while(true){
				if(categoryIsempty(category, j)==false){
					display.printMessage("It is already chosen");
					category=display.waitForPlayerToSelectCategory();
				}
				else break;
			}
				if(selectCategory(category)){ //if selected category is suitable for he dices, score should be added to the table.
					setScores(j, category);
				}
				else{
					scoreingTable [category-1][j]=0; //if not, the score is 0.
					display.updateScorecard(category, j+1, 0);
				}
			}
		}
		//after game ends upper and lower scores should also add on the table
		for(int n=0; n<nPlayers; n++){
			upperAndLowerScores(n);
		}
		winnerIs(); //deciding winner
	}

	//starting roll
	private void firstRoll(int j) {
		display.printMessage(playerNames[j] + " 's turn. Click /Roll Again/ to roll dice");
		display.waitForPlayerToClickRoll(j + 1);
		//every dice should be randomised
		for (int n = 0; n < N_DICE; n++) {
			dice[n] = rgen.nextInt(1, 6);
		}
		display.displayDice(dice);
	}

	private void secondThirdRoll(int j) {
		//this two rolls aren't different,so the method is the same
		for (int k = 0; k < 2; k++) {
			display.printMessage("Select the dice you wish to re-roll and click roll again.");
			display.waitForPlayerToSelectDice();
			if(diceIsNotSelected()){
				break;
			}
			//every dice that is selected should be randomised again
			for (int m = 0; m < N_DICE - 1; m++) {
				if (display.isDieSelected(m)) {
					dice[m] = rgen.nextInt(1, 6);
				}
			}
			display.displayDice(dice);
		}
	}
	
	
	//after rolls are over,category should be selected
	private boolean selectCategory(int category){
		Arrays.sort(dice);
	
		if(isOneOfTheSixCategories(category)){ //if dices are suitable for one of the first six categories return true
			return true;
		}
		
		if(threeIsSame(category)){ //if three from five dice is the same
			return true;
		}
		
		if(fourDiceIsSame(category)){ //if four of them are the same
			return true;
		}
		
		if(threeAndTwoIsSame(category)){ //if first three and last two, or first two and last three are same
			return true;
		}
		
		if(fourIsFollowingNumbers(category)){ //if dices are 1234, 2345, or 3456
			return true;
		}
		
		if(allIsFollowingNumbers(category)){ //if dices are either 12345 or 23456
			return true;
		}
		
		if(allIsTheSame(category)){ // if all the dices are the same
			return true;
		}
		if(category==CHANCE){ //every combination is accepted
			return true;
		}
		 return false;
		}
		

	//after categories are checked, suitable scoring is next
	private void setScores(int j,int category){
		int score=0;
		
		//if is ones, twos, ... sixes, score is given:
		if(category==ONES || category==TWOS || category==THREES || category==FOURS || category==FIVES || category==SIXES){
			//every dice, that is the same as their categories names, are added whenever they are found in the list
			for(int i=0; i<N_DICE; i++){
				if(dice[i]==category){
					score+=dice[i];
				}
			}
		}
		
		//if is one of these two, score is every dice summed
		if(category==THREE_OF_A_KIND || category==FOUR_OF_A_KIND){
			for(int i=0; i<N_DICE; i++){
				score+=dice[i];
			}
		}
		
		if(category==FULL_HOUSE){
			score=25;
		}
		
		if(category==SMALL_STRAIGHT){
			score=30;
		}
		
		if(category==LARGE_STRAIGHT){
			score=40;
		}
		
		if(category==YAHTZEE){
			score=50;
		}
		
		if(category==CHANCE){
			for(int i=1; i<N_DICE; i++){
				score+=dice[i];
			}
		}
		
		scoreingTable [category-1][j]=score;
		display.updateScorecard(category, j+1, score);
		countTotal(category, j, score);
	}
	
	//score in TOTAL category should be updated after every new score
	private void countTotal(int category, int j, int score){
		int total=0;
		for(int i=0; i<N_CATEGORIES; i++){
			if(scoreingTable[i][j]!=0){
				total+=scoreingTable[i][j];
			}
		}
		display.updateScorecard(TOTAL, j+1, total);
	}
	
	//upper and lower score categories should be updated after the game ends.
	private void upperAndLowerScores(int j){
		int upperScore=0;
		int lowerScore=0;
		int bonusScore=0;
		//upper score is scores summed from the begining to sixes category, which is 5 in matrix
		for(int i=0; i<SIXES; i++){
			upperScore+=scoreingTable[i][j];
		}
		
		
		display.updateScorecard(UPPER_SCORE, j+1, upperScore);
		//if upper score is more than 63, bonus score should be added and if not bonus score is 0.
		if(upperScore>63){
			bonusScore=35;
		} else{
			bonusScore=0;
		}
		display.updateScorecard(UPPER_BONUS, j+1, bonusScore);
		
		//lower score starts from threeofakind category and ends at chance category.
		for(int i=THREE_OF_A_KIND; i<CHANCE; i++){
			lowerScore+=scoreingTable[i][j];
		}
		display.updateScorecard(LOWER_SCORE, j+1, lowerScore);
	}
	
	//deciding which player is the winner
	private void winnerIs(){
		int highest=0;
		int winner=0;
		for(int n=0; n<nPlayers; n++){
			if(highest<scoreingTable[TOTAL-1][n]){
				highest=scoreingTable[TOTAL-1][n];
				winner=n;
			}
		}
		display.printMessage("Winner is " +playerNames[winner]+ "Winning score is " + scoreingTable[TOTAL-1][winner]);
	}
	
	
	//if dice is not selected,category can be chosen
	private boolean diceIsNotSelected(){
		for(int i=0; i<N_DICE; i++){
			if(display.isDieSelected(i)){
				return false;
			}
		}
		return true;
	}
	
	private boolean categoryIsempty(int category, int j){
		if(scoreingTable[category-1][j]==0){
			return true;
		} else{
			return false;
		}
	}
	
	private boolean isOneOfTheSixCategories(int category){
	if(category==ONES || category==TWOS || category==THREES || category==FOURS || category==FIVES || category==SIXES){
		for(int i=1; i<N_DICE; i++){
			if(dice[i]==category){
		return true;
	}
	}
	}
	return false;
	}
	
	//if i, i+1, i+2 is the same the category is threeofakind
	private boolean threeIsSame(int category){
	if(category==THREE_OF_A_KIND){
		for(int i=0; i<N_DICE-2; i++){
			if(dice[i]==dice[i+1] && dice[i+1]==dice[i+2]){
				return true;
			}
		}
	}
		return false;
	}
	
	//if i, i+1, i+2, i+3 is the same category is relevant
	private boolean fourDiceIsSame(int category){
		if(category==FOUR_OF_A_KIND){
		for(int i=0; i<N_DICE-3; i++){
			if(dice[i]==dice[i+1] && dice[i+1]==dice[i+2] && dice[i+2]==dice[i+3]){
				return true;
			}
		}
		}
		return false;
	}
	
	//if three and two or two and three are the same
	private boolean threeAndTwoIsSame(int category){
		if(category==FULL_HOUSE){
		if(firstThreeAndLastTwoIsSame() || firstTwoAndLastThreeIsSame()){
			return true;
		}
		}
		return false;
	}
	
	private boolean firstThreeAndLastTwoIsSame(){
		int i=0;
			if(dice[i]==dice[i+1] && dice[i+1]==dice[i+2] && dice[i+3]==dice[i+4] && dice[i+2]!=dice[i+3]){
				return true;
			}
		
		return false;
	}
	
	private boolean firstTwoAndLastThreeIsSame(){
		int i=0;
			if(dice[i]==dice[i+1] && dice[i+2]==dice[i+3] && dice[i+3]==dice[i+4] && dice[i+1]!=dice[i+2]){
				return true;
			}
		
		return false;
	}
	
	//this counts how many of them are following, if it's 4 then is relevant for this category
	private boolean fourIsFollowingNumbers(int category){
		if(category==SMALL_STRAIGHT){
			int count=1;
		for(int i=0; i<N_DICE-1; i++){
			if(dice[i+1]==dice[i]+1){
				count+=1;
			}
			if(count==4){
				return true;
			}
		}
		}
		return false;
	}
	
	//if count is 5, then is relevant
	private boolean allIsFollowingNumbers(int category){
		if(category==LARGE_STRAIGHT){
			int count=1;
		for(int i=0; i<N_DICE-1; i++){
			if(dice[i+1]==dice[i]+1){
				count+=1;
			}
			if(count==5){
				return true;
			}
		}
		}
		return false;
	}
	
	//if i, i+1, ... i+4 are the same, category is yahtzee
	private boolean allIsTheSame(int category){
		if(category==YAHTZEE){
		for(int i=1; i<N_DICE-1; i++){
			if(dice[i+1]==dice[i]){
				return true;
			}
		}
		}
		return false;
	}
}
