package monkey.rising.tomatogo.config;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import monkey.rising.tomatogo.R;

/**
 * Created by Administrator on 2016/7/18.
 */
public class Utils {
   // static int textSizeStatus=2;
    //static int progressValue=25;
    public static SharedPreferences configSP;
    public static SharedPreferences.Editor editor;
    public static void changeTheme(Activity activity){
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }
    public static void onActivityCreateSetTheme(Activity activity,int theme)
    {
        switch (theme)
        {
            default:
            case 1:
                activity.setTheme(R.style.MiniSize);
                break;
            case 2:
                activity.setTheme(R.style.SmallSize);
                break;
            case 3:
                activity.setTheme(R.style.MiddleSize);
                break;
            case 4:
                activity.setTheme(R.style.LargeSize);
                break;
            case 5:
                activity.setTheme(R.style.HugeSize);
                break;
        }
    }

    public static void restartApp(Activity activity){
        final Intent intent = activity.getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(activity.getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

}
