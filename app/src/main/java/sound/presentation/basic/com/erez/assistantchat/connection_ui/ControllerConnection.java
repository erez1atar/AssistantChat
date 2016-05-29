package sound.presentation.basic.com.erez.assistantchat.connection_ui;

import android.content.Intent;

import sound.presentation.basic.com.erez.assistantchat.chat_ui.ChatActivity;
import sound.presentation.basic.com.erez.assistantchat.chat_ui.ChatActivityLastMsg;
import sound.presentation.basic.com.erez.assistantchat.misc.App;
import sound.presentation.basic.com.erez.assistantchat.network.IServerMediator;

/**
 * Created by LENOVO on 22/05/2016.
 */
public class ControllerConnection implements IServerMediator.OpenSessionsListener
{
    @Override
    public void onChatOpened()
    {
        App.getServerMediator().changeAvailableStatus(false);
        Intent intent = new Intent(App.getInstance(), ChatActivityLastMsg.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getInstance().startActivity(intent);
    }
}
