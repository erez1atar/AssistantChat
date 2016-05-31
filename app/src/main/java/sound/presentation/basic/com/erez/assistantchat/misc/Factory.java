package sound.presentation.basic.com.erez.assistantchat.misc;


import sound.presentation.basic.com.erez.assistantchat.message.ChatMessage;
import sound.presentation.basic.com.erez.assistantchat.message.IMessage;

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
