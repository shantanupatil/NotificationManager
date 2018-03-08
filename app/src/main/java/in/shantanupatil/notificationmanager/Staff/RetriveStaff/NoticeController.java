package in.shantanupatil.notificationmanager.Staff.RetriveStaff;

/**
 * Created by Shantanu on 1/7/2018.
 */

public class NoticeController {

    Notice_Datapassing notice_datapassing;

    public NoticeController(Notice_Datapassing notice_datapassing) {
        this.notice_datapassing = notice_datapassing;
    }

    public void onItemClick(NoticeList noticeList) {
        notice_datapassing.startDetailActivity(noticeList.getTitle(), noticeList.getDescription(), noticeList.getImageURI());
    }
}
