package Dinkel.Musikman;

import java.util.ArrayList;
import java.util.List;

import Dinkel.Musikman.Commands.help;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandManager extends ListenerAdapter {
	
	List<CommandHelper> commands = new ArrayList<CommandHelper>();
	
	public void addCommand(CommandHelper command) {
		commands.add(command);
	}
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent eventMessage) {
		for(CommandHelper c : commands) {
			if(eventMessage.getMessage().getContentRaw().split("\\s+")[0].equals(Musikman_Main.prefix + c.getName()))
				c.commandCode(eventMessage);
		}
	}
}
