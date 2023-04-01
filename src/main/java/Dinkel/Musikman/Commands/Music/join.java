package Dinkel.Musikman.Commands.Music;

import java.util.List;

import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class join extends Command {
	public void textImplementation(MessageReceivedEvent eventMessage, List<String> args, boolean publicExec) {
		Member self = eventMessage.getGuild().getSelfMember();
		Member member = eventMessage.getMember();
		AudioManager audioManager = eventMessage.getGuild().getAudioManager();

		mergedImplementation(self, member, audioManager, new ImplementationData(eventMessage, publicExec, InvokeMethod.TEXT));
	}

	@Override
	public void slashImplementation(SlashCommandInteractionEvent eventMessage, boolean publicExec) {
		Member self = eventMessage.getGuild().getSelfMember();
		Member member = eventMessage.getMember();
		AudioManager audioManager = eventMessage.getGuild().getAudioManager();
		
		mergedImplementation(self, member, audioManager, new ImplementationData(eventMessage, publicExec, InvokeMethod.SLASH));
	}

	private void mergedImplementation(Member self, Member member, AudioManager audioManager, ImplementationData data){
		GuildVoiceState selfVoiceState = self.getVoiceState();
		
		if(selfVoiceState.inAudioChannel()) {
			this.publicExec(data.publicExec, this.createMessageRunnable(data.event, new MessageCreateBuilder().setContent("Im already in a voice channel").build(), data.type));
			return;
		}

		GuildVoiceState memberVoiceState = member.getVoiceState();
		
		if(!memberVoiceState.inAudioChannel()) {
			this.publicExec(data.publicExec, this.createMessageRunnable(data.event, new MessageCreateBuilder().setContent("You are not in a channel").build(), data.type));
			return;
		}
		
		VoiceChannel memberChannel = memberVoiceState.getChannel().asVoiceChannel();
		
		audioManager.openAudioConnection(memberChannel);
		this.publicExec(data.publicExec, this.createMessageRunnable(data.event, new MessageCreateBuilder().setContent("Connecting to \uD83D\uDD0A" + memberChannel.getName()).build(), data.type));
	}

	@Override
	public String[] getNames() {
		return new String[]{"join", "j"};
	}

	@Override
	public String getDescription() {
		return "join the voice channel of the user";
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
