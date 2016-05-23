package sound.presentation.basic.com.erez.assistantchat.network;

/**
 * Created by LENOVO on 22/05/2016.
 */
public interface IServerMediator
{
    void changeAvailableStatus(boolean available);

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
