package Dinkel.Musikman.Commands;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.Manager.TicketManager;
import Dinkel.Musikman.Tickets.deleteMessage;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.utils.AttachmentOption;

public class catrandom implements Command{

	@Override
	public void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args) {
		eventMessage.getChannel().sendMessage("ʕ•́ᴥ•̀ʔっ♡").addFile(getRandomCat(), "rule34.jpg").queue(message -> {
			message.addReaction("❌").queue();
			TicketManager.getInstance().addTicket(new deleteMessage(new long[]{message.getIdLong(), eventMessage.getMessageIdLong()}));
		});
	}
	
	public byte[] getRandomCat() {
		try {
			URL url = new URL("https://cataas.com/cat");
			BufferedImage image = ImageIO.read(url);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			byte[] bytes = baos.toByteArray();
			if(bytes.length > 8388608 && bytes.length < 10)
				return getRandomCat();
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
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

}
