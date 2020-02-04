import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import java.util.*;
import java.util.stream.Collectors;

public class RevisionListManager {
    private String pageID = null;
    private JsonObject IDLocation;

    public RevisionListManager(JsonObject jsonObject) {
        setID(jsonObject);
    }

    public void setID(JsonObject jsonObject) {
        IDLocation = jsonObject.getAsJsonObject("query").getAsJsonObject("pages");
        Set<String> keySet = IDLocation.keySet();
        for(String key:keySet) {
            pageID = key;
        }
    }

    private JsonArray getRevisions() {
        JsonObject IDField = IDLocation.getAsJsonObject(pageID);
        JsonArray revisions = IDField.getAsJsonArray("revisions");
        return revisions;
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
        LinkedHashMap<String,Integer> count = new LinkedHashMap<>();

        for(String user : UnsortedRevisionMap.values()) {
            if(count.containsKey(user)) {
                count.put(user,count.get(user) + 1);
            }
            else {
                count.put(user, (1));
            }
        }

        LinkedHashMap<String,Integer> result = count.entrySet()
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
}
