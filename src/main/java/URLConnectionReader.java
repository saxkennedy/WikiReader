import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.*;
import java.io.*;


public class URLConnectionReader {

    public JsonObject getJSONfromURL(String title) throws Exception {
        URL wiki = new URL("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles=" + title + "&rvprop=timestamp|user&rvlimit=30&redirects");
        URLConnection connection = wiki.openConnection();
        connection.setRequestProperty("User-Agent","Revision Tracker/0.1 (http://www.cs.bsu.edu/~pvg/courses/cs222Fa20; cthomas3@bsu.edu)");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String json = in.readLine();
        in.close();

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        return jsonObject;

    }
}