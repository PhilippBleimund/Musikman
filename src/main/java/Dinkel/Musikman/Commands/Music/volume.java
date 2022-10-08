package Dinkel.Musikman.Commands.Music;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import Dinkel.Musikman.Lavaplayer.GuildMusicManager;
import Dinkel.Musikman.Lavaplayer.PlayerManager;
import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.helper.helper;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class volume implements Command{

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
		AudioPlayer audioPlayer = musicManager.audioPlayer;
		
		if(audioPlayer.getPlayingTrack() == null) {
			channel.sendMessage("there is no track playing").queue();
			return;
		}
		
		String arg0 = args.get(0);
		if(helper.isInteger(arg0)) {
			int intArg = Integer.valueOf(arg0);
			if(intArg >= 0 && intArg <= 100) {
				channel.sendMessage("set audio player volume to `" + intArg + "`").queue();
				audioPlayer.setVolume(intArg);
			}
		}
	}

	@Override
	public String[] getNames() {
		return new String[] {"volume"};
	}

	@Override
	public String[] getArgs() {
		return new String[] {"0-300"};
	}

	@Override
	public String getDescription() {
		return "change the volume of the palyer";
	}

	@Override
	public boolean showInHelp() {
		return true;
	}

}
