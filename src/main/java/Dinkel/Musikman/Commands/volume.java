package Dinkel.Musikman.Commands;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import Dinkel.Musikman.Lavaplayer.GuildMusicManager;
import Dinkel.Musikman.Lavaplayer.PlayerManager;
import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.helper.helper;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class volume implements Command{

	@Override
	public void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args) {
		TextChannel channel = eventMessage.getChannel();
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
		AudioPlayer audioPlayer = musicManager.audioPlayer;
		
		if(audioPlayer.getPlayingTrack() == null) {
			channel.sendMessage("there is no track playing").queue();
			return;
		}
		
		String arg0 = args.get(0);
		if(helper.isInteger(arg0)) {
			int intArg = Integer.valueOf(arg0);
			if(intArg >= 0 && intArg <= 100) {
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
		return new String[] {"0-100"};
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
