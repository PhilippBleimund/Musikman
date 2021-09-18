package Dinkel.Musikman.Manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Dinkel.Musikman.Musikman_Main;
import Dinkel.Musikman.Commands.help;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandManager extends ListenerAdapter {
	
	List<Command> commands = new ArrayList<Command>();
	
	private TicketManager ticketManager;
	
	public void addCommand(Command command) {
		commands.add(command);
	}
	
	public void setTicketManager(TicketManager ticketManager) {
		this.ticketManager = ticketManager;
	}
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent eventMessage) {
		
		String[] args = eventMessage.getMessage().getContentRaw().split("\\s+");
		
		List<String> argsList = Arrays.asList(args).subList(1, args.length);
		
		for(Command c : commands) {
			if(eventMessage.getMessage().getContentRaw().split("\\s+")[0].equalsIgnoreCase(Musikman_Main.prefix + c.getName())) {
				
				c.commandCode(eventMessage, argsList, ticketManager);
			}
				
		}
	}
}
