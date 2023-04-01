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


public class help extends Command {
	public void commandCode(MessageReceivedEvent eventMessage, List<String> args, boolean publicExec) {
		MessageChannelUnion messageActionUnion = eventMessage.getChannel();
		TextChannel messageAction = messageActionUnion.asTextChannel();

		List<Command> commands = CommandManager.getInstance().commands;
		
		int maxLength = 0;
		for(int i=0;i<commands.size();i++){
			Command command = commands.get(i);
			if(command.showInHelp()) {
				String[] names = command.getNames();
				String fullName = "";
				for(int j=0;j<names.length;j++) {
					if(j+1 != names.length)
						fullName = fullName + (Musikman_Main.prefix + names[j] + ", ");
					else
						fullName = fullName + (Musikman_Main.prefix + names[j]);
				}
				
				if(maxLength < fullName.length())
					maxLength = fullName.length();
			}
		}

		MessageCreateAction sendMessage = messageAction.sendMessage("**help**\n\n");
		sendMessage.addContent("```bash");
	
		for(int i=0;i<commands.size();i++) {
			Command command = commands.get(i);
			if(command.showInHelp()) {
				sendMessage.addContent("# ");
				String[] names = command.getNames();
				String preName = "";
				for(int j=0;j<names.length;j++) {
					if(j+1 != names.length)
						preName = preName + (Musikman_Main.prefix + names[j] + ", ");
					else
						preName = preName + (Musikman_Main.prefix + names[j]);
				}
				
				if(preName.length() < maxLength){
					int diff = maxLength - preName.length();
					for(int j=0;j<diff;j++){
						preName = preName + " ";
					}
				}
				sendMessage.addContent(preName);

				String[] commandArgs = command.getArgs();
				if(commandArgs != null) {
					sendMessage.addContent(" args: ");
					for(int j=0;j<commandArgs.length;j++) {
						if(j+1 != commandArgs.length)
							sendMessage.addContent("[" + commandArgs[j] + "], ");
						else
							sendMessage.addContent("[" + commandArgs[j] + "]");
					}
				}
				sendMessage.addContent(" --> " + command.getDescription() + "\n");
			}
		}

		sendMessage.addContent("```");
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

	@Override
	public boolean NSFW() {
		return false;
	}
}
