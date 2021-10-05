package Dinkel.Musikman.Manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import Dinkel.Musikman.Lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

class soundEffect{
	File location;
	long userId;
	
	public soundEffect(File location, long userId) {
		this.location = location;
		this.userId = userId;
	}
}

public class customJoinSounds extends ListenerAdapter{
	
	private List<soundEffect> sounds = new ArrayList<soundEffect>();
	
	public customJoinSounds() {
		sounds.add(new soundEffect(new File("C:\\Users\\Philipp Bleimund\\Music\\Musikman\\hd-stardust-crusaders-za-warudo.mp3"), 406540701762060318l));
	}
	
	@Override
	public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
		Member self = event.getGuild().getSelfMember();
		GuildVoiceState voiceState = self.getVoiceState();
		VoiceChannel channel = voiceState.getChannel();
		for(soundEffect s : sounds) {
			if(s.userId == event.getMember().getIdLong() && channel == event.getChannelJoined()) {
				PlayerManager.getInstance().loadLocalFileSilent(event.getChannelJoined(), s.location);
			}
		}
	}
	
	@Override
	public void onGuildVoiceMove(@Nonnull GuildVoiceMoveEvent event) {
		Member self = event.getGuild().getSelfMember();
		GuildVoiceState voiceState = self.getVoiceState();
		VoiceChannel channel = voiceState.getChannel();
		for(soundEffect s : sounds) {
			if(s.userId == event.getMember().getIdLong() && channel == event.getChannelJoined()) {
				PlayerManager.getInstance().loadLocalFileSilent(event.getChannelJoined(), s.location);
			}
		}
	}
}
