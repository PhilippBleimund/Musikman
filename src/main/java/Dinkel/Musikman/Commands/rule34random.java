package Dinkel.Musikman.Commands;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.Manager.TicketManager;
import Dinkel.Musikman.Tickets.deleteMessage;
import net.dv8tion.jda.api.entities.RichPresence.Image;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.utils.AttachmentOption;

public class rule34random implements Command{

	@Override
	public void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args) {
		int trackCount = Math.min((args.size() != 0) ? Integer.parseInt(args.get(0)) : 1, 7);
		for(int i=0;i<trackCount;i++) {
			try {
				URLConnection con = new URL( "https://rule34.xxx/index.php?page=post&s=random" ).openConnection();
				System.out.println( "orignal url: " + con.getURL() );
				con.connect();
				System.out.println( "connected url: " + con.getURL() );
				InputStream is = con.getInputStream();
				System.out.println( "redirected url: " + con.getURL() );
				is.close();
				Document doc = Jsoup.connect(con.getURL().toString()).timeout(60000).maxBodySize(0).get();
				//System.out.println(doc);
				Elements element = doc.getElementsByAttribute("content");
				//System.out.println(element);
				Element element2 = element.get(6);
				System.out.println(element2);
				String attr = element2.attr("content");
				System.out.println(attr);
				
				URL url = new URL(attr);
				BufferedImage image = ImageIO.read(url);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				  ImageIO.write(image, "jpg", baos);
				  byte[] bytes = baos.toByteArray();
				if(bytes.length > 8388608)
					i--;
				  
				eventMessage.getChannel().sendMessage("( ͡° ͜ʖ ͡°)").addFile(bytes, "rule34.jpg", AttachmentOption.SPOILER).queue(message -> {
					message.addReaction("❌").queue();
					TicketManager.getInstance().addTicket(new deleteMessage(message.getIdLong(), eventMessage.getMessageIdLong()));
				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public String[] getNames() {
		return new String[] {"rule34"};
	}

	@Override
	public String getDescription() {
		return "a random rule 34 image --> !rule34 [imageCount]";
	}

}
