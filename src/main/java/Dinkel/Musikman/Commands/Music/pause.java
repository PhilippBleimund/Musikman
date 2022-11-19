package Dinkel.Musikman.Commands.Music;

import java.util.List;

import Dinkel.Musikman.Lavaplayer.GuildMusicManager;
import Dinkel.Musikman.Lavaplayer.PlayerManager;
import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class pause extends Command{

	@Override
	public void commandCode(MessageReceivedEvent eventMessage, List<String> args, boolean publicExec) {
		TextChannel channel = eventMessage.getChannel().asTextChannel();
		Member self = eventMessage.getGuild().getSelfMember();
		GuildVoiceState selfVoiceState = self.getVoiceState();
		
		if(!selfVoiceState.inAudioChannel()) {
			this.publicExec(publicExec, () -> {channel.sendMessage("I need to be in a voice channel").queue();});
			return;
		}
		
		Member member = eventMessage.getMember();
		GuildVoiceState memberVoiceState = member .getVoiceState();
		
		if(!memberVoiceState.inAudioChannel()) {
			this.publicExec(publicExec, () -> {channel.sendMessage("You are not in a channel").queue();});
			return;
		}
		
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			this.publicExec(publicExec, () -> {channel.sendMessage("we are not in the same voice channel").queue();});
			return;
		}
		
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusikManager(eventMessage.getGuild());
		
		boolean currState = musicManager.audioPlayer.isPaused();
		
		musicManager.audioPlayer.setPaused(!currState);
		
		if(currState == false) {
			this.publicExec(publicExec, () -> {channel.sendMessage("paused the track").queue();});
		}else {
			this.publicExec(publicExec, () -> {channel.sendMessage("resumed the track").queue();});
		}
	}

	@Override
	public String[] getNames() {
		return new String[]{"pause", "resume"};
	}

	@Override
	public String getDescription() {
		return "pauses or resumes the current playing song";
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
