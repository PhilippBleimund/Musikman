package Dinkel.Musikman.Commands;

import java.util.List;

import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap.SerializerAndMapResult;

import Dinkel.Musikman.Musikman_Main;
import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.Manager.CommandManager;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;


public class help implements Command {
	public void commandCode(MessageReceivedEvent eventMessage, List<String> args) {
		MessageChannelUnion messageActionUnion = eventMessage.getChannel();
		TextChannel messageAction = messageActionUnion.asTextChannel();
		MessageCreateAction sendMessage = messageAction.sendMessage("**help**\n");
	
	
		List<Command> commands = CommandManager.getInstance().commands;
		for(int i=0;i<commands.size();i++) {
			Command command = commands.get(i);
			if(command.showInHelp()) {
				sendMessage.addContent("# ");
				String[] names = command.getNames();
				for(int j=0;j<names.length;j++) {
					if(j+1 != names.length)
						sendMessage.addContent("`"+ Musikman_Main.prefix + names[j] + "`, ");
					else
						sendMessage.addContent("`"+ Musikman_Main.prefix + names[j] + "`");
				}
				
				String[] commandArgs = command.getArgs();
				if(commandArgs != null) {
					sendMessage.addContent(" args: ");
					for(int j=0;j<commandArgs.length;j++) {
						if(j+1 != commandArgs.length)
							sendMessage.addContent("`[" + commandArgs[j] + "]`, ");
						else
							sendMessage.addContent("`[" + commandArgs[j] + "]`");
					}
				}
				sendMessage.addContent(" --> **`" + command.getDescription() + "`**\n");
			}
		}
		sendMessage.queue();
	}

	@Override
	public String[] getNames() {
		return new String[]{"help", "h"};
	}

	@Override
	public String getDescription() {
		return "fucking help";
	}

	@Override
	public String[] getArgs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean showInHelp() {
		return true;
	}

}
