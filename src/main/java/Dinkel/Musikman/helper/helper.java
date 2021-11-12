package Dinkel.Musikman.helper;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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
	
	public static boolean isURL(String StringUrl) {
		try {
			URL url = new URL(StringUrl);
			url.toURI();
			return true;
		}catch(URISyntaxException | MalformedURLException e) {
			return false;
		}
	}
	
	public static String[] getSpotify(String StringUrl) {
		if(!isURL(StringUrl)) {
			return null;
		}
		if(StringUrl.contains("https://open.spotify.com/")) {
			String type = null;
			
			StringUrl = StringUrl.replace("https://open.spotify.com/", "");
			if(StringUrl.contains("track/")) {
				StringUrl = StringUrl.replace("track/", "");
				type = "track";
			}
			if(StringUrl.contains("playlist/")) {
				StringUrl = StringUrl.replace("playlist/", "");
				type = "playlist";
			}
			String[] split = StringUrl.split("\\?si=");
			return new String[] {split[0], type};
		}
		return null;
	}
}