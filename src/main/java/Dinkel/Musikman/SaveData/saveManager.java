package Dinkel.Musikman.SaveData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class saveManager {

	public static void main(String[] args) {
		new saveManager();
	}

	public saveManager() {
		try {
			test2();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeAudioTracks(List<AudioTrack> tracks) throws IOException {
		FileOutputStream fos = new FileOutputStream("C:\\Users\\Lars Bleimund\\Desktop\\Test\\AudioTracks.txt");
		ObjectOutputStream output = new ObjectOutputStream(fos);
		output.writeObject(tracks);
		output.close();
	}
	
	public static void saveJSON(JSONObject obj, File location) {
		FileWriter file = null;
		
		try {
            file = new FileWriter(location);
            file.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	public static JSONObject loadJSON(File location) {
		JSONParser parser = new JSONParser();
		try {
			return (JSONObject) parser.parse(new FileReader(location));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void test2() throws IOException, ClassNotFoundException {
		List<Integer> inte = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			inte.add(new Integer(i));
		}

		JSONObject obj = new JSONObject();
		JSONArray Data = new JSONArray();
		for (int i = 0; i < inte.size(); i++) {

		}

		FileOutputStream fos = new FileOutputStream("C:\\Users\\Lars Bleimund\\Desktop\\Test\\Integers.json");
		ObjectOutputStream output = new ObjectOutputStream(fos);
		output.writeObject(inte);
		output.close();

		FileInputStream fis = new FileInputStream("C:\\Users\\Lars Bleimund\\Desktop\\Test\\Integers.json");
		ObjectInputStream input = new ObjectInputStream(fis);
		List<Integer> integers = (List<Integer>) input.readObject();
		for (int i = 0; i < inte.size(); i++) {
			System.out.println(integers.get(i).intValue());
		}
	}
}
