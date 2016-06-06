package igy.com.assistantchat.misc;


import igy.com.assistantchat.message.ChatMessage;
import igy.com.assistantchat.message.IMessage;

/**
 * Class containing various factory methods utilized in this app.
 */
public class Factory
{
    public static IMessage createMessage(String msg, String date, String sendingName, String ip, String senderID, Boolean lastMsg)
    {
        return new ChatMessage(msg, date, sendingName, ip, senderID, lastMsg);
    }
}
