package Dinkel.Musikman.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import Dinkel.Musikman.Lavaplayer.GuildMusicManager;
import Dinkel.Musikman.Lavaplayer.PlayerManager;
import Dinkel.Musikman.helper.helper;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class MusicManager extends ListenerAdapter{
    
    private static MusicManager INSTANCE;

    public TextChannel channel;

    public String messageId;

    public static MusicManager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new MusicManager();
		}
		
		return INSTANCE;
	}

    public void update(){
        channel.editMessageById(messageId, "null").queue();
    }

    public MessageCreateData messageBuild(){

        MessageCreateBuilder message = new MessageCreateBuilder();
        
        GuildMusicManager musicManager = PlayerManager.getInstance().getMusikManager(channel.getGuild());
		BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
		
		if(queue.isEmpty()) {
			message.addContent("the queue is empty");
			return message.build();
		}
		
		int trackCount = Math.min(queue.size(), 20);
		List<AudioTrack> trackList = new ArrayList<>(queue);
        
        if(trackList.size() > trackCount) {
            message.addContent((trackList.size() - 20) + " more tracks ...\n");
        }
		
		for(int i=0;i<trackCount;i++) {
			AudioTrack track = trackList.get(i);
			AudioTrackInfo info = track.getInfo();
			
			message.addContent("#")
				.addContent(String.valueOf(i + 1))
				.addContent(" `")
				.addContent(info.title)
				.addContent(" by ")
				.addContent(info.author)
				.addContent("` [`")
				.addContent(helper.formatTime(track.getDuration()))
				.addContent("`]\n");
		}
		
        //message.setActionRow(null);

        return message.build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent  eventMessage) {
        if(eventMessage.getChannel().asTextChannel().getId().equals(channel.getId())){
            this.update();
        }
    }
}
