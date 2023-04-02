package Dinkel.Musikman.Commands.Music;

import java.util.List;

import Dinkel.Musikman.Lavaplayer.GuildMusicManager;
import Dinkel.Musikman.Lavaplayer.PlayerManager;
import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

public class loop extends Command{

	@Override
	public void textImplementation(MessageReceivedEvent eventMessage, List<String> args, boolean publicExec) {
		TextChannel channel = eventMessage.getChannel().asTextChannel();
		if(args.size() <= 0) {
			this.publicExec(publicExec, () -> {channel.sendMessage("add arg: `[track]`, `[queue]`, `[off]`").queue();});
			return;
		}
		
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

		if(args.get(0).equals("queue")) {
			musicManager.scheduler.loopQueue();
			this.publicExec(publicExec, () -> {channel.sendMessage("the queue will be looping").queue();	});
		}else if(args.get(0).equalsIgnoreCase("track")) {
			boolean newRepeating = !musicManager.scheduler.repeating;
			musicManager.scheduler.repeating = newRepeating;
			this.publicExec(publicExec, () -> {channel.sendMessageFormat("The player has been set to **%s**", newRepeating ? "repeating" : "not repeating").queue();});
		}else if(args.get(0).equalsIgnoreCase("off")) {
			musicManager.scheduler.loopOffQueue();
			musicManager.scheduler.repeating = false;
			this.publicExec(publicExec, () -> {channel.sendMessage("All loops were disactivated").queue();});
		}
	}

	@Override
	public void slashImplementation(SlashCommandInteractionEvent eventMessage, boolean publicExec) {
		this.publicExec(publicExec, this.createMessageRunnable(eventMessage, new MessageCreateBuilder().setContent("this slash command is still in work").build(), InvokeMethod.SLASH));
	}

	@Override
	public String[] getNames() {
		return new String[] {"loop"};
	}

	@Override
	public String getDescription() {
		return "loops the cuurent queue";
	}

	@Override
	public String[] getArgs() {
		return new String[] {"track", "queue", "off"};
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
