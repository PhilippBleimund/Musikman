package Dinkel.Musikman.Commands.Music;

import java.util.List;

import Dinkel.Musikman.Lavaplayer.GuildMusicManager;
import Dinkel.Musikman.Lavaplayer.PlayerManager;
import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class stop implements Command{

	@Override
	public void commandCode(MessageReceivedEvent eventMessage, List<String> args) {
		TextChannel channel = eventMessage.getChannel().asTextChannel();
		Member self = eventMessage.getGuild().getSelfMember();
		GuildVoiceState selfVoiceState = self.getVoiceState();
		
		if(!selfVoiceState.inAudioChannel()) {
			channel.sendMessage("I need to be in a voice channel").queue();
			return;
		}
		
		Member member = eventMessage.getMember();
		GuildVoiceState memberVoiceState = member .getVoiceState();
		
		if(!memberVoiceState.inAudioChannel()) {
			channel.sendMessage("You are not in a channel").queue();
			return;
		}
		
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			channel.sendMessage("we are not in the same voice channel").queue();
			return;
		}
		
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusikManager(eventMessage.getGuild());
		
		musicManager.scheduler.player.stopTrack();
		musicManager.scheduler.queue.clear();
		
		channel.sendMessage("stoped the music and cleared the queue").queue();
	}
	
	@Override
	public String[] getNames() {
		return new String[]{"stop", "reset"};
	}

	@Override
	public String getDescription() {
		return "remove all tracks and clear current track";
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
