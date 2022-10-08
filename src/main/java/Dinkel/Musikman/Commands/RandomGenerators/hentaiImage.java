package Dinkel.Musikman.Commands.RandomGenerators;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Dinkel.Musikman.Information;
import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.Manager.TicketManager;
import Dinkel.Musikman.Tickets.deleteMessage;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class hentaiImage implements Command {

	private String[] links;
	
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
			links = prepareArray(LOCATION);
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void commandCode(MessageReceivedEvent eventMessage, List<String> args) {
		String file = getRandomImage();
		eventMessage.getChannel().sendMessage(file).queue(message -> {
			message.addReaction(Emoji.fromUnicode("U+274C")).queue();
			TicketManager.getInstance().addTicket(new deleteMessage(new long[]{message.getIdLong(), eventMessage.getMessageIdLong()}));
		});
	}

	private String[] prepareArray(File location) throws FileNotFoundException, IOException, ParseException {
		String[] images;
		JSONParser json = new JSONParser();
		JSONObject parse = (JSONObject) json.parse(new FileReader(location));
		JSONArray jsonImages = (JSONArray) parse.get("images");
		images = new String[jsonImages.size()];
		for(int i=0;i<jsonImages.size();i++) {
			images[i] = (String) jsonImages.get(i);
		}
		return images;
	}
	
	private String getRandomImage() {
		Random rnd = new Random();
		int random = rnd.nextInt(links.length);
		return links[random];
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
