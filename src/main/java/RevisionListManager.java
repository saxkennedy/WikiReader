import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import sun.awt.image.ImageWatched;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RevisionListManager {
    private String pageID = null;
    private JsonObject IDLocation;
    private JsonObject query;
    public RevisionListManager(JsonObject jsonObject) {
        setID(jsonObject);
    }

    public LinkedHashMap<String,String> getTimeSortedRevisionMap() {
        LinkedHashMap<String,String> revisionMap = new LinkedHashMap<>();
        Gson gson = new Gson();

        JsonArray revisions = getRevisions();
        for(JsonElement revision:revisions) {
            Revision rev = gson.fromJson(revision,Revision.class);
            try {
                revisionMap.put(rev.getParsedDate(),rev.getUser());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       return revisionMap;
    }



    public LinkedHashMap<String, String> getFrequencySortedRevisionMap() {
        LinkedHashMap<String, String> UnsortedRevisionMap = getTimeSortedRevisionMap();
        LinkedHashMap<String, Integer> RevisionCounterMap = new LinkedHashMap<>();

        LinkedHashMap<String, String> ReverseRevisionMap = new LinkedHashMap<>();
        LinkedList<String> reverseList = new LinkedList<>(UnsortedRevisionMap.keySet());
        Collections.reverse(reverseList);
        for(String key:reverseList) {
            ReverseRevisionMap.put(key,UnsortedRevisionMap.get(key));
        }

        for(String user : ReverseRevisionMap.values()) {
            if(RevisionCounterMap.containsKey(user)) {
                RevisionCounterMap.put(user,RevisionCounterMap.get(user) + 1);
            }
            else {
                RevisionCounterMap.put(user, (1));
            }
        }

        LinkedHashMap<String,Integer> result = RevisionCounterMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue,newValue) -> oldValue, LinkedHashMap::new));

        LinkedHashMap<String,String> FrequencySortedRevisionMap = new LinkedHashMap<>();
        LinkedList<String> entryList = new LinkedList<>();

        for(Map.Entry<String, Integer> entry : result.entrySet()) {
            entryList.add(entry.getKey());
        }
        for(int i = entryList.size()-1; i >= 0; i--) {
            FrequencySortedRevisionMap.put(entryList.get(i), (result.get(entryList.get(i)).toString()));
        }

        return FrequencySortedRevisionMap;
    }

    private JsonArray getRevisions() {
        JsonObject IDField = IDLocation.getAsJsonObject(pageID);
        JsonArray revisions = IDField.getAsJsonArray("revisions");
        return revisions;
    }

    private void setID(JsonObject jsonObject) {
        query = jsonObject.getAsJsonObject("query");
        IDLocation=query.getAsJsonObject("pages");
        Set<String> keySet = IDLocation.keySet();
        for(String key:keySet) {
            pageID = key;
        }
    }


}
