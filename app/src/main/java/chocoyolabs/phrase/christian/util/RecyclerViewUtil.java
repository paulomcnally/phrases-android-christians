package chocoyolabs.phrase.christian.util;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;

public class RecyclerViewUtil {

    public static int getContentHeight(Activity activity, Toolbar toolbar) {

        // status bar height
        int statusBarHeight = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }

        // screen height
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;

        // toolbar height
        int toolbarHeight = toolbar.getLayoutParams().height;

        // content height
        return screenHeight - toolbarHeight - statusBarHeight;
    }
}