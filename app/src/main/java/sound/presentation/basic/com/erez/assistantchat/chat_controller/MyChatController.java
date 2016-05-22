package sound.presentation.basic.com.erez.assistantchat.chat_controller;


import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import sound.presentation.basic.com.erez.assistantchat.message.ChatMessage;
import sound.presentation.basic.com.erez.assistantchat.network.IServerMediator;

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
    public void sendToServer(ChatMessage msg) {
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

    public void setServerMediator(IServerMediator serverMediator) {
        this.serverMediator = serverMediator;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d("Debug", "enter onDataChange");
//        if( dataSnapshot.hasChild( FirebaseMediator.CONNECTED ) ) //serverMediator.getActiveAssistant()
//        {
//            Log.d("Debug", "enter if onDataChange");
//            fb.child(USERS_CHILD).child(String.valueOf(App.getServerMediator().getToken())).child(assistant.getName()).setValue(new ChatMessage("Chat started", "1.1.2016", assistant.getName(), "should be ip"));
            if (! dataSnapshot.getValue(Boolean.class) ) {
                Log.d("Debug", "onDataChange false");
//store last 10 entries
//Persistance persistance = new Persistance();
//
//List<IMessage> arrayLastMsgs = latestMessages.getSavedMessages();
//
//persistance.saveLastMessages( arrayLastMsgs );

            }
            Log.d("Debug", "onDataChange");
//        }
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }

}
