package Dinkel.Musikman.Commands.Admin;

import java.util.List;

import Dinkel.Musikman.Information;
import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class adminProcessId extends Command{

	@Override
	public void commandCode(MessageReceivedEvent eventMessage, List<String> args, boolean publicExec) {
		TextChannel channel = eventMessage.getChannel().asTextChannel();
		
		long pid = ProcessHandle.current().pid();
		channel.sendMessage("'ProcessID:' " + pid + " 'Current OP:' " + Information.OS).queue();
	}

	@Override
	public String[] getNames() {
		return new String[] {"process"};
	}

	@Override
	public String[] getArgs() {
		return null;
	}

	@Override
	public String getDescription() {
		return "get the processId of the application";
	}

	@Override
	public boolean showInHelp() {
		return true;
	}

	@Override
	public boolean NSFW() {
		return false;
	}

}
