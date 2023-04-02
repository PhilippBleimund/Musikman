package Dinkel.Musikman.Manager;

import java.util.List;
import java.util.function.Consumer;

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

	protected class ImplementationData{
		public Event event;
		public boolean publicExec;
		public InvokeMethod type;

		public ImplementationData(Event event, boolean publicExec, InvokeMethod type){
			this.event = event;
			this.publicExec = publicExec;
			this.type = type;
		}
	}

	protected void publicExec(boolean pub, Runnable r){
		if(pub)
			r.run();
	}
	
	protected Runnable createMessageRunnable(Event event, MessageCreateData message, InvokeMethod method){
		return createMessageRunnable(event, message, method, true);
	}

	protected Runnable createMessageRunnable(Event event, MessageCreateData message, InvokeMethod method, boolean onlyUser){
		return createMessageRunnable(event, message, method, onlyUser, null);
	}

	protected Runnable createMessageRunnable(Event event, MessageCreateData message, InvokeMethod method, boolean onlyUser, Consumer c){
		switch(method){
			case TEXT:
				MessageReceivedEvent textEvent = (MessageReceivedEvent) event;
				TextChannel channel = textEvent.getChannel().asTextChannel();
				if(c == null){
					return () -> channel.sendMessage(message).queue();
				}else{
					return () -> channel.sendMessage(message).queue(c);
				}
			case SLASH:
				SlashCommandInteractionEvent slashEvent = (SlashCommandInteractionEvent) event;
				if(c == null){
					return () -> slashEvent.reply(message).setEphemeral(onlyUser).queue();
				}else{
					return () -> slashEvent.reply(message).setEphemeral(onlyUser).queue(c);
				}
			case SLASH_DELAY:
				SlashCommandInteractionEvent slashDelayEvent = (SlashCommandInteractionEvent) event;
				if(c == null){
					return () -> slashDelayEvent.getHook().sendMessage(message).setEphemeral(onlyUser).queue();
				}else{
					return () -> slashDelayEvent.getHook().sendMessage(message).setEphemeral(onlyUser).queue(c);
				}
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
