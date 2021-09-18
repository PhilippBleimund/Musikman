package Dinkel.Musikman.Lavaplayer;

import java.nio.ByteBuffer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;

import net.dv8tion.jda.api.audio.AudioSendHandler;

public class AudioPlayerSendHandler implements AudioSendHandler{

	private AudioPlayer audioPlayer ;
	private ByteBuffer buffer;
	private MutableAudioFrame frame;
	
	public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
		this.audioPlayer = audioPlayer;
		this.buffer = ByteBuffer.allocate(1024);
		this.frame = new MutableAudioFrame();
		this.frame.setBuffer(buffer);
	}
	
	public boolean canProvide() {
		return this.audioPlayer.provide(this.frame);
	}

	public ByteBuffer provide20MsAudio() {
		return this.buffer.flip();
	}

}
