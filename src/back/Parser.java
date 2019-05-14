package back;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

public class Parser {

    public static final String DATA_URL = "http://data.fixer.io/api/latest?access_key=1fff7dee1ec818894832122c2c441c72";

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject getJsonFromUrl(String url) throws IOException {

        try (InputStream is = new URL(url).openStream()) {
            JSONObject json;
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONParser parser = new JSONParser();
            json = (JSONObject) parser.parse(jsonText);
            return json;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Double> getRates(JSONObject json) {
        if (json == null){
            return null;
        } else if (! ((Boolean) json.get("success"))) {
            return null;
        }
        return (Map<String, Double>) json.get("rates");
    }
}
