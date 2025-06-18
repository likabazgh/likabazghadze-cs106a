import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import acm.util.*;
public class HangmanLexicon {
	private ArrayList<String> lexicon;
	public HangmanLexicon(){
		lexicon = new ArrayList<String>();

	try{
	BufferedReader rd=new BufferedReader(new FileReader("HangmanLexicon.txt"));
	while(true){
		String line=rd.readLine();
		if(line==null){
			break;
		}
		lexicon.add(line);
	}
		rd.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return lexicon.size();
	}
	
	
	/** Returns the word at the specified index. */
	public String getWord(int index) {
		return lexicon.get(index);
	}
}
