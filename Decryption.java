import java.util.*;
import java.io.*;

//Code goes through 0 bitshift (default string) to 25 bitshift (in that order)
//Goes through Caesar Cipher and decrpyts it and prints the correct value

public class Decryption {
	private File file = new File("string.txt");
	private ArrayList<String> words = new ArrayList<String>();
	private ArrayList<String> confirmed = new ArrayList<String>();
	private ArrayList<String> arr = new ArrayList<String>();
	
	public void decodeString(String str) {
		String cipher = "";
		for(int in = 26; in>0; in--) {
			int num = in;
			for(int i = 0; i < str.length(); i++) {
				num = in;
				String g = "" + str.charAt(i);
				if(g.equals("") || g.equals(" ")) {
					cipher += " ";
					continue;
				}
				char c = str.charAt(i);
				int max = 0;
				char defChar;
				if(c>='a' && c<='z'){
					max = 122;
					defChar = 'a';
				} else {
					max = 90;
					defChar = 'A';
				}
				
				char testing = c;
				for(int example=in; example>0; example--) {
					if((int) testing == max) {
						testing = defChar;
					} else {
						testing+=1;
					}
						
				}
				cipher += testing;
				num = in;
			}
			arr.add(cipher);
			cipher = "\n";
		}
	}
	
	public void pullFromDictionary() {
		try {
			BufferedReader brc = new BufferedReader(new FileReader("Dictionary.txt"));
			String line = "";
			while ((line = brc.readLine()) != null) {
				words.add(line);
			}
			brc.close();
		} catch (Exception e) {
			System.out.println("Something went wrong...");
		}
	}
	
	public void readLinesFromEncyptedFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			String cipher = "";
			while ((line = br.readLine()) != null) {
				decodeString(line);
			}
			br.close();
		} catch (Exception e) {
			System.out.println("Something went wrong...");
		}
	}
	
	public void checkEncryptedStrings() {
		for(int i = 0; i<arr.size(); i++) {
			String g = arr.get(i);
			for(String a : words) {
				StringTokenizer st = new StringTokenizer(g);
				while(st.hasMoreTokens()) {
					if(st.nextToken().equals(a)) {
						if(!(confirmed.contains(g))) {
							String l = g;
							l = l.replaceAll("\n", "");
							confirmed.add(g);
						}
					}
				}
			}
		}
	}
	
	public void outputConfirmedStrings() {
		for(String g : confirmed) {
			g = g.replaceAll("\n", "");
			System.out.println("String Decoded: " + g);
		}
	}
	
	public void debugOutput() {
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
	
	public static void main(String args[]) {
		Decryption dec = new Decryption();
		dec.readLinesFromEncyptedFile();
		dec.pullFromDictionary();
		dec.checkEncryptedStrings();
		dec.outputConfirmedStrings();
		dec.debugOutput();
	}
}