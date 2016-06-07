package igy.com.assistantchat.chat_controller;


import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.lang.ref.WeakReference;

import igy.com.assistantchat.chat_ui.IChatUI;
import igy.com.assistantchat.message.IMessage;
import igy.com.assistantchat.misc.Factory;
import igy.com.assistantchat.network.IServerMediator;

/**
 * Created by user on 03/05/2016.
 */
public class MyChatController implements IChatController, ValueEventListener {

    private IServerMediator serverMediator;
    private WeakReference<IChatUI> iChatUIWeakReference;

    public MyChatController(IChatUI iChatUI){
        iChatUIWeakReference = new WeakReference<IChatUI>(iChatUI);
    }

    @Override
    public void sendToServer(IMessage msg) {
        serverMediator.sendMessage(msg);
    }

    public void setServerMediator(IServerMediator serverMediator) {
        this.serverMediator = serverMediator;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d("Debug", "AssisChat:enter onDataChange");
            if (! dataSnapshot.getValue(Boolean.class) ) {
                Log.d("Debug", "AssisChat:onDataChange false");
                serverMediator.unListeningConnected();
                IChatUI iChatUI = iChatUIWeakReference.get();
                if(iChatUI != null) {
                    iChatUI.onUserDisconnected();
                }
            }
            Log.d("Debug", "AssisChat:onDataChange");
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}
