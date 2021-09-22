package Dinkel.Musikman.Lavaplayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

public class TrackScheduler extends AudioEventAdapter {
	
	public AudioPlayer player;
	public BlockingQueue<AudioTrack> queue;
	public BlockingQueue<AudioTrack> loopingQueue;
	public boolean repeating = false;
	private boolean loopQueue = false;

	public TrackScheduler(AudioPlayer player) {
		this.player = player;
		this.queue = new LinkedBlockingQueue<AudioTrack>();
		this.loopingQueue = new LinkedBlockingQueue<AudioTrack>();
	}
	
	public void queue(AudioTrack track) {
		if(!this.player.startTrack(track, true)) {
			this.queue.offer(track);
		}
	}
	
	public void shuffleQueue() {
		List<AudioTrack> tracks = new ArrayList<AudioTrack>(queue);

		Collections.shuffle(tracks);
		queue.clear();
		queue = new LinkedBlockingQueue<AudioTrack>(tracks);
		if(loopQueue) {
			loopQueue();
		}
	}
	
	public void loopQueue() {
		loopQueue = true;
		loopingQueue = new LinkedBlockingQueue<AudioTrack>(queue);
	}
	
	public void loopOffQueue() {
		loopQueue = false;
		loopingQueue = null;
	}
	
	public void removeRange(int a, int b) {
		List<AudioTrack> tracks = new ArrayList<AudioTrack>(queue);
		List<AudioTrack> removeTracks = new ArrayList<AudioTrack>();
		for(int i=a-1;i<b;i++) {
			removeTracks.add(tracks.get(i));
		}
		queue.removeAll(removeTracks);
	}
	
	public void moveTrack(int a, int b) {
		List<AudioTrack> tracks = new ArrayList<AudioTrack>(queue);
		AudioTrack track1 = tracks.get(a-1);
		tracks.add(b-1, track1);
		tracks.remove(track1);
		queue.clear();
		queue = new LinkedBlockingQueue<AudioTrack>(tracks);
	}
	
	public void nextTrack() {
		if(loopQueue) {
			AudioTrack track = loopingQueue.poll();
			this.player.startTrack(track, false);
			loopingQueue.add(track.makeClone());
		}else
			this.player.startTrack(this.queue.poll(), false);
	}
	
	public void directPlay(int position) {
		AudioTrack[] tracks = new AudioTrack[queue.size()];
		queue.toArray(tracks);
		this.player.startTrack(tracks[position + 1].makeClone(), false);
	}
	
	@Override
  	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		if(endReason.mayStartNext) {
			if(this.repeating) {
				this.player.startTrack(track.makeClone(), false);
				return;
			}
			nextTrack();
		}
	}

}
