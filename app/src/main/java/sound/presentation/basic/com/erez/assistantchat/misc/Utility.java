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
    public static String getUserIP()
    {
        final String[] ip = {null};

        Thread ipFind = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    URL ipAdress = new URL("http://myexternalip.com/raw");
                    BufferedReader in = new BufferedReader(new InputStreamReader(ipAdress.openStream()));
                    ip[0] = in.readLine();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

        ipFind.start();
        try
        {
            ipFind.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return ip[0];
    }
}
