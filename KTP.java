import java.math.BigInteger;
import java.util.ArrayList;

/*
 * Author: Karrar (karrar.kazuya@gmail.com)
 * Date: 2017-7-3
 * Version: 1.0
 * Disc: A text processing class
 */
public class KTP {
	private static int percent = 0;
	private static String[] arlist = {"؟",",","."};
	private static String[] enlist = {" a "," is "," an "," and "," the "," of "," in ",".",",","?","!"};
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

	
	

	/**
	The Description of this method is to find out in percent how much what you are looking for exists in a string
	,"text" is the line of string to search in
	,"search" is the letter to search for
	@param the parameters used by this method.
	@return the value returned by this method is the number in percent of how much what you are looking for exists in the text, if returns from 0 to 100 depends how the percent, It will also return 0 if you pass a null value
	*/
	public static int Search(String text, String search){
		if(text == null || search == null)
			return 0;
		String temp = "";
		String temptext = "";
		String temp2 = "";
		String tempsearch = "";
		boolean added = false;
		// 1# check if it is same text
		temptext = text.toLowerCase();
		tempsearch = search.toLowerCase();
		if(temptext.equals(tempsearch)){
			return 100;
		}
		// 2# check if it got the whole part of the search
		if(temptext.contains(tempsearch)){
			return 90;
		}
		// 3# Handling the "..." words
		while(howManyLetter(search, "\"") >1){
			temp = cropFromTo(search, "\"", "\"",false);
			if(temp == null){
				break;
			}
			if(!text.contains(temp)){
				return 0;
			}else{
				search = search.replace("\""+temp+"\"", "");
				if(!added){
					AddToPercent(50);
					added = true;
				}
			}
			}
		// 4# removing unwanted spaces
		text = text + " ";
		search = search + " ";
		while(text.contains("  ")){
		text = text.replace("  ", " ");
		}
		if(text.indexOf(" ") == 0){
		text = text.replaceFirst(" ", "");
		}
		if(search.indexOf(" ") == 0){
		search = search.replaceFirst(" ", "");
		}
		// 5# Handling the -... words
		while(howManyLetter(search, "-") >0){
			temp = cropFromTo(search, "-", " ",false);
			if(temp == null){
				break;
			}
			if(text.contains(temp)){
				return 0;
			}
			search = search.replace("-"+temp+" ", "");
			}
		// 6# removing every useless or not needed words
		text = text.toLowerCase();
		search = search.toLowerCase();
		text = replaceAll(text, arlist," ");
		text = replaceAll(text, enlist," ");
		search = replaceAll(search, arlist," ");
		search = replaceAll(search, enlist," ");
		
		
		
		// 7# converting both strings to lists
		temptext = text;
		tempsearch = search;
		ArrayList<String> w = new ArrayList();
		ArrayList<String> s = new ArrayList();
		w = ConverToList(temptext);
		s = ConverToList(tempsearch);
		
		
		// 8# checking for similarities
		int num = 0;
		for(int x = 0;x<s.size();x++){
		for(int i = 0;i<w.size();i++){
			num = checkMistake(w.get(i),s.get(x))/s.size();
			if(num>0)
			AddToPercent(num);

			}
		}
		
		
		
		
		
		
		return percent;
		
	}
		
	private static int checkMistake(String f1,String f2){
		int val = 0;
		int val2 = 0;
		int num = 0;
		if(f2.length()<f1.length()){
			val2 = f2.length();
		}else{
			val2 = f1.length();
		}
		if(val2 == 1){
			return 20;
		}
		num = 100/val2;
			for(int i = 0; i<val2;i++){
				String s1 = f1.charAt(i)+"";
				String s2 = f2.charAt(i)+"";
				if(s1.equals(s2)){
					val = val+num;
				}else{
					val = val - num;
				}
			}
		
		if(val<60){
			return 0;
		}
		return val;
	}
	
		
		private static void AddToPercent(int num){
			if(percent != 100){
				percent = percent + num;
			}
			while(percent>100){
				percent = percent - 1;
			}
		}
	
	
	
	
	/**
	The Description of this method is to find out how many a letter exists in a string
	,"text" is the line of string to search in
	,"letter" is the letter to search for
	@param the parameters used by this method.
	@return the value returned by this method is the number of how many a letter exist in the text, if returns 0 it means there is no such letter in the text, It will also return 0 if you pass a null value
	*/
	public static int howManyLetter(String text, String letter){
		if(text == null || letter == null)
			return 0;
		int exist = 0;
		String charat = "";
		for(int i = 0;i<text.length();i++){
			charat = text.charAt(i)+"";
			if(charat.equals(letter)){
				exist = exist + 1;
			}
		}
		return exist;
	}

	
	/**
	The Description of this method is to find out how many a word exists in a string
	,"text" is the line of string to search in
	,"word" is the word to search for
	@param the parameters used by this method.
	@return the value returned by this method is the number of how many a word exist in the text, if returns 0 it means there is no such letter in the text, It will also return 0 if you pass a null value
	*/
	public static int howManyWord(String text, String word){
		if(text == null || word == null)
			return 0;
		int exist = 0;
		while(text.contains(word)){
			text = text.replaceFirst(word, "");
			exist = exist + 1;
		}
		return exist;
	}
	
	/**
	The Description of this method is to find out if a text is digit or string
	@param the parameters used by this method.
	@return the value returned by this method is either true or false, It will also return false if you pass a null value
	*/
	public static boolean isDigit(String text){
		if(text == null)
			return false;
		try{
			int x = Integer.valueOf(text);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}

	/**
	The Description of this method is to replace as much as you want in a string, "text" is the text to 
	replace in,"word" is to find and replace, "replacement" is what to replace "word" with in "text", "number" is how many existing similarities you want to replace
	@param the parameters used by this method.
	@return the value returned by this method is the string after making the replace changes, It will also return null if you pass a null value
	*/
	public static String replaceByNumber(String text,String word, String replacement, int number){
		if(text == null || word == null || replacement == null)
			return null;
		int replaced = 0;
		while(replaced != number){
			text = text.replaceFirst(word, replacement);
			replaced = replaced + 1;
		}
		
		return text;
		
	}

	
	

	/**
	The Description of this method is to crop a part of a string from long string, "text" is the text to 
	crop from,"from" is the start point, "to" is the end point "leavesides" is a boolean value if it is false then it will crop without sides else it will crop with sides
	@param the parameters used by this method.
	@return the value returned by this method is the cropped part of the string after from the start point till end point, It will also return null if you pass a null value
	*/
	public static String cropFromTo(String text,String from, String to, boolean leavesides){
		if(text == null || from == null || to == null)
			return null;
		String realfrom = from;
		String realto = to;
		if(from.equals(to)){
			text = text.replaceFirst(from, "%%>>%%ww");
					text = text.replaceFirst(to, "%%>>%%MM");
					from = "%%>>%%ww";
					to = "%%>>%%MM";
		}
		
		if(text.contains(from) && text.contains(to)){
			while(text.indexOf(from)>text.indexOf(to)){
				text = text.replaceFirst(to, "");
			}

			if(text.indexOf(from)<text.indexOf(to)){
				if(!leavesides){
					return text.substring(text.indexOf(from)+from.length(), text.indexOf(to));
				}else{
					return text.substring(text.indexOf(from), text.indexOf(to))+to;
				}
			}
		}	
		return null;
	}

	
	/**
	The Description of this method is to crop a part of a string from long string, "text" is the text to 
	crop from,"from" is the start point, "to" is the end point.
	@param the parameters used by this method.
	@return the value returned by this method is the cropped part of the string after from the start point till end point, It will also return null if you pass a null value
	*/
	public static String cropFromTo(String text,int from, String to){
		if(text == null || to == null)
			return null;
		if(text.contains(to)){
			if(0!=text.indexOf(to)){
					return text.substring(0, text.indexOf(to));
				
			}
		}	
		return null;
	}

	
	
	/**
	The Description of this method is to crop a part of a string from long string, "text" is the text to 
	crop from,"from" is the start point, "to" is the end point.
	@param the parameters used by this method.
	@return the value returned by this method is the cropped part of the string after from the start point till end point, It will also return null if you pass a null value
	*/
	public static String cropFromTo(String text,int from, int to){
		if(text == null)
			return null;
		if(text.length()>2){
			if(from<to){
					return text.substring(from, to);
				
			}
		}	
		return null;
	}

	
	
	/**
	The Description of this method is to crop a part of a string from long string, "text" is the text to 
	crop from,"from" is the start point, "to" is the end point.
	@param the parameters used by this method.
	@return the value returned by this method is the cropped part of the string after from the start point till end point, It will also return null if you pass a null value
	*/
	public static String cropFromTo(String text,String from, int to){
		if(text == null || from == null)
			return null;
		if(text.contains(from)){
			if(to>text.indexOf(from)){
					return text.substring(text.indexOf(from), to);
				
			}
		}	
		return null;
	}

	
	
	
	/**
	The Description of this method is to remove any digit from a string, "text" is the text to 
	remove digits from.
	@param the parameters used by this method.
	@return the value returned by this method is the string after removing the digits from, It will also return null if you pass a null value
	*/
	public static String getOnlyString(String text){
		if(text == null)
			return null;
		String lasttext = "";
		for(int i = 0;i<text.length()-1;i++){
			try{
				Integer.valueOf(text.charAt(i)+"");
			}catch(Exception e){
				lasttext = lasttext + text.charAt(i);
			}
		}
		return lasttext;
	}

	
	
	/**
	The Description of this method is to remove any digit from a string, "text" is the text to 
	remove digits from.
	@param the parameters used by this method.
	@return the value returned by this method is the string after removing the digits from, It will also return null if you pass a null value
	*/
	public static BigInteger getOnlyDigit(String text){
		if(text == null)
			return null;
		String lasttext = "";
		for(int i = 0;i<text.length()-1;i++){
			try{
				Integer.valueOf(text.charAt(i)+"");
				lasttext = lasttext + text.charAt(i);
			}catch(Exception e){
				// Pass
			}
		}
		return new BigInteger(lasttext);
	}

	
	
	
	/**
	The Description of this method is to remove every word in string based on a String[], "text" is the text to 
	remove words from, search is the String[] containing the words
	@param the parameters used by this method.
	@return the value returned by this method is the string after removing the words from, It will also return null if you pass a null value
	*/
	public static String removeAll(String text,String[] search){
		if(text == null || search == null)
			return null;
		String lasttext = "";
		for(int i = 0;i<search.length-1;i++){
			text = text.replace(search[i].toString(), "");
		}
		return text;
	}
	
	
	/**
	The Description of this method is to replace every word in string based on a String[] which is "search" with "replacement", "text" is the text to 
	remove words from, "search" is the String[] containing the words,"replacement" is the word to replace with
	@param the parameters used by this method.
	@return the value returned by this method is the string after replacing the words from, It will also return null if you pass a null value
	*/
	public static String replaceAll(String text,String[] search,String replacement){
		if(text == null || search == null || replacement == null)
			return null;
		String lasttext = "";
		for(int i = 0;i<search.length-1;i++){
			text = text.replace(search[i].toString(), replacement);
		}
		return text;
	}
	
	
	/**
	The Description of this method is to convert every word in string to ArrayList, "text" is the text to 
	convert words from.
	@param the parameters used by this method.
	@return the value returned by this method is the list after adding each word to, It will also return null if you pass a null value
	*/
	public static ArrayList ConverToList(String text){
		if(text == null)
			return null;
		String temptext = "";
		text = text+" ";
		ArrayList<String> list = new ArrayList();
		while(text.contains("  ")){
			text = text.replace("  ", " ");
			}
		
		if(text.indexOf(" ") == 0){
			text = text.replaceFirst(" ", "");
		}

		while(text.contains(" ")){
			
	        temptext = cropFromTo(text, 0, " ");
			text = text.replace(temptext+" ", "");
			list.add(temptext);
		}

		return list;
	}

	
	
	
}
