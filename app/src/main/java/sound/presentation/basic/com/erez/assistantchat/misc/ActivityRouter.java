package sound.presentation.basic.com.erez.assistantchat.misc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Class for allowing a simple change from one activity to the next.
 */
public class ActivityRouter {

    public static void changeActivity(Context context, Class<? extends Activity> activity)
    {
        Intent intent = new Intent(context, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
