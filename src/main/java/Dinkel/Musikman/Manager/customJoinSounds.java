package Dinkel.Musikman.Manager;

/*import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Dinkel.Musikman.Information;
import Dinkel.Musikman.Lavaplayer.PlayerManager;
import Dinkel.Musikman.SaveData.saveManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceUpdateEvent;
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

	private static customJoinSounds INSTANCE;
	
	private List<soundEffect> sounds = readSoundEffects();
		
	public void addSoundEffect(File location, long userId) {
		for(int i=0;i<sounds.size();i++) {
			if(sounds.get(i).userId == userId) {
				sounds.get(i).location = location;
			}else {
				this.sounds.add(new soundEffect(location, userId));				
			}
		}
	}
	
	public void saveSoundEffects() {
		JSONObject obj = new JSONObject();
		JSONArray soundArray = new JSONArray();
		for(int i=0;i<sounds.size();i++) {
			soundEffect s = sounds.get(i);
			JSONObject effect = new JSONObject();
			effect.put("location", s.location.toString());
			effect.put("userId", s.userId);
			soundArray.add(effect);
		}
		obj.put("soundEffects", soundArray);
		saveManager.saveJSON(obj, new File(Information.getCustomSounds() + "/joinSoundsIndex.json"));
	}
	
	public List<soundEffect> readSoundEffects(){
		List<soundEffect> soundsRead = new ArrayList<soundEffect>();
		JSONObject obj = saveManager.loadJSON(new File(Information.getCustomSounds() + "/joinSoundsIndex.json"));
		if(obj != null) {
			JSONArray soundArray = (JSONArray) obj.get("soundEffects");
			for(Object o : soundArray) {
				JSONObject JO = (JSONObject) o;
				soundsRead.add(new soundEffect(
						new File((String) JO.get("location")),
						(long) JO.get("userId")));
			}
		}
		return soundsRead;
	}
	
	@Override
	public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
		this.handleCommand(event);
	}
	
	@Override
	public void onGuildVoiceMove(@Nonnull GuildVoiceMoveEvent event) {
		this.handleCommand(event);
	}
	
	private void handleCommand(@Nonnull GenericGuildVoiceUpdateEvent event) {
		Member self = event.getGuild().getSelfMember();
		GuildVoiceState voiceState = self.getVoiceState();
		VoiceChannel channel = voiceState.getChannel();
		for(soundEffect s : sounds) {
			if(s.userId == event.getMember().getIdLong() && channel == event.getChannelJoined()) {
				PlayerManager.getInstance().loadLocalFileSilent(event.getChannelJoined(), s.location);
			}
		}
	}
	
	public static customJoinSounds getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new customJoinSounds();
		}
		
		return INSTANCE;
	}
}
*/