package Dinkel.Musikman.Commands.csInvest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Dinkel.Musikman.Information;
import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class csInvest extends Command {

	ArrayList<Command> commands = new ArrayList<Command>();
	
	public csInvest() {

	}
	
	@Override
	public void commandCode(MessageReceivedEvent eventMessage, List<String> args, boolean publicExec) {
		
		String argument = args.get(0);

		List<String> argsList = args.subList(1, args.size());
		
        for(Command c : commands) {
            for(int j=0;j<c.getNames().length;j++) {
                if(c.getNames()[j].equalsIgnoreCase(argument)) {
                    c.commandCode(eventMessage, argsList, true);
                }
            }
        }
	}

	@Override
	public String[] getNames() {
		return new String[] {"csInvest"};
	}

	@Override
	public String[] getArgs() {
		return new String[] {};
	}

	@Override
	public String getDescription() {
		return "tool to track and view your cs Investments on Steam";
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
