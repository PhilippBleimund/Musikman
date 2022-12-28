package Dinkel.Musikman;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.input.WindowsLineEndingInputStream;

public class Information {

	public static boolean MinecraftServerStatus = false;

	public static final String OS = System.getProperty("os.name");

	public static final String user = System.getProperty("user.name");
	
	public static final String TokenMusikMan = "ODg4NDQwMjc5NjcyMTgwNzY2.YUSufQ.IxTf_QecGnEyQz8FBef6ijSniI8";
	
	//public static String TokenPhilippMusikMan = "NDA2NTQwNzAxNzYyMDYwMzE4.X_n6GQ.GgbJhlYASP1X8zuuJ687NDUJtPY";
	
	public static final String TokenMusikFrau = "OTU0NDQ4MjMzMzE5NTI2NDMw.YjTRLw.BX7q8G3n17ale7_4cwlUMiJ8xBY";
	
	public static final long[] admins = new long[]{
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
	
	public static final long[] minecraftPlayer = new long[]{
		406540701762060318l,	//Philipp Bleimund
		395012173702758411l,	//Simon Krampe
		242226144810762260l,	//Nuri
		397426524179005440l,	//Simeon Wortmann
		411636395866849280l		//Lucas
	}

	public static boolean isMinecraftPlayer(long UserId) {
		for(int i=0;i<minecraftPlayer.length;i++) {
			if(UserId == minecraftPlayer[i]) {
				return true;
			}
		}
		return false;
	}	

	private static final String[] linuxCustomSounds = new String[]{"/home/","/Music/customSounds"};
	private static final String[] windowsCustomSounds = new String[]{"C:\\Users\\", "\\Music\\Musikman"};
	public static File getCustomSounds() {
		if(OS.indexOf("Windows") >= 0) {
			return generateFile(windowsCustomSounds);
		}else if(OS.contains("Linux")) {
			return generateFile(linuxCustomSounds);
		}
		return null;
	}
	
	private static final String[] linuxHentaiImages = new String[]{"/home/","/Pictures/hentaiImages/links.json"};
	private static final String[] windowsHentaiImages = new String[]{"C:\\Users\\", "\\Desktop\\DiscordChatExporter\\Chats\\hanime.tv\\links.json"};
	public static File getHentaiImages() {
		if(OS.indexOf("Windows") >= 0) {
			return generateFile(windowsHentaiImages);
		}else if(OS.contains("Linux")) {
			return generateFile(linuxHentaiImages);
		}
		return null;
	}
	
	private static final String[] linuxLogFile = new String[]{"/home/","/Documents/Musikmann_Bot/Musikman/LogIndex.json"};
	private static final String[] windowsLogFile = new String[]{"C:\\Users\\", "\\eclipse-workspace\\newStart\\Bot_Musikman\\LogIndex.json"};
	public static File getLogFile() {
		if(OS.indexOf("Windows") >= 0) {
			return generateFile(windowsLogFile);
		}else if(OS.contains("Linux")) {
			return generateFile(linuxLogFile);
		}
		return null;
	}
	
	private static File generateFile(String[] seperatedFile){
		String combined = seperatedFile[0] + user + seperatedFile[1];
		String formated = combined.replace('\\', '/');
		File file = new File(formated);
		file.getParentFile().mkdirs();
		return file;
	}

	public static final String spotify_clientId = "4aae44e133a94191a5ac9aef5bd7b1ea";
	public static final String spotify_clientSecret = "aae746976e474aa3bef66eca713cdd87";
}
