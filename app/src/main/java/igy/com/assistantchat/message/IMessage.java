package igy.com.assistantchat.message;

/**
 * Interface for message objects utilized in this app.
 */
public interface IMessage
{
    String getDate();

    String getMsg();

    String getSendingName();

    String getIp();

    String getSenderID();

    Boolean getLastMsg();
}
