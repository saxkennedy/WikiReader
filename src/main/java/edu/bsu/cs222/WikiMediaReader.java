package edu.bsu.cs222;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.*;
import java.io.*;


public class WikiMediaReader {

    private URLConnection connection;
    private URL wiki;

    public JsonObject getJSONfromURL(String title) throws Exception {
        title = title.replace(" ","_");
        wiki = new URL("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles=" + title + "&rvprop=timestamp|user&rvlimit=30&redirects");
        connection = wiki.openConnection();
        connection.setRequestProperty("User-Agent","edu.bsu.cs222.Revision Tracker/0.1 (http://www.cs.bsu.edu/~pvg/courses/cs222Fa20; cthomas3@bsu.edu)");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String json = in.readLine();
        in.close();

        return new JsonParser().parse(json).getAsJsonObject();

    }

}