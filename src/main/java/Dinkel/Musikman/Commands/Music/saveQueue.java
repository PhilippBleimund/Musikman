package Dinkel.Musikman.Commands.Music;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import Dinkel.Musikman.Lavaplayer.GuildMusicManager;
import Dinkel.Musikman.Lavaplayer.PlayerManager;
import Dinkel.Musikman.Lavaplayer.TrackScheduler;
import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.SaveData.saveManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class saveQueue implements Command{

	@Override
	public void commandCode(MessageReceivedEvent eventMessage, List<String> args) {
		saveManager save = new saveManager();
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusikManager(eventMessage.getGuild());
		TrackScheduler scheduler = musicManager.scheduler;
		BlockingQueue<AudioTrack> queue = scheduler.queue;
		try {
			save.writeAudioTracks(new ArrayList<AudioTrack>(queue));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String[] getNames() {
		return new String[] {"savequeue"};
	}

	@Override
	public String[] getArgs() {
		return new String[] {"queue name"};
	}

	@Override
	public String getDescription() {
		return "saves the queue";
	}

	@Override
	public boolean showInHelp() {
		return true;
	}

}
