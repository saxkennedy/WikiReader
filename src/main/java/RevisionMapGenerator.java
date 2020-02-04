import com.google.gson.JsonObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class RevisionMapGenerator {
    public LinkedHashMap<String, String> revisionMapGenerator(String term, String type) throws Exception {
        WikiMediaReader reader = new WikiMediaReader();
        JsonObject wikiPage = reader.getJSONfromURL(term);
        RevisionListManager parser = new RevisionListManager(wikiPage);
        LinkedHashMap<String, String> revisionMap;
        if (type == "frequency") {
            revisionMap = parser.getFrequencySortedRevisionMap();
            return revisionMap;
        } else{
            revisionMap = parser.getTimeSortedRevisionMap();
            return revisionMap;
        }


    }
}
