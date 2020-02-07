import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;


public class RevisionMapFactoryTest {
    JsonParser json = new JsonParser();
    String testString = "{\"query\":{\"pages\":{\"37287\":{\"pageid\":37287,\"ns\":0,\"title\":\"Scooby-Doo\",\"revisions\":" +
            "[{\"user\":\"Starzoner\",\"timestamp\":\"2020-01-29T02:42:44Z\"}," +
            "{\"user\":\"SonicClub\",\"timestamp\":\"2020-01-02T22:18:44Z\"}]}}}}";
    JsonObject testJsonObject = (JsonObject)json.parse(testString);

    @Test
    public void getTimeSortedRevisionMapTest() throws Exception {
         RevisionMapFactory fac = new RevisionMapFactory(testJsonObject);
         Revision rev1 = new Revision("Starzoner","2020-01-29T02:42:44Z");
         Revision rev2 = new Revision("SonicClub","2020-01-02T22:18:44Z");

         LinkedHashMap<String,String> map = fac.getTimeSortedRevisionMap();
         LinkedHashMap<String,String> actualMap = new LinkedHashMap<>();

         actualMap.put(rev1.getParsedDate(),"Starzoner");
         actualMap.put(rev2.getParsedDate(),"SonicClub");
         Assertions.assertEquals(map,actualMap);
    }

    @Test
    public void getFrequencySortedRevisionMapTest() throws Exception {
        RevisionMapFactory fac = new RevisionMapFactory(testJsonObject);
        Revision rev1 = new Revision("Starzoner","2020-01-29T02:42:44Z");
        Revision rev2 = new Revision("SonicClub","2020-01-02T22:18:44Z");

        LinkedHashMap<String,String> map = fac.getFrequencySortedRevisionMap();
        LinkedHashMap<String,String> actualMap = new LinkedHashMap<>();

        actualMap.put("Starzoner","1");
        actualMap.put("SonicClub","1");
        Assertions.assertEquals(map,actualMap);
    }

}
