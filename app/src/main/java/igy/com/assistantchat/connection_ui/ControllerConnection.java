package igy.com.assistantchat.connection_ui;

import android.util.Log;

import java.lang.ref.WeakReference;

import igy.com.assistantchat.chat_ui.ChatActivity;
import igy.com.assistantchat.misc.ActivityRouter;
import igy.com.assistantchat.misc.App;
import igy.com.assistantchat.misc.IModel;
import igy.com.assistantchat.network.IServerMediator;

public class ControllerConnection implements IServerMediator.OpenSessionsListener, IServerMediator.IUpdateDataAssistantListener , IControllerConnection, IServerMediator.DataListener {
    private IServerMediator serverMediator;
    private IModel model;
    private WeakReference<IConnectionUI> iConnectionUIWeakReference;

    public ControllerConnection(IConnectionUI iConnectionUI) {
        serverMediator = App.getServerMediator();
        model = App.getModel();
        serverMediator.setUpdateDataAssistantListener(this);
        iConnectionUIWeakReference = new WeakReference<IConnectionUI>(iConnectionUI);
    }

    @Override
    public void onChatOpened()
    {
        Log.d("onChatOpened", "Now transitioning to the last messages activity");
        IConnectionUI connectionUI = iConnectionUIWeakReference.get();
        if( connectionUI != null) {
            connectionUI.OnChatOpened();
        }
        App.getServerMediator().clearOpenSessionsListener();
        App.getServerMediator().registerDataDetailsListener(this);
        changeAvailableStatus(false);
    }

    @Override
    public void onUpdatedData() {
        Log.d("ControllerConnection", "onUpdatedData");
        IConnectionUI connectionUI = iConnectionUIWeakReference.get();
        if( connectionUI != null) {
            connectionUI.onAvailableStatusChanged(false);
        }
        ActivityRouter.changeActivity(App.getInstance(), ChatActivity.class);
    }


    @Override
    public void changeAvailableStatus(boolean isAvailable) {
        Log.d("changeAvailableStatus", "Available = " + isAvailable);
        serverMediator.changeAvailableStatus(isAvailable);
        if(isAvailable) {
            serverMediator.registerOpenSessionsListener(this);
        }
        else {
            serverMediator.clearOpenSessionsListener();
        }
    }

    @Override
    public void addToActiveAssistants() {
        serverMediator.addActiveAssistant(model.getID());
    }

    @Override
    public void removeFromActiveAssistants() {
        serverMediator.removeActiveAssistant(model.getID());
    }

    @Override
    public void finishShift() {
        removeFromActiveAssistants();
        changeAvailableStatus(false);
    }

    @Override
    public void onDetailsUpdated() {
        Log.d("ControllerConnection", "onDetailsUpdated");
        App.getServerMediator().changeAvailableStatus(false);
        serverMediator.updateAssistantName();
    }
}
