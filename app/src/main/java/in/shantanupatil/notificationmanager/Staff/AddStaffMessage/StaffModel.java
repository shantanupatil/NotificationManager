package in.shantanupatil.notificationmanager.Staff.AddStaffMessage;

/**
 * Created by Shantanu on 1/1/2018.
 */

public class StaffModel {

    private String title;
    private String description;
    private String  imageURI;
    private long date;
    private String staffName;


    public StaffModel(String title, String description, String imageURI, long date, String staffName) {
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

    public long getDate() {
        return date;
    }

    public String getStaffName() {
        return staffName;
    }
}
