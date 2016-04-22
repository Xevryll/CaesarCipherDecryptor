import java.util.*;
import java.io.*;

//Code goes through 0 bitshift (default string) to 25 bitshift (in that order)
//Goes through Caesar Cipher and decrpyts it and prints the correct value

//Decrypted Strings: output.txt
//Dictionary File: Dictionary.txt

public class Decryption {
	private static File file;
	private static ArrayList<String> words = new ArrayList<String>();
	private static ArrayList<String> confirmed = new ArrayList<String>();
	private static ArrayList<String> arr = new ArrayList<String>();
	private static StringDecryptor decryptor = new StringDecryptor();
	
	public static void main(String args[]) {
		try {
			file = new File(args[0]);
		} catch (Exception e) {
			System.out.println("File name not specified, please do");
			System.out.println("java Decryption file.txt");
			System.exit(0);
		}
		Decryption dec = new Decryption();
		dec.readLinesFromEncyptedFile();
		dec.pullFromDictionary();
		dec.checkDecryptedStrings();
		dec.outputConfirmedStrings();
		dec.debugOutput();
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
			System.out.println("Something went wrong...d");
		}
	}
	
	public void readLinesFromEncyptedFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = br.readLine()) != null) {
				for(String g : decryptor.decryptString(line)) {
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