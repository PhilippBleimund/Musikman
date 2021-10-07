package Dinkel.Musikman;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Information {

	public static String OS = System.getProperty("os.name");
	
	public static String Token = "ODg4NDQwMjc5NjcyMTgwNzY2.YUSufQ.IxTf_QecGnEyQz8FBef6ijSniI8";
	
	public static String TokenPhilipp = "NDA2NTQwNzAxNzYyMDYwMzE4.X_n6GQ.GgbJhlYASP1X8zuuJ687NDUJtPY";
	
	public static long[] admins = new long[]{
		406540701762060318l,	//Philipp Bleimund
		395012173702758411l		//Simon Krampe
	};
	
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
	
	public static File linuxHentaiImages = new File("/home/pi/Pictures/HentaiImages");
	public static File windowsHentaiImages = new File("C:\\Users\\Philipp Bleimund\\Desktop\\DiscordChatExporter\\Chats\\Doujin District\\hentaiImages");
	public static File getHentaiImages() {
		if(OS.indexOf("Windows") >= 0) {
			String formated = windowsHentaiImages.getAbsolutePath().replace('\\', '/');
			return new File(formated);
		}else if(OS.contains("Linux")) {
			return linuxHentaiImages;
		}
		return null;
	}
}
