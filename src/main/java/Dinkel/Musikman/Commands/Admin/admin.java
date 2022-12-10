package Dinkel.Musikman.Commands.Admin;

import java.util.ArrayList;
import java.util.List;

import Dinkel.Musikman.Information;
import Dinkel.Musikman.Commands.MinecraftServer.startServer;
import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class admin extends Command {

	ArrayList<Command> commands = new ArrayList<Command>();
	
	public admin() {
		commands.add(new adminLock());
		//commands.add(new adminPersonalSound());
		commands.add(new adminProcessId());
		commands.add(new restart());
		commands.add(new shutdown());
		//commands.add(new startServer());
	}
	
	@Override
	public void commandCode(MessageReceivedEvent eventMessage, List<String> args, boolean publicExec) {
		
		TextChannel channel = eventMessage.getChannel().asTextChannel();
		
		String argument = args.get(0);
		
		long idLong = eventMessage.getAuthor().getIdLong();
		for(int i=0;i<Information.admins.length;i++) {
			if(idLong == Information.admins[i]) {
				i = 1000;
				for(Command c : commands) {
					for(int j=0;j<c.getNames().length;j++) {
						if(c.getNames()[j].equalsIgnoreCase(argument)) {
							c.commandCode(eventMessage, args, true);
						}
					}
				}
			}
		}
	}

	@Override
	public String[] getNames() {
		return new String[] {"admin"};
	}

	@Override
	public String[] getArgs() {
		return new String[] { "shutdown", "restart", "personalSound (mention user)", "lock"};
	}

	@Override
	public String getDescription() {
		return "special commands for the admins";
	}

	@Override
	public boolean showInHelp() {
		return true;
	}

}
