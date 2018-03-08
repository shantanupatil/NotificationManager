package in.shantanupatil.notificationmanager.Events.RetriveEvents;

import android.content.Intent;

/**
 * Created by Shantanu on 12/24/2017.
 */

public class ListItem {

    private String title;
    private String description;
    private String time;
    private String forum;
    private String price;

    public ListItem(String title, String description, String time, String forum, String price) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.forum = forum;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return time;
    }

    public String getForum() {
        return forum;
    }

    public String getPrice() {
        return price;
    }
}
