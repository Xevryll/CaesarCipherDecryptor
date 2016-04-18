import java.util.*;
import java.io.*;

//Code goes through 0 bitshift (default string) to 25 bitshift (in that order)
//Goes through Caesar Cipher and decrpyts it and prints the correct value

public class Decryption {
	public static void main(String args[]) {
		
		//Decrypts all Information from String.txt
		File file = new File("string.txt");
		ArrayList<String> words = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			String cipher = "";
			ArrayList<String> arr = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				//System.out.println("LINE: " + line);
				for(int in = 26; in>0; in--) {
					int num = in;
					for(int i = 0; i < line.length(); i++) {
						num = in;
						String g = "" + line.charAt(i);
						if(g.equals("") || g.equals(" ")) {
							cipher += " ";
							continue;
						}
						char c = line.charAt(i);
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
			
			//Grabs all the words from the dictionary
			BufferedReader brc = new BufferedReader(new FileReader("Dictionary.txt"));
			while ((line = brc.readLine()) != null) {
				words.add(line);
			}
			
			//Checks to see if decrypted strings are ACTUALLY in the dictionary
			ArrayList<String> confirmed = new ArrayList<String>();
			for(int i = 0; i<arr.size(); i++) {
				String g = arr.get(i);
				//System.out.println("WORDS: " + g);
				for(String a : words) {
					//System.out.println("COMPARER: " + a);
					StringTokenizer st = new StringTokenizer(g);
					while(st.hasMoreTokens()) {
						if(st.nextToken().equals(a)) {
							if(!(confirmed.contains(g))) {
								String l = g;
								l = l.replaceAll("\n", "");
								//System.out.println("Decoded String: " + l);
								confirmed.add(g);
								//System.out.println();
							//System.out.println("STRING: " + g);
							//System.out.println("TEST STRING: " + a);
							} else {
								String l = g;
								l = l.replaceAll("\n", "");
								//System.out.println("Preexisting String: " + l);
								//System.out.println("Skipping it...");
								//System.out.println();
							}
						} else {
							//System.out.println(g + " Denied");
						}
					}
				}
			}
			
			//Old printwriter rip
			//Now prints out all correct decrypted strings
			try {
				//PrintWriter pw = new PrintWriter(new FileWriter("output.txt"));
				for(String g : confirmed) {
					g = g.replaceAll("\n", "");
					System.out.println("String Decoded: " + g);
					/*g = g.replaceAll("\n", "");
					pw.write(g);
					pw.write("\n"); */
				}
				//pw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}	
			
			//Outputs all of the decrypted strings to doutput.txt
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
		} catch (Exception e) {
			System.out.println("Something went wrong...");
			e.printStackTrace();
		}
	}
}