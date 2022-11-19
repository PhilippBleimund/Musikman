package Dinkel.Musikman.Commands;

import java.util.List;

import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.Manager.MusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;

public class musicInit implements Command{
    
    @Override
    public void commandCode(MessageReceivedEvent eventMessage, List<String> args) {
        Guild guild = eventMessage.getGuild();
        List<TextChannel> textChannelsByName2 = guild.getTextChannelsByName("musicchannel", false);
        if(textChannelsByName2.isEmpty()){
            guild.createTextChannel("musicchannel").complete();
        }
        List<TextChannel> textChannelsByName = guild.getTextChannelsByName("musicchannel", false);
        MusicManager.getInstance().channel = textChannelsByName.get(0);

        MusicManager.getInstance().channel.sendMessage(MusicManager.getInstance().messageBuild()).queue((message) -> {
            String messageId = message.getId();
            MusicManager.getInstance().messageId = messageId;
        });;
    }


    @Override
    public String[] getNames() {
        return new String[]{"musicInit"};
    }


    @Override
    public String[] getArgs() {
        return null;
    }

    @Override
    public String getDescription() {
        return "creates a music text channel";
    }


    @Override
    public boolean showInHelp() {
        return false;
    }
}
