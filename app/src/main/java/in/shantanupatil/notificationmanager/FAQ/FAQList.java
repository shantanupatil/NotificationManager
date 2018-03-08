package in.shantanupatil.notificationmanager.FAQ;

/**
 * Created by Shantanu on 1/31/2018.
 */

public class FAQList {
    private String title;
    private String description;

    public FAQList(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
