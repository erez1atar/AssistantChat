package sound.presentation.basic.com.erez.assistantchat.chat_controller;


import sound.presentation.basic.com.erez.assistantchat.message.ChatMessage;
import sound.presentation.basic.com.erez.assistantchat.message.IMessage;
import sound.presentation.basic.com.erez.assistantchat.network.IServerMediator;

/**
 * Created by user on 04/05/2016.
 */
public interface IChatController
{
//    List<IMessage> getMessageFromServer();
    void sendToServer(IMessage msg);
    void setServerMediator(IServerMediator serverMediator);
    //void sendToServer(ChatMessage chatMessage);
//    void displayMessage(String msg);
//    void saveLastMessage(IMessage iMessage);

}
