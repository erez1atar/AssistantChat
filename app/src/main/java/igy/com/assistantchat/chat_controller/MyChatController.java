package igy.com.assistantchat.chat_controller;


import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import igy.com.assistantchat.message.IMessage;
import igy.com.assistantchat.misc.Factory;
import igy.com.assistantchat.network.IServerMediator;

/**
 * Created by user on 03/05/2016.
 */
public class MyChatController implements IChatController, ValueEventListener {

//    private WeakReference<IPresentChat> iWRPresent;
    private IServerMediator serverMediator;
//    private SavingLastMessage latestMessages = new SavingLastMessage(10);

    //get message according to identifier/token of user(can firebase do search into)
//    public List<IMessage> getMessageFromServer() { //add parameters
//        return serverMediator.getMessageHistory();
//    }

    @Override
    public void sendToServer(IMessage msg) {
        serverMediator.sendMessage(msg);
    }

//    public void sendToServer(ChatMessage chatMessage){
//        serverMediator.sendMessage(chatMessage);
//    }

//    public void displayMessage(ChatMessage chatMessage) {
//        iWRPresent.displayMessage(chatMessage);
//    }


//
//    @Override
//    public void onUpdate(Object o) {
//
//        if (o instanceof ChatMessage) {
//            ChatMessage chatMessage = (ChatMessage) o;
////            iWRPresent.get().displayMessage( chatMessage );
//        }
//    }

    @Override
    public void setServerMediator(IServerMediator serverMediator) {
        this.serverMediator = serverMediator;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d("Debug", "AssisChat:enter onDataChange");
            if (! dataSnapshot.getValue(Boolean.class) ) {
                Log.d("Debug", "AssisChat:onDataChange false");
                serverMediator.unListeningConnected();
                //sendDisconnectedMsgFromOtherSide();
                //instead dialog
            }
            else {
               // serverMediator.updateAssistantName();
            }
            Log.d("Debug", "AssisChat:onDataChange");
//        }
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }

    private void sendDisconnectedMsgFromOtherSide() {
        IMessage endMsg = Factory.createMessage("userName disconnected", "", "system", "","",false);
        sendToServer(endMsg);
    }

}