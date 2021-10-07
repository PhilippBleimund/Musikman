package Dinkel.Musikman.Commands;

import java.io.File;
import java.util.List;

import Dinkel.Musikman.Information;
import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.Manager.customJoinSounds;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class addCustomSound implements Command{

	private File soundLocation = Information.getCustomSounds();
	
	@Override
	public void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args) {
		TextChannel channel = eventMessage.getChannel();
		
		Message message = eventMessage.getMessage();
		List<Attachment> attachments = message.getAttachments();
		Attachment attachment = attachments.get(0);

		if(!attachment.getFileExtension().equalsIgnoreCase("mp3")) {
			channel.sendMessage("your attached file isnt mp3").queue();
			return;
		}
		attachment.downloadToFile(soundLocation + "/" + attachment.getFileName());
		
		User author = eventMessage.getAuthor();
		long idLong = author.getIdLong();
		
		customJoinSounds instance = customJoinSounds.getInstance();
		instance.addSoundEffect(new File(soundLocation + "/" + attachment.getFileName()), idLong);
		instance.saveSoundEffects();
		
		channel.sendMessage("added your personal Sound").queue();
	}

	@Override
	public String[] getNames() {
		return new String[] {"addCustomSound"};
	}

	@Override
	public String[] getArgs() {
		return new String[] {"attache your mp3 file"};
	}

	@Override
	public String getDescription() {
		return "add a custom Sound that gets played when you join a Channel with `Muskman`";
	}

	@Override
	public boolean showInHelp() {
		return true;
	}

}