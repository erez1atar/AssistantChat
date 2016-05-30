package sound.presentation.basic.com.erez.assistantchat.network;

import com.firebase.client.ValueEventListener;

import sound.presentation.basic.com.erez.assistantchat.message.IMessage;

/**
 * Interface for the various methods required to connect with the server.
 */
public interface IServerMediator
{
    interface OpenSessionsListener
    {
        void onChatOpened();
    }

    interface ILoginAuthentication
    {
        void onLoginSuccess();

        void onLoginFailed();
    }

    void changeAvailableStatus(boolean available);
    void endConversation();
    void sendMessage(IMessage message);
    void setListener(ValueEventListener listener);
    void executeListeningConnected();

    void registerOpenSessionsListener(OpenSessionsListener listener);
    void clearOpenSessionsListener();
    void addActiveAssistant(String assistantName);
    void removeActiveAssistant(String assistantName);

    void login(ILoginAuthentication authentication);
}
