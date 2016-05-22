package sound.presentation.basic.com.erez.assistantchat.misc;

import android.app.Application;

import sound.presentation.basic.com.erez.assistantchat.network.FirebaseMediator;
import sound.presentation.basic.com.erez.assistantchat.network.IServerMediator;

/**
 * Created by LENOVO on 22/05/2016.
 */
public class App extends Application
{
    private static IModel iModel;
    private static IServerMediator serverMediator;

    public static IModel getiModel()
    {
        if(iModel == null)
        {
            iModel = new Model();
        }
        return iModel;
    }

    public static IServerMediator getServerMediator()
    {
        if(serverMediator == null)
        {
            serverMediator = new FirebaseMediator();
        }
        return serverMediator;
    }
}
