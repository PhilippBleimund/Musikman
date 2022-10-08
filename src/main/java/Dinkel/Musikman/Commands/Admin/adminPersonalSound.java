package Dinkel.Musikman.Commands.Admin;

/*import java.io.File;
import java.util.List;

import Dinkel.Musikman.Information;
import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.Manager.customJoinSounds;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class adminPersonalSound implements Command{

	@Override
	public void commandCode(MessageReceivedEvent eventMessage, List<String> args) {
		TextChannel channel = eventMessage.getChannel().asTextChannel();
		
		Message message = eventMessage.getMessage();
		List<User> mentionedUsers = message.getMentionedUsers();
		User saveUser = mentionedUsers.get(0);
		
		List<Attachment> attachments = message.getAttachments();
		Attachment attachment = attachments.get(0);

		if(!attachment.getFileExtension().equalsIgnoreCase("mp3")) {
			channel.sendMessage("your attached file isnt mp3").queue();
			return;
		}
		attachment.downloadToFile(Information.getCustomSounds() + "/" + attachment.getFileName());
		
		long idUser = saveUser.getIdLong();
		
		customJoinSounds instance = customJoinSounds.getInstance();
		instance.addSoundEffect(new File(Information.getCustomSounds() + "/" + attachment.getFileName()), idUser);
		instance.saveSoundEffects();
		
		channel.sendMessage("added personal Sound for").mentionUsers(idUser).queue();
	}

	@Override
	public String[] getNames() {
		return new String[] {"personalSound"};
	}

	@Override
	public String[] getArgs() {
		return new String[] {"user"};
	}

	@Override
	public String getDescription() {
		return "set a personal sound for an user";
	}

	@Override
	public boolean showInHelp() {
		return true;
	}

}
*/