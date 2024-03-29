package Dinkel.Musikman.Commands.Music;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import Dinkel.Musikman.Lavaplayer.GuildMusicManager;
import Dinkel.Musikman.Lavaplayer.PlayerManager;
import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.helper.helper;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class skip extends Command{

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
		
		if(audioPlayer.getPlayingTrack() == null) {
			this.publicExec(publicExec, () -> {channel.sendMessage("there is no track playing").queue();});
			return;
		}
		
		if(args.size() <= 0) {
			if(musicManager.scheduler.queue.size() == 0) {
				this.publicExec(publicExec, () -> {channel.sendMessage("queue is empty").queue();});
				return;
			}
			musicManager.scheduler.nextTrack();
			this.publicExec(publicExec, () -> {channel.sendMessage("current track skipped").queue();});
		}else {
			String arg0 = args.get(0);
			if(!helper.isInteger(arg0)) {
				this.publicExec(publicExec, () -> {channel.sendMessage("`" + arg0 + "` is not a valid number(1, 2, 3,...)").queue();});
				return;
			}
			int position = Integer.valueOf(args.get(0));
			if(musicManager.scheduler.queue.size() > position || musicManager.scheduler.queue.size() <= 0) {
				this.publicExec(publicExec, () -> {channel.sendMessage("track " + "#" + position + " is not on queue");});
				return;
			}else {
				for(int i=0;i<position;i++) {
					musicManager.scheduler.nextTrack();
				}
				AudioTrack track = audioPlayer.getPlayingTrack();
				AudioTrackInfo info = track.getInfo();
				this.publicExec(publicExec, () -> {channel.sendMessage("skiped to `" + info.title + "`").queue();});
				
			}
		}
	}

	@Override
	public String[] getNames() {
		return new String[]{"skip", "s"};
	}

	@Override
	public String getDescription() {
		return "skips the cuurent track or to the position in the queue";
	}

	@Override
	public String[] getArgs() {
		return new String[] {"queue id"};
	}

	@Override
	public boolean showInHelp() {
		return true;
	}

}
