package Dinkel.Musikman.Manager;

import java.util.List;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public abstract class Command {

	public abstract void commandCode(MessageReceivedEvent  eventMessage, List<String> args, boolean publicExec);

	protected void publicExec(boolean pub, Runnable r){
		if(pub)
			r.run();
	}
	
	public abstract String[] getNames();
	
	public abstract String[] getArgs();
	
	public abstract String getDescription();
	
	public abstract boolean showInHelp();

	public abstract boolean NSFW();

	protected CommandData getCommandData(){
		return Commands.slash(getNames()[0], getDescription());
	}
}
