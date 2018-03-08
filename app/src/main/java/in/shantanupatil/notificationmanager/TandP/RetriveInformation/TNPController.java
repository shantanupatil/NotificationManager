package in.shantanupatil.notificationmanager.TandP.RetriveInformation;

/**
 * Created by Shantanu on 12/31/2017.
 */

public class TNPController {

    TNP_Data dataPassing;

    public TNPController(TNP_Data dataPassing) {
        this.dataPassing = dataPassing;
    }

    public void onItemClicked(TnpList list) {
        dataPassing.startTnpDetail(list.getTitle(), list.getDescription(), list.getImage());
    }
}
