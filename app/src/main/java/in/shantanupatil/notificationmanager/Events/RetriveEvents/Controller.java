package in.shantanupatil.notificationmanager.Events.RetriveEvents;

import android.support.v4.app.FragmentActivity;

/**
 * Created by Shantanu on 12/24/2017.
 */

public class Controller {

    DataPassing dataPassing;

    public Controller(DataPassing dataPassing) {
        this.dataPassing = dataPassing;
    }

    public void OnListItemClicked(ListItem listItem) {
        dataPassing.startDetailActivity(listItem.getTitle(), listItem.getDescription(),
                listItem.getDate(), listItem.getForum(), listItem.getPrice());
    }
}
