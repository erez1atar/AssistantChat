package sound.presentation.basic.com.erez.assistantchat.login_controller;

import android.util.Log;

import java.lang.ref.WeakReference;

import sound.presentation.basic.com.erez.assistantchat.connection_ui.ConnectionActivity;
import sound.presentation.basic.com.erez.assistantchat.login_ui.ILoginUI;
import sound.presentation.basic.com.erez.assistantchat.misc.ActivityRouter;
import sound.presentation.basic.com.erez.assistantchat.misc.App;
import sound.presentation.basic.com.erez.assistantchat.misc.IModel;
import sound.presentation.basic.com.erez.assistantchat.network.IServerMediator;

/**
 * Created by Tom Vizel on 29/05/2016.
 */
public class MyLoginController
{
    private WeakReference<ILoginUI> uiRef;

    public MyLoginController(ILoginUI ui)
    {
        uiRef = new WeakReference<>(ui);
    }

    public void login()
    {
        ILoginUI ui = uiRef.get();
        if (ui != null)
        {
            IModel model = App.getModel();
            String email = ui.getEmail();
            String password = ui.getPassword();
            if (email != null && password != null)
            {
                model.setEmail(email);
                model.setPassword(password);
                App.getServerMediator().login(new IServerMediator.ILoginAuthentication()
                {
                    @Override
                    public void onLoginSuccess()
                    {
                        ActivityRouter.changeActivity(App.getInstance(), ConnectionActivity.class);
                    }

                    @Override
                    public void onLoginFailed()
                    {
                        Log.d("login", "Login failed!");
                    }
                });
            }
            else
            {
                ui.invalidEmailOrPassword();
            }
        }
    }
}
