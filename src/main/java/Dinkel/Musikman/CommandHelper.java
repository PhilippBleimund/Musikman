package Dinkel.Musikman;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class CommandHelper {

	public abstract void commandCode(GuildMessageReceivedEvent eventMessage);
	
	public abstract String getName();
	
}
