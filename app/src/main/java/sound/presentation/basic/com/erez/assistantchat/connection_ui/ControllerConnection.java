package sound.presentation.basic.com.erez.assistantchat.connection_ui;

import android.util.Log;

import sound.presentation.basic.com.erez.assistantchat.chat_ui.ChatActivity;
import sound.presentation.basic.com.erez.assistantchat.chat_ui.ChatActivityLastMsg;
import sound.presentation.basic.com.erez.assistantchat.misc.ActivityRouter;
import sound.presentation.basic.com.erez.assistantchat.misc.App;
import sound.presentation.basic.com.erez.assistantchat.misc.IModel;
import sound.presentation.basic.com.erez.assistantchat.network.IServerMediator;

/**
 * Controller for handling background connection from server before transitioning to the chat activities.
 */
public class ControllerConnection implements IServerMediator.OpenSessionsListener, IControllerConnection
{
    private IServerMediator serverMediator;
    private IModel model;

    public ControllerConnection()
    {
        serverMediator = App.getServerMediator();
        model = App.getModel();
    }
    @Override
    public void onChatOpened()
    {
        Log.d("onChatOpened", "Now transitioning to the last messages activity");
//        App.getServerMediator().changeAvailableStatus(false);
//        App.getServerMediator().clearOpenSessionsListener();
        changeAvailableStatus(false);
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


}
