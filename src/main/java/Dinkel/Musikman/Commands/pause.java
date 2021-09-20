package Dinkel.Musikman.Commands;

import java.util.List;

import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class pause implements Command{

	@Override
	public void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args) {
		// TODO Auto-generated method stub
	}

	@Override
	public String[] getNames() {
		return new String[]{"pause", "resume"};
	}

	@Override
	public String getDescription() {
		return "pauses or resumes the current playing song";
	}

}
