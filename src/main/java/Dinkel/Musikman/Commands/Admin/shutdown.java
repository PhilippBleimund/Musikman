package Dinkel.Musikman.Commands.Admin;

import java.util.List;

import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class shutdown extends Command{

	@Override
	public void commandCode(MessageReceivedEvent eventMessage, List<String> args, boolean publicExec) {
		System.exit(0);
	}

	@Override
	public String[] getNames() {
		return new String[] {"shutdown"};
	}

	@Override
	public String[] getArgs() {
		return null;
	}

	@Override
	public String getDescription() {
		return "shutdowns the application";
	}

	@Override
	public boolean showInHelp() {
		return true;
	}

}
