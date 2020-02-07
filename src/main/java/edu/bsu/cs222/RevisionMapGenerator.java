package edu.bsu.cs222;

import com.google.gson.JsonObject;
import java.util.LinkedHashMap;

public class RevisionMapGenerator {
    private JsonObject wikiPage;


    public LinkedHashMap<String, String> revisionMapGenerator(String term, String type) {

        wikiPage = getWikiPage(term);
        RevisionMapFactory parser = new RevisionMapFactory(wikiPage);
        LinkedHashMap<String, String> revisionMap;
        if (type.equals("frequency")) {
            revisionMap = parser.getFrequencySortedRevisionMap();
            return revisionMap;
        } else if (type.equals("time")) {
            revisionMap = parser.getTimeSortedRevisionMap();
            return revisionMap;
        } else {
            return null;
        }
    }

    public String getRedirect() {
        JsonObject query = wikiPage.getAsJsonObject("query");
        String redirectSubString;
        try {
            String redirectSuperString = query.get("redirects").toString();
            redirectSubString = redirectSuperString.substring(redirectSuperString.indexOf(",\"to\":\"") + 7, redirectSuperString.indexOf("\"}]"));
            return redirectSubString;
        } catch (Exception e) {
            return null;
        }
    }
    public JsonObject getWikiPage(String term) {
        WikiMediaReader reader = new WikiMediaReader();
        try {
            wikiPage = reader.getJSONfromURL(term);
        } catch(Exception e) {
            return null;
        }
        return wikiPage;
    }
}
