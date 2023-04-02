package Dinkel.Musikman.Commands.Music;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import Dinkel.Musikman.Lavaplayer.GuildMusicManager;
import Dinkel.Musikman.Lavaplayer.PlayerManager;
import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

public class nowPlaying extends Command{

	@Override
	public void textImplementation(MessageReceivedEvent eventMessage, List<String> args, boolean publicExec) {
		Guild guild = eventMessage.getGuild();
		Member member = eventMessage.getMember();

		mergedImplementation(guild, member, new ImplementationData(eventMessage, publicExec, InvokeMethod.TEXT));
	}

	@Override
	public void slashImplementation(SlashCommandInteractionEvent eventMessage, boolean publicExec) {
		Guild guild = eventMessage.getGuild();
		Member member = eventMessage.getMember();

		mergedImplementation(guild, member, new ImplementationData(eventMessage, publicExec, InvokeMethod.SLASH));
	}

	public void mergedImplementation(Guild guild, Member member, ImplementationData data){
		Member self = guild.getSelfMember();
		GuildVoiceState selfVoiceState = self.getVoiceState();
		
		if(!selfVoiceState.inAudioChannel()) {
			this.publicExec(data.publicExec, this.createMessageRunnable(data.event, new MessageCreateBuilder().setContent("I need to be in a voice channel").build(), data.type));
			return;
		}
		
		GuildVoiceState memberVoiceState = member .getVoiceState();
		
		if(!memberVoiceState.inAudioChannel()) {
			this.publicExec(data.publicExec, this.createMessageRunnable(data.event, new MessageCreateBuilder().setContent("You are not in a channel").build(), data.type));
			return;
		}
		
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			this.publicExec(data.publicExec, this.createMessageRunnable(data.event, new MessageCreateBuilder().setContent("We are not in the same voice channel").build(), data.type));
			return;
		}
		
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusikManager(guild);
		AudioPlayer audioPlayer = musicManager.audioPlayer;
		AudioTrack track = audioPlayer.getPlayingTrack();
		
		if(track == null) {
			this.publicExec(data.publicExec, this.createMessageRunnable(data.event, new MessageCreateBuilder().setContent("There is no track playing").build(), data.type));
			return;
		}
		
		AudioTrackInfo info = track.getInfo();
		
		this.publicExec(data.publicExec, this.createMessageRunnable(data.event, new MessageCreateBuilder().setContent("now playing `"+info.title+"` by `"+info.author+"` (Link: <"+info.uri+">").build(), data.type, false));
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
