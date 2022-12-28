package Dinkel.Musikman.helper;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.entities.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

	public static String formatTime(long timeInMillis) {
		long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
		long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
		long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);
		
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

	public static String getFileFormat(String name) {
		String FileFormat = "";
		for(int i=name.length()-1;i>0;i-=1) {
			if(name.charAt(i) != '.')
				FileFormat = FileFormat + name.charAt(i);
			else
				i = 0;
		}
		String reversed = "";
		for(int i=FileFormat.length()-1;i>-1;i-=1) {
			reversed = reversed + FileFormat.charAt(i);
		}
		return reversed;
	}
	
	public static String getIp() throws Exception {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine();
            return ip;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }	

	public static void sendPrivateMessage(User user, String content) {
		user.openPrivateChannel()
			.flatMap(channel -> channel.sendMessage(content))
			.queue();
	}
}