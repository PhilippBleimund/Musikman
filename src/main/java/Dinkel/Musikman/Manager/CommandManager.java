package Dinkel.Musikman.Manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Dinkel.Musikman.Musikman_Main;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandManager extends ListenerAdapter {

	private static CommandManager INSTANCE;

	public List<Command> commands = new ArrayList<Command>();

	public void addCommand(Command command) {
		commands.add(command);
	}

	public void onMessageReceived(MessageReceivedEvent  eventMessage) {
		String[] args = eventMessage.getMessage().getContentRaw().split("\\s+");

		List<String> argsList = Arrays.asList(args).subList(1, args.length);

		for (Command c : commands) {
			String arg = eventMessage.getMessage().getContentRaw().split("\\s+")[0];
			for (int i = 0; i < c.getNames().length; i++) {
				if (arg.equalsIgnoreCase(Musikman_Main.prefix + c.getNames()[i])) {
					c.commandCode(eventMessage, argsList, true);
				}
			}

		}
	}

	public void CommandRequest(MessageReceivedEvent  eventMessage, String commandName) {
		String[] args = eventMessage.getMessage().getContentRaw().split("\\s+");

		List<String> argsList = Arrays.asList(args).subList(1, args.length);
		for (Command c : commands) {
			for (int i = 0; i < c.getNames().length; i++) {
				if (commandName.equalsIgnoreCase(Musikman_Main.prefix + c.getNames()[i])) {
					c.commandCode(eventMessage, argsList, false);
				}
			}
		}
	}

	public static CommandManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CommandManager();
		}

		return INSTANCE;
	}
}
