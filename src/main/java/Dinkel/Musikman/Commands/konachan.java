package Dinkel.Musikman.Commands;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class konachan implements Command{

	List<String> URLs;
	
	@Override
	public void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args) {
		// TODO Auto-generated method stub
		getKonanImageURL();
	}
	
	public String getKonanImageURL() {
		try {
			Document doc = Jsoup.connect("https://konachan.com/post?tags=order%3Arandom").timeout(Integer.MAX_VALUE).get();
			Element elementById = doc.getElementById("content");
			Elements elementsByClass = elementById.getElementsByClass("content");
			Element element = elementsByClass.get(0);
			Element elementById2 = element.getElementById("post-list-posts");
			Elements allElements = elementById2.select("li");
			for(Element e : allElements) {
				System.out.println(e.attr("id"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String[] getNames() {
		return new String[]{"konanchan"};
	}

	@Override
	public String[] getArgs() {
		return null;
	}

	@Override
	public String getDescription() {
		return "get random lewd anime image. Might not be lewd";
	}

	@Override
	public boolean showInHelp() {
		return true;
	}
	
}