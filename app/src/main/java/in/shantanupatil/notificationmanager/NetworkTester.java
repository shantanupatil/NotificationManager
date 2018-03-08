package in.shantanupatil.notificationmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Shantanu on 2/3/2018.
 */

public class NetworkTester {

    Context context;

    public NetworkTester(Context context) {
        this.context = context;
    }

    public boolean isOnline(){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}
