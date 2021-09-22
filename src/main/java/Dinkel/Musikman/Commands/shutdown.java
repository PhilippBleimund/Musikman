package Dinkel.Musikman.Commands;

import java.util.List;

import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class shutdown implements Command{

	@Override
	public void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getArgs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
