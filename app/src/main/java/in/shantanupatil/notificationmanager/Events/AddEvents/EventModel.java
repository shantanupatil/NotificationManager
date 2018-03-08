package in.shantanupatil.notificationmanager.Events.AddEvents;

/**
 * Created by Shantanu on 12/23/2017.
 */

public class EventModel {

    private String description;
    private Long dates;
    private String title;
    private String price;
    private String Forum;

    public EventModel(){

    }

    public EventModel(String title, String description, Long dates, String price, String Forum) {
        this.title = title;
        this.description = description;
        this.dates = dates;
        this.Forum = Forum;
        this.price = price;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getDate() {
        return dates;
    }

    public String getPrice() {
        return price;
    }

    public String getForum() {
        return Forum;
    }
}
