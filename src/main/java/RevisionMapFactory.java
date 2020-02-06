import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import sun.awt.image.ImageWatched;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RevisionMapFactory {
    private String pageID = null;
    private JsonObject IDLocation;
    private JsonObject query;
    public RevisionMapFactory(JsonObject jsonObject) {
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
        LinkedHashMap<String,String> reverseRevisionMap = reverseUnsortedRevisionMap(UnsortedRevisionMap);
        LinkedHashMap<String, Integer> revisionCounterMap =  loadRevisionCounterMap(reverseRevisionMap);

        LinkedHashMap<String,Integer> result = revisionCounterMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue,newValue) -> oldValue, LinkedHashMap::new));

        LinkedHashMap<String,String> FrequencySortedRevisionMap = reverseSortedRevisionMap(result);

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

    private LinkedHashMap<String,Integer> loadRevisionCounterMap(LinkedHashMap<String,String> map) {
        LinkedHashMap<String,Integer> revisionCounterMap = new LinkedHashMap<>();
        for(String user : map.values()) {
            if(revisionCounterMap.containsKey(user)) {
                revisionCounterMap.put(user,revisionCounterMap.get(user) + 1);
            }
            else {
                revisionCounterMap.put(user, (1));
            }
        }
        return revisionCounterMap;
    }

    private LinkedHashMap<String,String> reverseUnsortedRevisionMap(LinkedHashMap<String,String> map) {
        LinkedList<String> reverseList = new LinkedList<>(map.keySet());
        LinkedHashMap<String,String> reverseRevisionMap = new LinkedHashMap<>();
        Collections.reverse(reverseList);
        for(String key:reverseList) {
            reverseRevisionMap.put(key,map.get(key));
        }
        return reverseRevisionMap;
    }

    private LinkedHashMap<String,String> reverseSortedRevisionMap(LinkedHashMap<String,Integer> map) {
        LinkedList<String> entryList = new LinkedList<>();
        LinkedHashMap<String,String> frequencySortedRevisionMap = new LinkedHashMap<>();
        for(Map.Entry<String, Integer> entry : map.entrySet()) {
            entryList.add(entry.getKey());
        }
        for(int i = entryList.size()-1; i >= 0; i--) {
            frequencySortedRevisionMap.put(entryList.get(i), (map.get(entryList.get(i)).toString()));
        }
        return frequencySortedRevisionMap;
    }
}