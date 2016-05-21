import java.util.*;
import java.io.*;

//Code goes through 0 bitshift (default string) to 25 bitshift (in that order)
//Goes through Caesar Cipher and decrpyts it and prints the correct value

//Decrypted Strings: output.txt
//Dictionary File: Dictionary.txt

public class Decryption {
	private static File file;
	private static File dictionaryFile;
	private static ArrayList<String> words = new ArrayList<String>();
	private static ArrayList<String> confirmed = new ArrayList<String>();
	private static ArrayList<String> arr = new ArrayList<String>();
	private static StringDecryptor decryptor = new StringDecryptor();
	private static boolean debug = false;
	
	public static void main(String args[]) {
		try {
			if(args[1].equals("true")) {
				debug = true;
			}
		} catch (Exception e){}
		try {
			file = new File(args[0]);
		} catch (Exception e) {
			System.out.println("File name not found, please do");
			System.out.println("java Decryption file.txt Dictonary.txt");
			System.exit(0);
		}
		try {
			dictionaryFile = new File(args[1]);
		} catch (Exception e) {
			System.out.println("Dictionary file name not found, please do");
			System.out.println("java Decryption file.txt Dictionary.txt");
			System.exit(0);
		}
		Decryption dec = new Decryption();
		dec.readLinesFromEncyptedFile(); //Decrypts lines from file
		dec.pullFromDictionary();
		dec.checkDecryptedStrings(); //Checks Strings non-strictly
		dec.doubleCheckDecryptedStrings(); //Checks Strings strictly
		dec.outputConfirmedStrings(); //Outputs string to a file
		//dec.debugOutput(); //Debug Output (Testing)
	}
	
	public void pullFromDictionary() {
		try {
			BufferedReader brc = new BufferedReader(new FileReader(dictionaryFile));
			String line = "";
			while ((line = brc.readLine()) != null) {
				words.add(line.toLowerCase());
			}
		} catch (Exception e) {
			System.out.println("Dictionary file not found");
		}
	}
	
	public void readLinesFromEncyptedFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = br.readLine()) != null) {
				for(String g : decryptor.decryptString(line)) { //Decrypts string
					arr.add(g);
				}
			}
			br.close();
		} catch (Exception e) {
			System.out.println("File not found");
		}
	}
	
	public void checkDecryptedStrings() {
		for(int i = 0; i<arr.size(); i++) {
			String g = arr.get(i);
			for(String a : words) {
				StringTokenizer st = new StringTokenizer(g);
				while(st.hasMoreTokens()) {
					String ge = st.nextToken();
					if(ge.toLowerCase().equals(a.toLowerCase())) {
						if(!(confirmed.contains(g))) {
							String l = g;
							l = l.replaceAll("\n", ""); //Removes all new lines from string
							confirmed.add(g);
						}
					}
				}
			}
		}
	}
	
	public void doubleCheckDecryptedStrings() {
		ArrayList<String> tempArr = new ArrayList<String>();
		for(String g : confirmed) {
			int correct = 0; //Correct words
			int max = 0; //Max words
			for(String a : words) {
				StringTokenizer st = new StringTokenizer(g);
				max = st.countTokens();
				while(st.hasMoreTokens()) {
					String temp = st.nextToken();
					if(temp.toLowerCase().equals(a.toLowerCase())) {
						correct++; //Correct word +1
					}
				}
			}
			if(((double) correct/max) < 0.49d) {
				tempArr.add(g); //Adds to temp arraylist to remove from main arraylist
				//this is due to a ConcurrentModificationException
			} else if (correct == 0) {
				tempArr.add(g); //Adds to temp arraylist to remove from main arraylist
				//this is due to a ConcurrentModificationException
			}
		}
		
		for(String g : tempArr) {
			confirmed.remove(g);
		}
	}
	
	public void outputConfirmedStrings() {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter("output.txt"));
			for(String g : confirmed) {
				g = g.replaceAll("\n", "");
				pw.write(g);
				pw.write("\n");
			}
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void debugOutput() {
		if(debug){
			try {
				PrintWriter pw = new PrintWriter(new FileWriter("doutput.txt"));
				for(String g : arr) {
					g = g.replaceAll("\n", "");
					pw.write(g);
					pw.write("\n");
				}
				pw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}