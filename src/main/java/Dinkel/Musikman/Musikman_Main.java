package Dinkel.Musikman;

import java.io.IOException;
import java.util.EnumSet;

import javax.security.auth.login.LoginException;

import org.apache.hc.core5.http.ParseException;

import Dinkel.Musikman.Commands.help;
import Dinkel.Musikman.Commands.status;
import Dinkel.Musikman.Commands.updateImageList;
import Dinkel.Musikman.Commands.Admin.admin;
import Dinkel.Musikman.Commands.MinecraftServer.getIp;
import Dinkel.Musikman.Commands.Music.join;
import Dinkel.Musikman.Commands.Music.leave;
import Dinkel.Musikman.Commands.Music.loop;
import Dinkel.Musikman.Commands.Music.move;
import Dinkel.Musikman.Commands.Music.nowPlaying;
import Dinkel.Musikman.Commands.Music.pause;
import Dinkel.Musikman.Commands.Music.play;
import Dinkel.Musikman.Commands.Music.queue;
import Dinkel.Musikman.Commands.Music.remove;
import Dinkel.Musikman.Commands.Music.saveQueue;
import Dinkel.Musikman.Commands.Music.shuffle;
import Dinkel.Musikman.Commands.Music.skip;
import Dinkel.Musikman.Commands.Music.stop;
import Dinkel.Musikman.Commands.Music.volume;
import Dinkel.Musikman.Commands.RandomGenerators.catrandom;
import Dinkel.Musikman.Commands.RandomGenerators.hentaiImage;
import Dinkel.Musikman.Commands.RandomGenerators.konachan;
import Dinkel.Musikman.Commands.RandomGenerators.rule34random;
import Dinkel.Musikman.Manager.CommandManager;
import Dinkel.Musikman.Manager.LogManager;
import Dinkel.Musikman.Manager.TicketManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
//import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

public class Musikman_Main {
	
	public static JDA jda;

	public static String prefix = "!";

	public static SpotifyApi spotifyApi;

	public String token;
	
	public static void main(String[] args) throws LoginException {
		Thread printingHook = new Thread(() -> System.out.println("In the middle of a shutdown"));
		new Musikman_Main(args);
	}

	public Musikman_Main(String[] args) throws LoginException {
		
		if(args[0].equals("MusikMan")) {
			token = Information.TokenMusikMan;
			prefix = "!";
		}else if (args[0].equals("MusikFrau")) {
			token = Information.TokenMusikFrau;
			prefix = "?";
		}else {
			System.out.println("MusikMan or MusikFrau");
			System.exit(0);
		}
		
		jda = JDABuilder.createDefault(token)
						.enableIntents(EnumSet.allOf(GatewayIntent.class))
						.build();

		jda.getPresence().setStatus(OnlineStatus.ONLINE);

		CommandManager manager = CommandManager.getInstance();
		manager.addCommand(new help());
		manager.addCommand(new join());
		manager.addCommand(new play());
		manager.addCommand(new stop());
		manager.addCommand(new skip());
		manager.addCommand(new nowPlaying());
		manager.addCommand(new queue());
		manager.addCommand(new leave());
		manager.addCommand(new pause());
		manager.addCommand(new rule34random());
		manager.addCommand(new catrandom());
		manager.addCommand(new loop());
		manager.addCommand(new shuffle());
		manager.addCommand(new admin());
		manager.addCommand(new remove());
		manager.addCommand(new move());
		manager.addCommand(new konachan());
		manager.addCommand(new saveQueue());
		manager.addCommand(new volume());
		manager.addCommand(new updateImageList());
		manager.addCommand(new status());
		//manager.addCommand(new addCustomSound());
		manager.addCommand(new hentaiImage());
		manager.addCommand(new getIp());
		jda.addEventListener(manager);
		jda.addEventListener(TicketManager.getInstance());
		jda.addEventListener(new LogManager());
		//jda.addEventListener(customJoinSounds.getInstance());

		try {
			jda.awaitReady();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (args.length != 0 && args[0].matches("[+-]?\\d*(\\.\\d+)?")) {
			TextChannel channel = jda.getTextChannelById(args[0]);
			channel.sendMessage("im back on").queue();
		}

		initSpotify();
	}

	public JDA getJDA(){
		return this.jda;
	}

	public void initSpotify() {
		spotifyApi = new SpotifyApi.Builder().setClientId(Information.spotify_clientId)
				.setClientSecret(Information.spotify_clientSecret).build();
		ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
		try {
			final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

			spotifyApi.setAccessToken(clientCredentials.getAccessToken());

			System.out.println("Expires in: " + clientCredentials.getExpiresIn());
		} catch (IOException | SpotifyWebApiException | ParseException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}