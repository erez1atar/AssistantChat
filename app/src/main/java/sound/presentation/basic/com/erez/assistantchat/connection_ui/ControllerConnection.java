package sound.presentation.basic.com.erez.assistantchat.connection_ui;

import android.util.Log;

import sound.presentation.basic.com.erez.assistantchat.chat_ui.ChatActivity;
import sound.presentation.basic.com.erez.assistantchat.misc.ActivityRouter;
import sound.presentation.basic.com.erez.assistantchat.misc.App;
import sound.presentation.basic.com.erez.assistantchat.misc.IModel;
import sound.presentation.basic.com.erez.assistantchat.network.IServerMediator;

/**
 * Controller for handling background connection from server before transitioning to the chat activities.
 */
public class ControllerConnection implements IServerMediator.OpenSessionsListener, IServerMediator.IUpdateDataAssistantListener , IControllerConnection
{
    private IServerMediator serverMediator;
    private IModel model;

    public ControllerConnection()
    {
        serverMediator = App.getServerMediator();
        model = App.getModel();
        serverMediator.setUpdateDataAssistantListener(this);
    }

    @Override
    public void onChatOpened()
    {
        Log.d("onChatOpened", "Now transitioning to the last messages activity");
//        App.getServerMediator().changeAvailableStatus(false);
//        App.getServerMediator().clearOpenSessionsListener();
        updateUserData();
        changeAvailableStatus(false);

        serverMediator.updateAssistantName();
    }

    @Override
    public void onUpdatedData() {
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
    public void updateUserData()
    {
        App.getServerMediator().updateUserData();
    }


}
