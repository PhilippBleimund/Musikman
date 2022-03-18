package Dinkel.Musikman.Commands.Admin;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import Dinkel.Musikman.Information;
import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.Manager.customJoinSounds;
import Dinkel.Musikman.helper.helper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class admin implements Command {

	ArrayList<Command> commands = new ArrayList<Command>();
	
	public admin() {
		commands.add(new adminLock());
		commands.add(new adminPersonalSound());
		commands.add(new adminProcessId());
		commands.add(new restart());
		commands.add(new shutdown());
	}
	
	@Override
	public void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args) {
		
		TextChannel channel = eventMessage.getChannel();
		
		String argument = args.get(0);
		
		long idLong = eventMessage.getAuthor().getIdLong();
		for(int i=0;i<Information.admins.length;i++) {
			if(idLong == Information.admins[i]) {
				i = Integer.MAX_VALUE;
				for(Command c : commands) {
					for(int j=0;j<c.getNames().length;j++) {
						if(c.getNames()[j].equalsIgnoreCase(argument)) {
							c.commandCode(eventMessage, args);
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
