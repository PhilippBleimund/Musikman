package Dinkel.Musikman.Commands.Admin;

import java.util.List;

import Dinkel.Musikman.Information;
import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class adminProcessId implements Command{

	@Override
	public void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args) {
		TextChannel channel = eventMessage.getChannel();
		
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

}
