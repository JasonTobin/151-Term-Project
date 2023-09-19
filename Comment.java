import java.time.Instant;
import java.util.Date;

public class Comment {
    private String commentDesc; // required
    private Date comDate;

    public void setDesc(String n) {
        this.commentDesc = n;
        return;
    }

    public String getDesc() {
        return this.commentDesc;
    }

    public void setComDate() {
        this.comDate = Date.from(Instant.now()); // Dates are not editable for comments
        return;
    }

    public Date getComDate() {
        return this.comDate;
    }

    public Comment(String d) {
        this.commentDesc = d; // required
        this.setComDate(); // set by default and unchangable
    }
}
