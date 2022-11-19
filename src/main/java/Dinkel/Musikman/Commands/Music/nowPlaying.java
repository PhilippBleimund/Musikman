package Dinkel.Musikman.Commands.Music;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import Dinkel.Musikman.Lavaplayer.GuildMusicManager;
import Dinkel.Musikman.Lavaplayer.PlayerManager;
import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class nowPlaying extends Command{

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
		AudioPlayer audioPlayer = musicManager.audioPlayer;
		AudioTrack track = audioPlayer.getPlayingTrack();
		
		if(track == null) {
			this.publicExec(publicExec, () -> {channel.sendMessage("there is no track playing").queue();});
			return;
		}
		
		AudioTrackInfo info = track.getInfo();
		
		this.publicExec(publicExec, () -> {channel.sendMessageFormat("now playing `%s` by `%s` (Link: <%s>", info.title, info.author, info.uri).queue();});
	}

	@Override
	public String[] getNames() {
		return new String[]{"nowPlaying", "np"};
	}

	@Override
	public String getDescription() {
		return "sends the current playing song";
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
