package sound.presentation.basic.com.erez.assistantchat.misc;

/**
 * Created by user on 31 מאי 2016.
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by user on 31 מאי 2016.
 */
public class TimerUtility {


    public static String getFormat(SimpleDateFormat df){
        df.setTimeZone(TimeZone.getDefault() );
        return df.format( Calendar.getInstance().getTime() ) ;
    }

    public static String currentTime() {
        return getFormat(new SimpleDateFormat("HH:mm"));
    }

    public static String currentFullDate(){
        return getFormat(new SimpleDateFormat("EEE, d MMM yyyy, HH:mm"));

    }
}
