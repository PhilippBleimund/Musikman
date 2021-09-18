package Dinkel.Musikman.Lavaplayer;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import Dinkel.Musikman.Manager.TicketManager;
import Dinkel.Musikman.Tickets.playlistTicket;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class PlayerManager {

	private static PlayerManager INSTANCE;
	
	private Map<Long, GuildMusicManager> musicManagers;
	private AudioPlayerManager audioPlayerManager;
	
	public PlayerManager() {
		this.musicManagers = new HashMap<>();
		this.audioPlayerManager = new DefaultAudioPlayerManager();
		
		AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
		AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
	}
	
	public GuildMusicManager getMusikManager(Guild guild) {
		return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);

            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

            return guildMusicManager;
        });
	}
	
	public void loadAndPlay(TextChannel channel, String trackURL, TicketManager ticketManager) {
		GuildMusicManager musicManager = this.getMusikManager(channel.getGuild());
		
		this.audioPlayerManager.loadItemOrdered(musicManager, trackURL, new AudioLoadResultHandler() {

			@Override
			public void trackLoaded(AudioTrack track) {
				musicManager.scheduler.queue(track);
				
				channel.sendMessage("Adding to queue: `")
					.append(track.getInfo().title)
					.append("` by `")
					.append(track.getInfo().author)
					.append("`")
					.queue();
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				List<AudioTrack> tracks = playlist.getTracks();
				
				if(trackURL.startsWith("ytsearch:")) {
					channel.sendMessage("`Warning` search is playlist with `")
						.append(String.valueOf(tracks.size()))
						.append("` Titles. do you want to add?")
						.queue(message -> {
							message.addReaction("👍").queue();
							message.addReaction("👎").queue();
							long messageId = message.getIdLong();
							ticketManager.addTicket(new playlistTicket(tracks, messageId));
						});
					return;
				}
				
				
				channel.sendMessage("Adding to queue: `")
				.append(String.valueOf(tracks.size()))
				.append("` tracks from playlist `")
				.append(playlist.getName())
				.append("`")
				.queue();
				
				for(AudioTrack track : tracks) {
					musicManager.scheduler.queue(track);
				}
			}

			@Override
			public void noMatches() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void loadFailed(FriendlyException exception) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	public static PlayerManager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new PlayerManager();
		}
		
		return INSTANCE;
	}
}
