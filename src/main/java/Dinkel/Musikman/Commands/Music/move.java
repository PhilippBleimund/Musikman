package Dinkel.Musikman.Commands.Music;

import java.util.List;

import Dinkel.Musikman.Lavaplayer.GuildMusicManager;
import Dinkel.Musikman.Lavaplayer.PlayerManager;
import Dinkel.Musikman.Lavaplayer.TrackScheduler;
import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.helper.helper;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class move implements Command{

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
		TrackScheduler scheduler = musicManager.scheduler;
		if(scheduler.queue.size() == 0) {
			channel.sendMessage("queue is empty").queue();
			return;
		}
		
		if(args.size() < 2) {
			channel.sendMessage("not enought arguments. See `!help`").queue();
			return;
		}
		String arg1 = args.get(0);
		String arg2 = args.get(1);
		if(helper.isInteger(arg1) && helper.isInteger(arg2)) {
			scheduler.moveTrack(Integer.valueOf(arg1), Integer.valueOf(arg2));
			channel.sendMessage("moved track `" + arg1 + " to " + arg2 +"`").queue();
			return;
		}else {
			channel.sendMessage("`" + arg1 + "` or `" + arg2 + "` is not a valid number(1, 2, 3,...)").queue();
			return;
		}
	}

	@Override
	public String[] getNames() {
		return new String[] {"move"};
	}

	@Override
	public String[] getArgs() {
		return new String[] {"track newPos."};
	}

	@Override
	public String getDescription() {
		return "moved a track to a new position";
	}

	@Override
	public boolean showInHelp() {
		return true;
	}

}
