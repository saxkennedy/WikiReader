import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Revision {
    private String user;
    private String timestamp;

    public Revision(String user, String timestamp) {
        this.user = user;
        this.timestamp = timestamp;
    }

    public String getUser() {
        return this.user;
    }


    public String getParsedDate() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.getCalendar().setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = format.parse(this.timestamp);
        return date.toString();
    }
}