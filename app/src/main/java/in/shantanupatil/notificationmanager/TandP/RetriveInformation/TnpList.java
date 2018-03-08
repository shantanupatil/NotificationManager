package in.shantanupatil.notificationmanager.TandP.RetriveInformation;

/**
 * Created by Shantanu on 12/30/2017.
 */

public class TnpList {

    private String title;
    private String description;
    private String image;
    private String time;

    public TnpList(String title, String description, String image, String time) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getTime() {
        return time;
    }
}
