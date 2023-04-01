package Dinkel.Musikman.Manager;

import java.util.List;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public abstract class Command {

	public CommandData commandAPI;

	public enum InvokeMethod{
		TEXT,
		SLASH,
		SLASH_DELAY
	}

	public abstract void textImplementation(MessageReceivedEvent  eventMessage, List<String> args, boolean publicExec);

	public abstract void slashImplementation(SlashCommandInteractionEvent eventMessage, boolean publicExec);

	protected void publicExec(boolean pub, Runnable r){
		if(pub)
			r.run();
	}
	
	protected Runnable createMessageRunnable(Event event, MessageCreateData message, InvokeMethod method){
		return createMessageRunnable(event, message, method, false);
	}

	protected Runnable createMessageRunnable(Event event, MessageCreateData message, InvokeMethod method, boolean slashGlobal){
		switch(method){
			case TEXT:
				MessageReceivedEvent textEvent = (MessageReceivedEvent) event;
				TextChannel channel = textEvent.getChannel().asTextChannel();
				return () -> channel.sendMessage(message).queue();
			case SLASH:
				SlashCommandInteractionEvent slashEvent = (SlashCommandInteractionEvent) event;
				return () -> slashEvent.reply(message).setEphemeral(slashGlobal).queue();
			case SLASH_DELAY:
				SlashCommandInteractionEvent slashDelayEvent = (SlashCommandInteractionEvent) event;
				return () -> slashDelayEvent.getHook().sendMessage(message).setEphemeral(slashGlobal).queue();
		}
		return () -> {};
	}

	public abstract String[] getNames();
	
	public abstract String[] getArgs();
	
	public abstract String getDescription();
	
	public abstract boolean showInHelp();

	public abstract boolean NSFW();

	protected CommandData getCommandData(){
		if(commandAPI == null)
			return Commands.slash(getNames()[0], getDescription());
		else
			return commandAPI;
	}
}
