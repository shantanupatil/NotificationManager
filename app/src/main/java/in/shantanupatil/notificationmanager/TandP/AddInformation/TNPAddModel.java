package in.shantanupatil.notificationmanager.TandP.AddInformation;

import android.net.Uri;

/**
 * Created by Shantanu on 12/30/2017.
 */

public class TNPAddModel {

    private String title;
    private String description;
    private String imageURI;
    private long date;

    public TNPAddModel(String title, String description, String imageURI, long date) {
        this.title = title;
        this.description = description;
        this.imageURI = imageURI;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURI() {
        return imageURI;
    }

    public long getDate(){
        return date;
    }
}
