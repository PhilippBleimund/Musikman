package Dinkel.Musikman;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Information {

	public static String OS = System.getProperty("os.name");
	
	public static String TokenMusikMan = "ODg4NDQwMjc5NjcyMTgwNzY2.YUSufQ.IxTf_QecGnEyQz8FBef6ijSniI8";
	
	//public static String TokenPhilippMusikMan = "NDA2NTQwNzAxNzYyMDYwMzE4.X_n6GQ.GgbJhlYASP1X8zuuJ687NDUJtPY";
	
	public static String TokenMusikFrau = "OTU0NDQ4MjMzMzE5NTI2NDMw.YjTRLw.BX7q8G3n17ale7_4cwlUMiJ8xBY";
	
	public static long[] admins = new long[]{
		406540701762060318l,	//Philipp Bleimund
		395012173702758411l		//Simon Krampe
	};
	
	public static boolean isAdmin(long UserId) {
		for(int i=0;i<admins.length;i++) {
			if(UserId == admins[i]) {
				return true;
			}
		}
		return false;
	}
	
	public static File linuxCustomSounds = new File("/home/pi/Music/customSounds");
	public static File windowsCustomSounds = new File("C:\\Users\\Philipp Bleimund\\Music\\Musikman");
	public static File getCustomSounds() {
		if(OS.indexOf("Windows") >= 0) {
			String formated = windowsCustomSounds.getAbsolutePath().replace('\\', '/');
			return new File(formated);
		}else if(OS.contains("Linux")) {
			return linuxCustomSounds;
		}
		return null;
	}
	
	public static File linuxHentaiImages = new File("/home/pi/Pictures/hentaiImages/links.json");
	public static File windowsHentaiImages = new File("C:\\Users\\Philipp Bleimund\\Desktop\\DiscordChatExporter\\Chats\\hanime.tv\\links.json");
	public static File getHentaiImages() {
		if(OS.indexOf("Windows") >= 0) {
			String formated = windowsHentaiImages.getAbsolutePath().replace('\\', '/');
			return new File(formated);
		}else if(OS.contains("Linux")) {
			return linuxHentaiImages;
		}
		return null;
	}
	
	public static File linuxLogFile = new File("/home/pi/Documents/Musikmann_Bot/Musikman/LogIndex.json");
	public static File windowsLogFile = new File("C:\\Users\\Philipp Bleimund\\eclipse-workspace\\newStart\\Bot_Musikman\\LogIndex.json");
	public static File getLogFile() {
		if(OS.indexOf("Windows") >= 0) {
			String formated = windowsLogFile.getAbsolutePath().replace('\\', '/');
			return new File(formated);
		}else if(OS.contains("Linux")) {
			return linuxLogFile;
		}
		return null;
	}
	
	public static String spotify_clientId = "4aae44e133a94191a5ac9aef5bd7b1ea";
	public static String spotify_clientSecret = "aae746976e474aa3bef66eca713cdd87";
}
