package Dinkel.Musikman.Commands;

import java.util.List;

import Dinkel.Musikman.Lavaplayer.GuildMusicManager;
import Dinkel.Musikman.Lavaplayer.PlayerManager;
import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class loop implements Command{

	@Override
	public void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args) {
		TextChannel channel = eventMessage.getChannel();
		if(args.size() <= 0) {
			channel.sendMessage("add arg: `[track]`, `[queue]`, `[off]`").queue();
			return;
		}
		
		Member self = eventMessage.getGuild().getSelfMember();
		GuildVoiceState selfVoiceState = self.getVoiceState();
		
		if(!selfVoiceState.inVoiceChannel()) {
			channel.sendMessage("I need to be in a voice channel").queue();
			return;
		}
		
		Member member = eventMessage.getMember();
		GuildVoiceState memberVoiceState = member .getVoiceState();
		
		if(!memberVoiceState.inVoiceChannel()) {
			channel.sendMessage("You are not in a channel").queue();
			return;
		}
		
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			channel.sendMessage("we are not in the same voice channel").queue();
			return;
		}
		
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusikManager(eventMessage.getGuild());

		if(args.get(0).equals("queue")) {
			musicManager.scheduler.loopQueue();
			channel.sendMessage("the queue will be looping").queue();			
		}else if(args.get(0).equalsIgnoreCase("track")) {
			boolean newRepeating = !musicManager.scheduler.repeating;
			musicManager.scheduler.repeating = newRepeating;
			channel.sendMessageFormat("The player has been set to **%s**", newRepeating ? "repeating" : "not repeating").queue();
		}else if(args.get(0).equalsIgnoreCase("off")) {
			musicManager.scheduler.loopOffQueue();
			musicManager.scheduler.repeating = false;
			channel.sendMessage("All loops were disactivated").queue();
		}
	}

	@Override
	public String[] getNames() {
		return new String[] {"loop"};
	}

	@Override
	public String getDescription() {
		return "loops the cuurent queue";
	}

	@Override
	public String[] getArgs() {
		return new String[] {"track", "queue", "off"};
	}

	@Override
	public boolean showInHelp() {
		return true;
	}

}
