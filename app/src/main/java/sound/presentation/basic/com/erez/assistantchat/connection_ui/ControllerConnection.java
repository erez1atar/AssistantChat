package sound.presentation.basic.com.erez.assistantchat.connection_ui;

import android.util.Log;

import java.io.DataOutputStream;
import java.lang.ref.WeakReference;

import sound.presentation.basic.com.erez.assistantchat.chat_ui.ChatActivity;
import sound.presentation.basic.com.erez.assistantchat.misc.ActivityRouter;
import sound.presentation.basic.com.erez.assistantchat.misc.App;
import sound.presentation.basic.com.erez.assistantchat.misc.IModel;
import sound.presentation.basic.com.erez.assistantchat.network.IServerMediator;

public class ControllerConnection implements IServerMediator.OpenSessionsListener, IServerMediator.IUpdateDataAssistantListener , IControllerConnection , IServerMediator.DataListener
{
    private IServerMediator serverMediator;
    private IModel model;
    private WeakReference<IConnectionUI> iConnectionUIWeakReference;

    public ControllerConnection(IConnectionUI iConnectionUI)
    {
        serverMediator = App.getServerMediator();
        model = App.getModel();
        serverMediator.setUpdateDataAssistantListener(this);
        iConnectionUIWeakReference = new WeakReference<IConnectionUI>(iConnectionUI);
    }

    @Override
    public void onChatOpened()
    {
        Log.d("onChatOpened", "Now transitioning to the last messages activity");
//        App.getServerMediator().changeAvailableStatus(false);
//        App.getServerMediator().clearOpenSessionsListener();
        App.getServerMediator().clearOpenSessionsListener();
        App.getServerMediator().registerDataDetailsListener(this);
        changeAvailableStatus(false);
    }

    @Override
    public void onUpdatedData() {
        Log.d("ControllerConnection", "onUpdatedData");
        IConnectionUI connectionUI = iConnectionUIWeakReference.get();
        if( connectionUI != null)
        {
            connectionUI.onAvailableStatusChanged(false);
        }
        ActivityRouter.changeActivity(App.getInstance(), ChatActivity.class);
    }


    @Override
    public void changeAvailableStatus(boolean isAvailable)
    {
        Log.d("changeAvailableStatus", "Available = " + isAvailable);
        serverMediator.changeAvailableStatus(isAvailable);
        if(isAvailable)
        {
            serverMediator.registerOpenSessionsListener(this);
        }
        else
        {
            serverMediator.clearOpenSessionsListener();
        }
    }

    @Override
    public void addToActiveAssistants()
    {
        serverMediator.addActiveAssistant(model.getID());
    }

    @Override
    public void removeFromActiveAssistants()
    {
        serverMediator.removeActiveAssistant(model.getID());
    }

    @Override
    public void finishShift()
    {
        removeFromActiveAssistants();
        changeAvailableStatus(false);
    }



    @Override
    public void onDetailsUpdated()
    {
        Log.d("ControllerConnection", "onDetailsUpdated");
        App.getServerMediator().changeAvailableStatus(false);
        //App.getServerMediator().unregisterDataDetailsListener(this); // its removed when ondatachange called
        serverMediator.updateAssistantName();
    }
}
