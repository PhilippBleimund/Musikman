package Dinkel.Musikman.Commands;

import java.util.List;

import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.Manager.CommandManager;
import Dinkel.Musikman.Manager.TicketManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

public class help implements Command {
	public void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args) {
		MessageAction messageAction = eventMessage.getChannel().sendMessage("**help**\n");
		List<Command> commands = CommandManager.getInstance().commands;
		for(int i=0;i<commands.size();i++) {
			messageAction.append("# ");
			String[] names = commands.get(i).getNames();
			for(int j=0;j<names.length;j++) {
				if(j+1 != names.length)
					messageAction.append("`!" + names[j] + "`, ");
				else
					messageAction.append("`!" + names[j] + "`");
			}
			
			messageAction.append(" `[" + commands.get(i).getDescription() + "]`\n");
		}
		messageAction.queue();
	}

	@Override
	public String[] getNames() {
		return new String[]{"help", "h"};
	}

	@Override
	public String getDescription() {
		return "fucking help";
	}

}
