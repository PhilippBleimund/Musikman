package Dinkel.Musikman.Commands;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.Manager.TicketManager;
import Dinkel.Musikman.Tickets.deleteMessage;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.utils.AttachmentOption;

public class catrandom implements Command{

	private final String CatJpg = "https://thecatapi.com/api/images/get?format=src&type=jpg";
	private final String CatGif = "https://thecatapi.com/api/images/get?format=src&type=gif";
	
	@Override
	public void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args) {
		
		String URL = CatJpg;
		String format = "jpg";

		if(args.size() > 0) {
			String arg0 = args.get(0);
			if(arg0.equalsIgnoreCase("jpg")) {
				URL = CatJpg;
			}else if(arg0.equalsIgnoreCase("gif")) {
				URL = CatGif;
				format = "gif";
			}
		}
		
		eventMessage.getChannel().sendTyping().queue();
		eventMessage.getChannel().sendMessage("ʕ•́ᴥ•̀ʔっ♡").addFile(getRandomCat(URL, 0), "cat." + format).queue(message -> {
			message.addReaction("❌").queue();
			TicketManager.getInstance().addTicket(new deleteMessage(new long[]{message.getIdLong(), eventMessage.getMessageIdLong()}));
		});
	}
	
	public byte[] getRandomCat(String sUrl, int i) {
		try {
			URLConnection con = new URL(sUrl).openConnection();
			con.connect();
			InputStream is = con.getInputStream();
			String url = con.getURL().toString();
			byte[] bytes = IOUtils.toByteArray(is);
			is.close();
			if(bytes.length > 8388608 && bytes.length < 10 && i < 10)
				return getRandomCat(sUrl, i++);
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
			if(i < 10)
				return getRandomCat(sUrl, i++);
			return null;
		}
	}

	@Override
	public String[] getNames() {
		return new String[] {"cat"};
	}

	@Override
	public String getDescription() {
		return "a random cat image";
	}

	@Override
	public String[] getArgs() {
		return new String[] {"jpg", "gif"};
	}

	@Override
	public boolean showInHelp() {
		return true;
	}

}
