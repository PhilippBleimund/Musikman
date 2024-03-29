package Dinkel.Musikman.Commands.RandomGenerators;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.Manager.TicketManager;
import Dinkel.Musikman.Tickets.deleteMessage;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.FileUpload;

public class rule34random extends Command{

	private String[] faces = new String[] {"( ͡° ͜ʖ ͡°)", "( ͡⚆ ͜ʖ ͡⚆ )", "( ͝° ͜ʖ͡°)", "/╲/( ͡☉ ͜ʖ ͡☉)/\\╱\\", "ಠ ﭛ ಠ"};
	Random rnd = new Random();
	
	@Override
	public void commandCode(MessageReceivedEvent eventMessage, List<String> args, boolean publicExec) {
		eventMessage.getChannel().sendMessage(faces[rnd.nextInt(faces.length)]).addFiles(FileUpload.fromData(getRule34Image(), "rule34.jpg").asSpoiler()).queue(message -> {
			message.addReaction(Emoji.fromUnicode("U+274C")).queue();
			TicketManager.getInstance().addTicket(new deleteMessage(new long[]{message.getIdLong(), eventMessage.getMessageIdLong()}));
		});
	}
	
	private String getRule34ImageURL() {
		try {
			URLConnection con = new URL( "https://rule34.xxx/index.php?page=post&s=random" ).openConnection();
			con.connect();
			InputStream is = con.getInputStream();
			Document doc = Jsoup.parse(is, null, con.getURL().toString());
			is.close();
			Elements element = doc.getElementsByAttribute("content");
			Element element2 = element.get(6);
			String attr = element2.attr("content");
			if(attr == null || attr == "")
				return getRule34ImageURL();
			return attr;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private byte[] getRule34Image() {
		try {
			URL url = new URL(getRule34ImageURL());
			BufferedImage image = ImageIO.read(url);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			byte[] bytes = baos.toByteArray();
			if(bytes.length > 8388608 && bytes.length < 10)
				return getRule34Image();
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String[] getNames() {
		return new String[] {"rule34"};
	}

	@Override
	public String getDescription() {
		return "a random rule 34 image";
	}

	@Override
	public String[] getArgs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean showInHelp() {
		return true;
	}

}
