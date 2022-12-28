package Dinkel.Musikman.Commands.MinecraftServer;

import java.util.List;

import Dinkel.Musikman.Information;
import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.helper.helper;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class getIp extends Command{

    @Override
    public void commandCode(MessageReceivedEvent eventMessage, List<String> args, boolean publicExec) {
        MessageChannelUnion messageActionUnion = eventMessage.getChannel();
		TextChannel messageAction = null;
        PrivateChannel privateMessageAction = null;
        if(messageActionUnion.getType() == ChannelType.TEXT)
            messageAction = messageActionUnion.asTextChannel();
        else
            privateMessageAction = messageActionUnion.asPrivateChannel();
        
        long AuthorId = eventMessage.getAuthor().getIdLong();

        if(Information.isMinecraftPlayer(AuthorId)){
            String ip = "all Services are down";
            try {
                ip = helper.getIp();
            } catch (Exception e) {
                e.printStackTrace();
            }
            helper.sendPrivateMessage(eventMessage.getAuthor(), "The current ip is: `" + ip + "`");
        }else{
            if(messageActionUnion.getType() == ChannelType.TEXT)
                messageAction.sendMessage("You are not allowed to use this command. Please contact one of the Server Admins: \n ```Philipp Bleimund: <@" + Information.admins[0] + "> \nSimon: <@" + Information.admins[1] + ">```").queue();
            else
                privateMessageAction.sendMessage("You are not allowed to use this command. Please contact one of the Server Admins: \n ```Philipp Bleimund: <@" + Information.admins[0] + "> \nSimon: <@" + Information.admins[1] + ">```").queue();
        }
    }

    @Override
    public String[] getNames() {
        return new String[]{"getIp"};
    }

    @Override
    public String[] getArgs() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean showInHelp() {
        return false;
    }
    
}
