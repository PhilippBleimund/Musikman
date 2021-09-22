package Dinkel.Musikman.helper;

import java.net.URI;
import java.net.URISyntaxException;

public class helper {
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	public static boolean isURL(String url) {
		try {
			new URI(url);
			return true;
		}catch(URISyntaxException e) {
			return false;
		}
	}
}
