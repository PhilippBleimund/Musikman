package Dinkel.Musikman.Commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Dinkel.Musikman.Information;
import Dinkel.Musikman.Commands.RandomGenerators.hentaiImage;
import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.Manager.CommandManager;
import Dinkel.Musikman.SaveData.saveManager;
import Dinkel.Musikman.helper.helper;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class updateImageList extends Command {

    String messageId = "";

    @Override
    public void commandCode(MessageReceivedEvent eventMessage, List<String> args, boolean publicExec) {
        
        
        eventMessage.getChannel().sendMessage("starting Process").queue((message) -> {
            String id = message.getId();
            messageId = id;
        });;

        while(messageId != ""){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        eventMessage.getChannel().editMessageById(messageId, "prepare download URL").queue();
        URL download = null;
        try {
            download = prepareURL(args.get(0));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        

        eventMessage.getChannel().editMessageById(messageId, "downloading").queue();
        JSONObject raw = null;
        try {
            raw = downloadRaw(download);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        eventMessage.getChannel().editMessageById(messageId, "format downloaded JSON").queue();
        JSONObject links = formatJSON(raw);


        eventMessage.getChannel().editMessageById(messageId, "saving JSON").queue();
        saveManager.saveJSON(links, Information.getHentaiImages());

        eventMessage.getChannel().editMessageById(messageId, "updating current List").queue();
        CommandManager manager = CommandManager.getInstance();
        for(Command c : manager.commands){
            if(c instanceof hentaiImage){
                hentaiImage h = (hentaiImage) c;
                h.updateList();
            }
        }

        eventMessage.getChannel().editMessageById(messageId, "everything up to date").queue();
    }

    private JSONObject formatJSON(JSONObject raw){
        JSONArray messages = (JSONArray) raw.get("messages");
        JSONArray urls = new JSONArray();
		
		for(int i=messages.size()-1;i>200000;i--) {
			JSONObject message = (JSONObject) messages.get(i);
			JSONArray attachments = (JSONArray) message.get("attachments");
			for(int j=0;j<attachments.size();j++) {
				JSONObject attachment = (JSONObject) attachments.get(j);
				String URL = (String) attachment.get("url");
				String fileFormat = helper.getFileFormat(URL);
				switch(fileFormat) {
				case "jpg": 
					urls.add(URL);
					break;
				case "png":
					urls.add(URL);
				}
			}
		}
		
		JSONObject obj = new JSONObject();
		obj.put("images", urls);
        return obj;
    }

    private JSONObject downloadRaw(URL downloadURL) throws IOException, ParseException{
        JSONParser json = new JSONParser();
        JSONObject raw = new JSONObject();
        InputStream in = downloadURL.openStream();
        Scanner s = new Scanner(in).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        raw = (JSONObject) json.parse(result);

        return raw;
    }

    private URL prepareURL(String id) throws IOException, ParseException{
        URL url;
        String outputLine = "";

            url = new URL("https://api.anonfiles.com/v2/file/" + id + "/info");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            StringBuffer content = new StringBuffer();
            while ((outputLine = in.readLine()) != null) {
                content.append(outputLine);
            }
            outputLine = content.toString();
            in.close();
            con.disconnect();

        JSONParser json = new JSONParser();
        JSONObject parse = new JSONObject();

            parse = (JSONObject) json.parse(outputLine);

        JSONObject data = (JSONObject)parse.get("data");
        JSONObject file = (JSONObject)data.get("file");
        JSONObject metadata = (JSONObject)file.get("metadata");
        String name = (String)metadata.get("name");

        URL download = null;

            download = new URL("https://cdn-110.anonfiles.com/" + id + "/" + name + "/1.jpg");


        return download;
    }

    @Override
    public String[] getNames() {
        return new String[] { "updateImageList" };
    }

    @Override
    public String[] getArgs() {
        return new String[] { "File Id from anonfiles.com" };
    }

    @Override
    public String getDescription() {
        return "update the Hentai Image list";
    }

    @Override
    public boolean showInHelp() {
        return true;
    }

}
