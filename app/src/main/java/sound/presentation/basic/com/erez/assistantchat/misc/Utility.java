package sound.presentation.basic.com.erez.assistantchat.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Misc. utility methods.
 */
public final class Utility
{
    private static String ip;
    private static Thread ipFindThread;

    public static void findUserIP()
    {
        //final String[] ip = {null};

        ipFindThread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    URL ipAdress = new URL("http://myexternalip.com/raw");
                    BufferedReader in = new BufferedReader(new InputStreamReader(ipAdress.openStream()));
                    ip = in.readLine();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

        ipFindThread.start();
//        try
//        {
//            ipFindThread.join();
//        }
//        catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }
    }

    public static String getUserIP() {
        if (ip == null) {
            findUserIP();
            try
            {
                ipFindThread.join();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

        }
        return ip;
    }

    public static void resetIP(){
        ip = null;
    }
}
