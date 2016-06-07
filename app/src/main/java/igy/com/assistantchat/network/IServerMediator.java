package igy.com.assistantchat.network;

import com.firebase.client.ValueEventListener;

import igy.com.assistantchat.message.IMessage;

/**
 * Interface for the various methods required to connect with the server.
 */
public interface IServerMediator
{
    interface OpenSessionsListener {
        void onChatOpened();
    }

    interface DataListener {
        void onDetailsUpdated();
    }

    interface ILoginAuthentication {
        void onLoginSuccess();

        void onLoginFailed();
    }


    interface IUpdateDataAssistantListener {
        void onUpdatedData();
    }

    void setUpdateDataAssistantListener(IUpdateDataAssistantListener updateDataAssistantListener);
    void changeAvailableStatus(boolean available);
    void endConversation();
    void sendMessage(IMessage message);
    void setListenerOnConnected(ValueEventListener listener);
    void executeListeningConnected();
    void unListeningConnected();
    void registerOpenSessionsListener(OpenSessionsListener listener);
    void clearOpenSessionsListener();
    void registerDataDetailsListener(DataListener dataListener);
    void addActiveAssistant(String assistantName);
    void removeActiveAssistant(String assistantName);
    void updateAssistantName();
    void login(ILoginAuthentication authentication);
    void updateUserData();
    String getAssistantName();
}
