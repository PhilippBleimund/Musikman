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
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

public class leave extends Command{

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
		GuildVoiceState selfVoiceState = guild.getSelfMember().getVoiceState();
		
		if(!selfVoiceState.inAudioChannel()) {
			this.publicExec(data.publicExec, this.createMessageRunnable(data.event, new MessageCreateBuilder().setContent("I need to be in a voice channel").build(), data.type));
			return;
		}

		GuildVoiceState memberVoiceState = member.getVoiceState();
		
		if(!memberVoiceState.inAudioChannel()) {
			this.publicExec(data.publicExec, this.createMessageRunnable(data.event, new MessageCreateBuilder().setContent("You are not in a channel").build(), data.type));
			return;
		}
		
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			this.publicExec(data.publicExec, this.createMessageRunnable(data.event, new MessageCreateBuilder().setContent("We are not in the same voice channel").build(), data.type));
			return;
		}
		
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusikManager(guild);
		
		musicManager.scheduler.repeating = false;
		musicManager.scheduler.queue.clear();
		musicManager.audioPlayer.stopTrack();
		
		AudioManager audioManager = guild.getAudioManager();
		audioManager.closeAudioConnection();
		
		this.publicExec(data.publicExec, this.createMessageRunnable(data.event, new MessageCreateBuilder().setContent("I have left the voice channel").build(), data.type));
	}

	@Override
	public String[] getNames() {
		return new String[]{"leave", "l"};
	}

	@Override
	public String getDescription() {
		return "leaves the current voice channel";
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
