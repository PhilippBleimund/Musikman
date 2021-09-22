package Dinkel.Musikman.Manager;

import java.util.List;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface Command {

	public abstract void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args);
	
	public abstract String[] getNames();
	
	public abstract String[] getArgs();
	
	public abstract String getDescription();
	
	public abstract boolean showInHelp();
}
