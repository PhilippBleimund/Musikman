package Dinkel.Musikman.Commands.Music;

import java.util.List;

import Dinkel.Musikman.Lavaplayer.GuildMusicManager;
import Dinkel.Musikman.Lavaplayer.PlayerManager;
import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

public class pause extends Command{

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
			this.publicExec(data.publicExec, this.createMessageRunnable(data.event, new MessageCreateBuilder().setContent("we are not in the same voice channel").build(), data.type));
			return;
		}
		
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusikManager(guild);
		
		boolean currState = musicManager.audioPlayer.isPaused();
		
		musicManager.audioPlayer.setPaused(!currState);
		
		if(currState == false) {
			this.publicExec(data.publicExec, this.createMessageRunnable(data.event, new MessageCreateBuilder().setContent("paused the track").build(), data.type));
		}else {
			this.publicExec(data.publicExec, this.createMessageRunnable(data.event, new MessageCreateBuilder().setContent("resumed the track").build(), data.type));
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
