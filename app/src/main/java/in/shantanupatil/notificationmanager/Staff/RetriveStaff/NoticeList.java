package in.shantanupatil.notificationmanager.Staff.RetriveStaff;

/**
 * Created by Shantanu on 1/7/2018.
 */

public class NoticeList {

    private String title;
    private String description;
    private String imageURI;
    private String date;
    private String staffName;

    public NoticeList(String title, String description, String imageURI, String date, String staffName) {
        this.title = title;
        this.description = description;
        this.imageURI = imageURI;
        this.date = date;
        this.staffName = staffName;
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

    public String getDate() {
        return date;
    }

    public String getStaffName() {
        return staffName;
    }
}
