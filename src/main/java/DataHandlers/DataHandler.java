package DataHandlers;

import net.dv8tion.jda.core.entities.Guild;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Verwerkt de gegevens van een Guild en zet dit om naar een Java-verwerkbaar formaat.
 */
@SuppressWarnings("unchecked")
public class DataHandler {

    JSONObject jsonObject;
    protected String guild;
    protected Guild g;
    protected JSONObject guildObject;

    DataHandler(Guild g) {
        JSONParser parser = new JSONParser();
        guild = g.getId();
        this.g = g;
        try (FileReader reader = new FileReader("src/main/resources/Data.json")) {
            jsonObject = ((JSONObject) parser.parse(reader));
            if (!jsonObject.containsKey(g.getId())) {
                JSONArray food = new JSONArray();
                JSONArray dates = new JSONArray();
                JSONArray characters = new JSONArray();
                JSONObject config = new JSONObject();
                JSONObject messages = new JSONObject();
                JSONObject data = new JSONObject();
                data.put("Food", food);
                data.put("Dates", dates);
                data.put("Characters", characters);
                data.put("Config", config);
                data.put("Messages", messages);
                jsonObject.put(g.getId(), data);
                save();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        guildObject = (JSONObject) jsonObject.get(guild);
    }

    void save() {
        try (FileWriter file = new FileWriter("src/main/resources/Data.json")) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
