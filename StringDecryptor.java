import java.util.*;

public class StringDecryptor implements Decryptor{
	
	@Override
	public ArrayList<String> decryptString(String str) {
		ArrayList<String> arr = new ArrayList<String>();
		String cipher = "";
		for(int in = 26; in>0; in--) {
			for(int i = 0; i < str.length(); i++) {
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
			}
			arr.add(cipher);
			cipher = "\n";
		}
		return arr;
	}
}
