package sound.presentation.basic.com.erez.assistantchat.connection_ui;

/**
 * Created by LENOVO on 29/05/2016.
 */
public interface IControllerConnection
{
    void changeAvailableStatus(boolean isAvailable);

    void addToActiveAssistants();

    void removeFromActiveAssistants();

    void finishShift();
}
