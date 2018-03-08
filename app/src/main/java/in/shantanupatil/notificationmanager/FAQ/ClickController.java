package in.shantanupatil.notificationmanager.FAQ;

import java.util.List;

/**
 * Created by Shantanu on 2/1/2018.
 */

public class ClickController {
    FAQInterface faqInterface;

    public ClickController(FAQInterface faqInterface) {
        this.faqInterface = faqInterface;
    }

    public void onItemClick(FAQList faqLists) {
        faqInterface.startDetailActivity(faqLists.getTitle(), faqLists.getDescription());
    }
}
