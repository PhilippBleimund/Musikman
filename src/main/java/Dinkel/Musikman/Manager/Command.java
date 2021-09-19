package Dinkel.Musikman.Manager;

import java.util.List;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class Command {

	public abstract void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args);
	
	public abstract String getName();
	
}
