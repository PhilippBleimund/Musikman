package Dinkel.Musikman.Commands;

import java.awt.Color;
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
			Command command = commands.get(i);
			if(command.showInHelp()) {
				messageAction.append("# ");
				String[] names = command.getNames();
				for(int j=0;j<names.length;j++) {
					if(j+1 != names.length)
						messageAction.append("`!" + names[j] + "`, ");
					else
						messageAction.append("`!" + names[j] + "`");
				}
				
				String[] commandArgs = command.getArgs();
				if(commandArgs != null) {
					messageAction.append(" args: ");
					for(int j=0;j<commandArgs.length;j++) {
						if(j+1 != commandArgs.length)
							messageAction.append("`[" + commandArgs[j] + "]`, ");
						else
							messageAction.append("`[" + commandArgs[j] + "]`");
					}
				}
				messageAction.append(" --> **`" + command.getDescription() + "`**\n");
			}
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
