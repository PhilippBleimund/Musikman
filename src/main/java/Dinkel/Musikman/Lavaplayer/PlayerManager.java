package Dinkel.Musikman.Lavaplayer;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.bandcamp.BandcampAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.beam.BeamAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.http.HttpAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.local.LocalAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.soundcloud.SoundCloudAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.twitch.TwitchStreamAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.vimeo.VimeoAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioReference;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import Dinkel.Musikman.Manager.TicketManager;
import Dinkel.Musikman.Tickets.playlistTicket;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class PlayerManager {

	private static PlayerManager INSTANCE;
	
	private Map<Long, GuildMusicManager> musicManagers;
	private AudioPlayerManager audioPlayerManager;
	private LocalAudioSourceManager localSourceManager;
	
	public PlayerManager() {
		this.musicManagers = new HashMap<>();
		this.audioPlayerManager = new DefaultAudioPlayerManager();
		
		audioPlayerManager.registerSourceManager(new YoutubeAudioSourceManager());
		audioPlayerManager.registerSourceManager(SoundCloudAudioSourceManager.createDefault());
		audioPlayerManager.registerSourceManager(new BandcampAudioSourceManager());
		audioPlayerManager.registerSourceManager(new VimeoAudioSourceManager());
		audioPlayerManager.registerSourceManager(new TwitchStreamAudioSourceManager());
		audioPlayerManager.registerSourceManager(new BeamAudioSourceManager());
		audioPlayerManager.registerSourceManager(new HttpAudioSourceManager());
		audioPlayerManager.registerSourceManager(new LocalAudioSourceManager());
	}
	
	public GuildMusicManager getMusikManager(Guild guild) {
		return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);

            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

            return guildMusicManager;
        });
	}
	
	/**
	 * No response to any channel
	 * 
	 * @param voice
	 * @param location
	 */
	public void loadLocalFileSilent(VoiceChannel voice, File location) {
		GuildMusicManager musicManager = this.getMusikManager(voice.getGuild());
		
		this.audioPlayerManager.loadItem(new AudioReference(location.getAbsolutePath(), "local"), new AudioLoadResultHandler() {

			@Override
			public void trackLoaded(AudioTrack track) {
				musicManager.scheduler.directPlay(track);
				System.out.println("join Sound played");
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				// TODO Auto-generated method stub
				
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
		
		//this.audioPlayerManager.loadItemOrdered(musicManager,new AudioReference(location.getAbsolutePath(), "local"), null);
	}
	
	public void loadAndPlay(TextChannel channel, String trackURL) {
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
					musicManager.scheduler.queue(tracks.get(0));
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
