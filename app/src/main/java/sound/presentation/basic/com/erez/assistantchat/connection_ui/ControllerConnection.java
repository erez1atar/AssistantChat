package sound.presentation.basic.com.erez.assistantchat.connection_ui;

import sound.presentation.basic.com.erez.assistantchat.chat_ui.ChatActivity;
import sound.presentation.basic.com.erez.assistantchat.login_ui.LoginActivity;
import sound.presentation.basic.com.erez.assistantchat.misc.ActivityRouter;
import sound.presentation.basic.com.erez.assistantchat.misc.App;
import sound.presentation.basic.com.erez.assistantchat.misc.IModel;
import sound.presentation.basic.com.erez.assistantchat.network.IServerMediator;

/**
 * Created by LENOVO on 22/05/2016.
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
        App.getServerMediator().changeAvailableStatus(false);
        /*Intent intent = new Intent(App.getInstance(), ChatActivityLastMsg.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getInstance().startActivity(intent);*/
        ActivityRouter.changeActivity(App.getInstance(), ChatActivity.class);
    }

    @Override
    public void changeAvailableStatus(boolean isAvailable)
    {
        serverMediator.changeAvailableStatus(isAvailable);
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
        ActivityRouter.changeActivity(App.getInstance(), LoginActivity.class);
    }


}
