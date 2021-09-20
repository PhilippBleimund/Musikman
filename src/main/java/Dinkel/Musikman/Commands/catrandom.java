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
		URL url;
		try {
			url = new URL("https://cataas.com/cat");
			BufferedImage image = ImageIO.read(url);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			byte[] bytes = baos.toByteArray();
			eventMessage.getChannel().sendMessage("ʕ•́ᴥ•̀ʔっ♡").addFile(bytes, "rule34.jpg", AttachmentOption.SPOILER).queue(message -> {
				message.addReaction("❌").queue();
				TicketManager.getInstance().addTicket(new deleteMessage(message.getIdLong(), eventMessage.getMessageIdLong()));
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
