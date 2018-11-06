package datacenter.crudreposity.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "news")
public class News  implements java.io.Serializable{
    private static final long serialVersionUID = 1000006L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    private String other_info;
    private String title;
    private String junior_channel;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOther_info() {
        return other_info;
    }

    public void setOther_info(String other_info) {
        this.other_info = other_info;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJunior_channel() {
        return junior_channel;
    }

    public void setJunior_channel(String junior_channel) {
        this.junior_channel = junior_channel;
    }

    public Date getNews_time() {
        return news_time;
    }

    public void setNews_time(Date news_time) {
        this.news_time = news_time;
    }

    private Date news_time;


}
