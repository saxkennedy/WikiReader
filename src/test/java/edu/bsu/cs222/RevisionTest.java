package edu.bsu.cs222;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RevisionTest {

    @Test
    public void constructRevisionTest() {
        Revision rev = new Revision("Colin","1111-11-11T11:11:11");
    }

    @Test
    public void parseDateTest() throws Exception {
        Revision rev = new Revision("Jeffrey","2020-02-06T08:25:43");
        String date = rev.getParsedDate();
        Assertions.assertEquals(date,"Thu Feb 06 03:25:43 EST 2020");
    }

    @Test
    public void wikiTestTest() {
        RevisionMapGenerator testGenerator = new RevisionMapGenerator();
        JsonObject page = testGenerator.getWikiPage("Obama");
    }
}
