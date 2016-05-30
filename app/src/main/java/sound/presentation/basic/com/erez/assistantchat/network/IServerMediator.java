package sound.presentation.basic.com.erez.assistantchat.network;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

import sound.presentation.basic.com.erez.assistantchat.message.IMessage;

/**
 * Interface for the various methods required to connect with the server.
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

    interface OpenSessionsListener
    {
        void onChatOpened();
    }

    interface ILoginAuthentication
    {
        void onLoginSuccess();

        void onLoginFailed();
    }

    void login(ILoginAuthentication authentication);
}
