package in.shantanupatil.notificationmanager.IntroSlider;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Shantanu on 2/4/2018.
 */

public class IntroductionSlider {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public IntroductionSlider(Context context) {
        this.context = context;
        pref = context.getSharedPreferences("first", 0);
        editor = pref.edit();
    }

    public void setFirstLaunch(boolean isFirst) {
        editor.putBoolean("check", isFirst);
        editor.commit();
    }

    public boolean checkLauch(){
        return pref.getBoolean("check", true);
    }
}
