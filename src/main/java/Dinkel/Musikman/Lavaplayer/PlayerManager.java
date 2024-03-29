package Dinkel.Musikman.Lavaplayer;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
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

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;

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

		this.audioPlayerManager.loadItem(new AudioReference(location.getAbsolutePath(), "local"),
				new AudioLoadResultHandler() {

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
	}

	public void loadSpotifyPlaylist(TextChannel channel, String[] trackURLs) {
		GuildMusicManager musicManager = this.getMusikManager(channel.getGuild());

		class ResultHandler implements AudioLoadResultHandler {

			int index;

			public ResultHandler(int index) {
				super();
				this.index = index;
			}

			@Override
			public void trackLoaded(AudioTrack track) {
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				List<AudioTrack> tracksSearch = playlist.getTracks();

				musicManager.scheduler.queue(tracksSearch.get(0));
			}

			@Override
			public void noMatches() {
			}

			@Override
			public void loadFailed(FriendlyException exception) {
			}
		}

		for (int i = 0; i < trackURLs.length; i++) {
			this.audioPlayerManager.loadItemOrdered(musicManager, trackURLs[i], new ResultHandler(i));
		}
	}

	public void loadAndPlay(TextChannel channel, String trackURL) {
		GuildMusicManager musicManager = this.getMusikManager(channel.getGuild());

		this.audioPlayerManager.loadItemOrdered(musicManager, trackURL, new AudioLoadResultHandler() {

			@Override
			public void trackLoaded(AudioTrack track) {
				musicManager.scheduler.queue(track);

				channel.sendMessage("Adding to queue: `").addContent(track.getInfo().title).addContent("` by `")
						.addContent(track.getInfo().author).addContent("`").queue();
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				List<AudioTrack> tracks = playlist.getTracks();

				if (trackURL.startsWith("ytsearch:")) {
					musicManager.scheduler.queue(tracks.get(0));
					channel.sendMessage("Adding to queue: `").addContent(tracks.get(0).getInfo().title).addContent("` by `")
							.addContent(tracks.get(0).getInfo().author).addContent("`").queue();
					return;
				}

				channel.sendMessage("Adding to queue: `").addContent(String.valueOf(tracks.size()))
						.addContent("` tracks from playlist `").addContent(playlist.getName()).addContent("`").queue();

				for (AudioTrack track : tracks) {
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
		if (INSTANCE == null) {
			INSTANCE = new PlayerManager();
		}

		return INSTANCE;
	}
}
