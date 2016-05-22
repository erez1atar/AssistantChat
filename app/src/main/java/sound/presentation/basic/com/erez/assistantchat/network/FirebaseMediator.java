package sound.presentation.basic.com.erez.assistantchat.network;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import sound.presentation.basic.com.erez.assistantchat.message.IMessage;
import sound.presentation.basic.com.erez.assistantchat.misc.App;

/**
 * Created by LENOVO on 22/05/2016.
 */
public class FirebaseMediator implements IServerMediator
{

    private static final String FIREBASE_ADDRESS = "https://incandescent-inferno-5809.firebaseio.com";
    private static final String ACTIVE_ASSISTANTS_CHILD = "active_assistants";
    private static final String ASSISTANTS_DETAILS_CHILD = "assistants";
    private static final String OPENED_SESSIONS_CHILD = "opened_sessions";
    private static final String AVAILABLE_ASSISTANT_FLAG_CHILD = "available";
    private static final String MESSAGES_CHILD = "chat";
    private static final String LAST_MESSAGES_CHILD = "last_messages_child";
    public static final String CONNECTED = "connected";


    private Firebase fb;
    private ValueEventListener listener;


    public FirebaseMediator()
    {
        Firebase.setAndroidContext(App.getInstance());
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        fb = new Firebase(FIREBASE_ADDRESS);
    }


    @Override
    public void changeAvailableStatus(boolean available)
    {
        fb.child(ASSISTANTS_DETAILS_CHILD).child(App.getiModel().getAssistantName()).child(AVAILABLE_ASSISTANT_FLAG_CHILD).setValue(available);
    }

    public void setListener(ValueEventListener listener) {
        this.listener = listener;
    }

    public void endConversation() {
        Log.d("Debug", "endConversation");
        fb.child(OPENED_SESSIONS_CHILD).child(App.getiModel().getAssistantName()).child(CONNECTED).setValue(false);
    }

    public Firebase getMessagesDB()
    {
        return fb.child(OPENED_SESSIONS_CHILD).child(App.getiModel().getAssistantName()).child(MESSAGES_CHILD);
    }

    public Firebase getLastMessagesDB()
    {
        return fb.child(OPENED_SESSIONS_CHILD).child(App.getiModel().getAssistantName()).child(LAST_MESSAGES_CHILD);
    }

    public void executeListeningConnected() {
        fb.child(OPENED_SESSIONS_CHILD).child(App.getiModel().getAssistantName()).child(CONNECTED).addValueEventListener(listener);
        //.addListenerForSingleValueEvent(listener);//child(CONNECTED).
    }

    public void sendMessage(IMessage message)
    {
        //fb.child(MESSAGES_CHILD).push().setValue(message);
        fb.child(OPENED_SESSIONS_CHILD).child(App.getiModel().getAssistantName()).child(MESSAGES_CHILD).push().setValue(message);
    }

}
