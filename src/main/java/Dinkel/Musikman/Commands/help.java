package Dinkel.Musikman.Commands;

import java.util.List;

import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.Manager.TicketManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class help extends Command {
	public void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args) {
		EmbedBuilder help = new EmbedBuilder();
		help.setTitle("help for Musikman");
		help.appendDescription("!play --> play Song");
		eventMessage.getChannel().sendMessage(help.build()).queue();;
		help.clear();
	}

	@Override
	public String getName() {
		return "help";
	}

}
