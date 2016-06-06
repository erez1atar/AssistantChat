package igy.com.assistantchat.chat_controller;


import igy.com.assistantchat.message.IMessage;
import igy.com.assistantchat.network.IServerMediator;

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
