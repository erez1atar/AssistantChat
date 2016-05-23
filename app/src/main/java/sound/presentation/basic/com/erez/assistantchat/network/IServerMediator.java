package sound.presentation.basic.com.erez.assistantchat.network;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

import sound.presentation.basic.com.erez.assistantchat.message.IMessage;

/**
 * Created by LENOVO on 22/05/2016.
 */
public interface IServerMediator
{
    void changeAvailableStatus(boolean available);
    Firebase getLastMessagesDB();
    void endConversation();
    void sendMessage(IMessage message);
    Firebase getMessagesDB();
    void setListener(ValueEventListener listener);
    void executeListeningConnected();

    void registerOpenSessionsListener(OpenSessionsListener listener);
    void clearOpenSessionsListener();
    void addActiveAssistant(String assistantName);
    void removeActiveAssistant(String assistantName);

    public interface OpenSessionsListener
    {
        void onChatOpened();
    }

    void login();
}
