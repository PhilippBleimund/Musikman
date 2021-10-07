package Dinkel.Musikman.Commands.RandomGenerators;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Dinkel.Musikman.Information;
import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.Manager.TicketManager;
import Dinkel.Musikman.Tickets.deleteMessage;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class hentaiImage implements Command {

	private ArrayList<File> images;

	private File LOCATION;
	
	public hentaiImage() {
		
		String OS = System.getProperty("os.name");
		System.out.println(OS);
		if(OS.indexOf("Windows") >= 0) {
			LOCATION = Information.windowsHentaiImages;
		}else if(OS.contains("Linux")) {
			LOCATION = Information.linuxHentaiImages;
		}
		
		try {
			images = prepareArray(new File[] {LOCATION});
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args) {
		File file = getRandomImage();
		eventMessage.getChannel().sendMessage("ʕ•́ᴥ•̀ʔっ♡").addFile(file, file.getName()).queue(message -> {
			message.addReaction("❌").queue();
			TicketManager.getInstance().addTicket(new deleteMessage(new long[]{message.getIdLong(), eventMessage.getMessageIdLong()}));
		});
	}

	private ArrayList<File> prepareArray(File[] locations) throws FileNotFoundException, IOException, ParseException {
		File[][] allFiles = new File[locations.length][0];
		for(int i=0;i<locations.length;i++) {
			allFiles[i] = locations[i].listFiles(new FilenameFilter() {
			    @Override
			    public boolean accept(File dir, String name) {
			    	boolean accept = false;
			    	if(name.toLowerCase().endsWith(".png"))
			    		accept = true;
			    	if(name.toLowerCase().endsWith(".jpg"))
			    		accept = true;
			    	if(name.toLowerCase().endsWith(".gif"))
			    		accept = true;
			    	if(name.toLowerCase().endsWith(".jpeg"))
			    		accept = true;
			        return accept;
			    }
			});
		}
		ArrayList<File> files = new ArrayList<File>();
		for(int i=0;i<allFiles.length;i++) {
			for(int j=0;j<allFiles[0].length;j++) {
				files.add(allFiles[i][j]);
			}
		}
		return files;
	}
	
	private File getRandomImage() {
		Random rnd = new Random();
		int random = rnd.nextInt(images.size());
		return images.get(random);
	}

	@Override
	public String[] getNames() {
		return new String[] { "hentaiImage" };
	}

	@Override
	public String[] getArgs() {
		return null;
	}

	@Override
	public String getDescription() {
		return "get a random hentai imgage";
	}

	@Override
	public boolean showInHelp() {
		return true;
	}

}
