package Dinkel.Musikman.Commands.Music;

import java.util.List;

import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class join implements Command {
	public void commandCode(MessageReceivedEvent eventMessage, List<String> args) {
		TextChannel channel = eventMessage.getChannel().asTextChannel();
		Member self = eventMessage.getGuild().getSelfMember();
		GuildVoiceState selfVoiceState = self.getVoiceState();
		
		if(selfVoiceState.inAudioChannel()) {
			channel.sendMessage("Im already in a voice channel").queue();
			return;
		}
		
		Member member = eventMessage.getMember();
		GuildVoiceState memberVoiceState = member .getVoiceState();
		
		if(!memberVoiceState.inAudioChannel()) {
			channel.sendMessage("You are not in a channel").queue();
			return;
		}
		
		AudioManager audioManager = eventMessage.getGuild().getAudioManager();
		VoiceChannel memberChannel = memberVoiceState.getChannel().asVoiceChannel();
		
		audioManager.openAudioConnection(memberChannel);
		channel.sendMessage("Connecting to \uD83D\uDD0A" + memberChannel.getName()).queue();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean showInHelp() {
		return true;
	}

}
