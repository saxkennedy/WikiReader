import com.google.gson.*;
import com.google.gson.internal.GsonBuildConfig;

import java.lang.reflect.Array;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception{
        URLConnectionReader reader = new URLConnectionReader();

        JsonObject jsonObject = reader.getJSONfromURL("pear");
        JsonObject query = jsonObject.getAsJsonObject("query");
        JsonObject pages = query.getAsJsonObject("pages");
        Set<String> keySet = pages.keySet();
        String pageID = null;
        for(String key:keySet) {
            pageID = key;
        }

        JsonObject ID = pages.getAsJsonObject(pageID);
        JsonArray revisions = ID.getAsJsonArray("revisions");


        Gson gson = new Gson();
        Revision rev = gson.fromJson(revisions.get(0),Revision.class);
        System.out.println(rev.user);
        System.out.println(rev.timestamp);


    }
}
