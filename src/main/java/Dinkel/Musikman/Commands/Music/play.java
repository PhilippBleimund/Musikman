package Dinkel.Musikman.Commands.Music;

import java.io.IOException;
import java.util.List;

import org.apache.hc.core5.http.ParseException;

import Dinkel.Musikman.Musikman_Main;
import Dinkel.Musikman.Lavaplayer.GuildMusicManager;
import Dinkel.Musikman.Lavaplayer.PlayerManager;
import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.Manager.CommandManager;
import Dinkel.Musikman.helper.helper;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.IPlaylistItem;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;

public class play extends Command {

	@Override
	public void commandCode(MessageReceivedEvent eventMessage, List<String> args, boolean publicExec) {
		this.publicExec(publicExec, () -> {});
		TextChannel channel = eventMessage.getChannel().asTextChannel();

		if (args.isEmpty()) {
			this.publicExec(publicExec, () -> {channel.sendMessage("add arguments").queue();});
			return;
		}

		Member self = eventMessage.getGuild().getSelfMember();
		GuildVoiceState selfVoiceState = self.getVoiceState();

		if (!selfVoiceState.inAudioChannel()) {
			CommandManager.getInstance().CommandRequest(eventMessage, "join");
			return;
		}

		Member member = eventMessage.getMember();
		GuildVoiceState memberVoiceState = member.getVoiceState();

		if (!memberVoiceState.inAudioChannel()) {
			this.publicExec(publicExec, () -> {channel.sendMessage("You are not in a channel").queue();});
			return;
		}

		if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			this.publicExec(publicExec, () -> {channel.sendMessage("we are not in the same voice channel").queue();});
			return;
		}

		String link = String.join(" ", args);

		GuildMusicManager musicManager = PlayerManager.getInstance().getMusikManager(eventMessage.getGuild());

		String[] spotify = helper.getSpotify(link);
		if (spotify != null) {
			switch (spotify[1]) {
			case "track":
				addSpotifyTrack(channel, spotify[0]);
			/*case "playlist":
				String[] spotifyPlaylist = getSpotifyPlaylist(spotify[0], 0);
				for(int i=0;i<spotifyPlaylist.length;i++) {
					addSpotifyTrack(channel, spotifyPlaylist[i]);
				}*/
			}
			return;
		}

		if (!helper.isURL(link)) {
			link = "ytsearch:" + link;
			PlayerManager.getInstance().loadAndPlay(channel, link);
			return;
		}

		String arg1 = args.get(0);
		if (helper.isInteger(arg1)) {
			int number = Integer.parseInt(arg1);
			int size = musicManager.scheduler.queue.size();
			if (number > size) {
				this.publicExec(publicExec, () -> {channel.sendMessage("track number is too big").queue();});
				return;
			}
			musicManager.scheduler.directPlay(number);
			this.publicExec(publicExec, () -> {channel.sendMessage("skiped to postion `" + number + "`").queue();});
			return;
		}

		PlayerManager.getInstance().loadAndPlay(channel, link);
	}

	@Override
	public String[] getNames() {
		return new String[] { "play", "p" };
	}

	@Override
	public String getDescription() {
		return "add the song to the queue";
	}

	@Override
	public String[] getArgs() {
		return new String[] { "URL", "smart search(title)", "position in queue" };
	}

	@Override
	public boolean showInHelp() {
		return true;
	}

	private void addSpotifyTrack(TextChannel channel, String id) {
		SpotifyApi spotifyApi = Musikman_Main.spotifyApi;
		GetTrackRequest getTrackRequest = spotifyApi.getTrack(id).build();
		try {
			final Track track = getTrackRequest.execute();
			ArtistSimplified[] artists = track.getArtists();

			String link = "ytsearch:" + track.getName() + " " + artists[0].getName();
			PlayerManager.getInstance().loadAndPlay(channel, link);
		} catch (IOException | SpotifyWebApiException | ParseException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private String[] getSpotifyPlaylist(String id, int offset) {
		SpotifyApi spotifyApi = Musikman_Main.spotifyApi;
		GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi.getPlaylistsItems(id)
				.offset(offset)
				.build();
		try {
			final Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsItemsRequest.execute();
			PlaylistTrack[] items = playlistTrackPaging.getItems();
			String[] itemsIds;
			if(items.length == 100) {
				String[] spotifyPlaylist = getSpotifyPlaylist(id, offset + 100);
				itemsIds = new String[items.length + spotifyPlaylist.length];
				for(int i=100;i<itemsIds.length;i++) {
					itemsIds[i] = spotifyPlaylist[i - 100];
				}
			}else {
				itemsIds = new String[items.length];
			}
			for (int i = 0; i < items.length; i++) {
				IPlaylistItem track = items[i].getTrack();
				itemsIds[i] = track.getId();
			}
			return itemsIds;
		} catch (IOException | SpotifyWebApiException | ParseException e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
	}

	@Override
	public boolean NSFW() {
		return false;
	}
}
