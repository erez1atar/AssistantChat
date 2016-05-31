package sound.presentation.basic.com.erez.assistantchat.message;

import android.util.Log;

/**
 * Message object to be saved on firebase and displayed on UI.
 */
public class ChatMessage implements IMessage
{
    private String msg;
    private String date;
    private String sendingName;
    private String ip;
    private String senderID;
    private Boolean lastMsg;

    public ChatMessage(){}

//    public ChatMessage(String msg, String date, String sendingName, String ip, String senderID) {
//        this.msg = msg;
//        this.date = date;
//        this.sendingName = sendingName;
//        this.ip = ip;
//        this.senderID = senderID;
//        this.lastMsg = false;
//
//    }

    public ChatMessage(String msg, String date, String sendingName, String ip, String senderID, Boolean lastMsg) {
        this.msg = msg;
        this.date = date;
        this.sendingName = sendingName;
        this.ip = ip;
        this.senderID = senderID;
        this.lastMsg = lastMsg;

    }


    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getSendingName() {
        return sendingName;
    }

    @Override
    public String getIp() {
        return ip;
    }

    @Override
    public String getSenderID() {
        return senderID;
    }

    @Override
    public Boolean getLastMsg(){
        return lastMsg;
    }


    @Override
    public String toString() {
        Log.d("Debug", "toString- ChatMessage");
        return "ChatMessage{msg='" + msg + "\', date='" + date + "\', sendingName='" + sendingName + "\', ip='" + ip + "\', senderID='" + senderID + "\', lastMsg='" + lastMsg + "\'}";
    }
}
