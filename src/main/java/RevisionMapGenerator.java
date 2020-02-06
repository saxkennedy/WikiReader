import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.LinkedHashMap;

public class RevisionMapGenerator {
    private JsonObject wikiPage;

    public LinkedHashMap<String, String> revisionMapGenerator(String term, String type) throws Exception {
        WikiMediaReader reader = new WikiMediaReader();
        wikiPage = reader.getJSONfromURL(term);
        RevisionListManager parser = new RevisionListManager(wikiPage);
        LinkedHashMap<String, String> revisionMap;
        if (type == "frequency") {
            revisionMap = parser.getFrequencySortedRevisionMap();
            return revisionMap;
        } else if (type == "time") {
            revisionMap = parser.getTimeSortedRevisionMap();
            return revisionMap;
        } else {
            revisionMap = parser.getFrequencySortedRevisionMap();
            return revisionMap;
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
            return redirectSubString = null;
        }
    }

}
