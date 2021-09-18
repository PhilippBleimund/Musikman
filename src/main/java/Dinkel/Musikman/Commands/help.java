package Dinkel.Musikman.Commands;

import Dinkel.Musikman.CommandHelper;
import Dinkel.Musikman.Musikman_Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class help extends CommandHelper {
	public void commandCode(GuildMessageReceivedEvent eventMessage) {
		EmbedBuilder help = new EmbedBuilder();
		help.setTitle("help for Musikman");
		help.appendDescription("!p --> play Song");
		eventMessage.getChannel().sendMessage(help.build()).queue();;
		help.clear();
	}

	@Override
	public String getName() {
		return "help";
	}

}
