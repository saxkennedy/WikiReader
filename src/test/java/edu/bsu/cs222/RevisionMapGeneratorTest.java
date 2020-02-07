package edu.bsu.cs222;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class RevisionMapGeneratorTest {
    private JsonObject testJson= new JsonParser().parse("{\"continue\":{\"rvcontinue\":\"20200116214203|936131193\",\"continue\":\"||\"},\"query\":{\"normalized\":[{\"from\":\"obama\",\"to\":\"Obama\"}],\"redirects\":[{\"from\":\"Obama\",\"to\":\"Barack Obama\"}],\"pages\":{\"534366\":{\"pageid\":534366,\"ns\":0,\"title\":\"Barack Obama\",\"revisions\":[{\"user\":\"Eyer\",\"timestamp\":\"2020-02-05T17:06:19Z\"},{\"user\":\"Postcard Cathy\",\"timestamp\":\"2020-02-05T16:19:33Z\"},{\"user\":\"ClueBot NG\",\"timestamp\":\"2020-02-03T14:03:34Z\"},{\"user\":\"KingNTJ\",\"timestamp\":\"2020-02-03T14:03:31Z\"},{\"user\":\"DrKay\",\"timestamp\":\"2020-01-31T21:13:23Z\"},{\"user\":\"Laiggg\",\"timestamp\":\"2020-01-31T06:24:51Z\"},{\"user\":\"Laiggg\",\"timestamp\":\"2020-01-31T06:23:21Z\"},{\"user\":\"Midnightblueowl\",\"timestamp\":\"2020-01-29T17:14:52Z\"},{\"user\":\"Georgefourmen77\",\"timestamp\":\"2020-01-29T17:03:07Z\"},{\"user\":\"Muboshgu\",\"timestamp\":\"2020-01-29T16:30:28Z\"},{\"user\":\"GlassBones\",\"timestamp\":\"2020-01-29T16:22:57Z\"},{\"user\":\"SibTower1987\",\"timestamp\":\"2020-01-28T03:09:25Z\"},{\"user\":\"Wrath X\",\"timestamp\":\"2020-01-26T06:30:15Z\"},{\"user\":\"BD2412\",\"timestamp\":\"2020-01-26T01:11:16Z\"},{\"user\":\"Paleontologist99\",\"timestamp\":\"2020-01-24T18:43:53Z\"},{\"user\":\"Paleontologist99\",\"timestamp\":\"2020-01-24T18:42:44Z\"},{\"user\":\"Paleontologist99\",\"timestamp\":\"2020-01-24T18:41:14Z\"},{\"user\":\"Kind Tennis Fan\",\"timestamp\":\"2020-01-23T11:18:09Z\"},{\"user\":\"DrKay\",\"timestamp\":\"2020-01-22T21:01:49Z\"},{\"user\":\"MelanieN\",\"timestamp\":\"2020-01-21T18:22:26Z\"},{\"user\":\"RedHotPear\",\"timestamp\":\"2020-01-18T03:39:41Z\"},{\"user\":\"RedHotPear\",\"timestamp\":\"2020-01-18T03:33:57Z\"},{\"user\":\"RedHotPear\",\"timestamp\":\"2020-01-18T03:29:57Z\"},{\"user\":\"RedHotPear\",\"timestamp\":\"2020-01-18T03:24:51Z\"},{\"user\":\"XXeducationexpertXX\",\"timestamp\":\"2020-01-17T18:43:45Z\"},{\"user\":\"Mediaexpert3\",\"timestamp\":\"2020-01-17T08:59:13Z\"},{\"user\":\"SunCrow\",\"timestamp\":\"2020-01-16T22:09:58Z\"},{\"user\":\"SunCrow\",\"timestamp\":\"2020-01-16T22:05:39Z\"},{\"user\":\"SunCrow\",\"timestamp\":\"2020-01-16T22:00:24Z\"},{\"user\":\"SunCrow\",\"timestamp\":\"2020-01-16T21:57:37Z\"}]}}}}\n")
            .getAsJsonObject();

    @Test
    public void getWikiPageTest(){
        System.out.println(testJson);
        RevisionMapGenerator testGenerator=new RevisionMapGenerator();
        JsonObject actualObject=testGenerator.getWikiPage("obama");
        Assertions.assertEquals(testJson,actualObject);
    }
    @ParameterizedTest
    @CsvSource({"obama,Barack Obama,time","barack,Barack Obama,time","president obama,Barack Obama,time","Barack Obama,\0,time",
            "obama,Barack Obama,frequency","barack,Barack Obama,frequency","president obama,Barack Obama,frequency","Barack Obama,\0,frequency"})
    public void getRedirectTest(String term, String expected, String type){
        RevisionMapGenerator testGenerator=new RevisionMapGenerator();
        testGenerator.revisionMapGenerator(term,type);
        String actual=testGenerator.getRedirect();
        Assertions.assertEquals(actual,expected);
    }
}