package sound.presentation.basic.com.erez.assistantchat.network;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.security.token.TokenGenerator;
import com.firebase.security.token.TokenOptions;

import sound.presentation.basic.com.erez.assistantchat.message.IMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import sound.presentation.basic.com.erez.assistantchat.R;
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
    private static final String LAST_MESSAGES_CHILD = "last_messages";

    private static final String TAG = "FirebaseMediator";

    public static final String CONNECTED = "connected";

    private Firebase fb;
    private ValueEventListener listener;
    private OpenSessionsListener openSessionsListener;
    private ValueEventListener valueEventListener;
    private String admin_token;

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

    @Override
    public void login()
    {
        Map<String, Object> payload = new HashMap<>();
        payload.put("uid", UUID.randomUUID());
        payload.put("password", "42");

        TokenOptions options = new TokenOptions();
        options.setAdmin(true);

        TokenGenerator tokenizer = new TokenGenerator(App.getInstance().getString(R.string.firebase_secret));
        fb.authWithCustomToken(tokenizer.createToken(payload, options), new Firebase.AuthResultHandler()
        {
            @Override
            public void onAuthenticated(AuthData authData)
            {
                Log.d(TAG + "_login", "authentication successful!");
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError)
            {
                Log.e(TAG + "_login", "authentication failed!");
            }
        });
    }

    @Override
    public void registerOpenSessionsListener(OpenSessionsListener listener)
    {
        openSessionsListener = listener;
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.hasChild(App.getiModel().getAssistantName()))
                {
                    openSessionsListener.onChatOpened();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {

            }
        };
        fb.child(OPENED_SESSIONS_CHILD).addValueEventListener(valueEventListener);
    }

    @Override
    public void clearOpenSessionsListener()
    {
        openSessionsListener = null;
        fb.child(OPENED_SESSIONS_CHILD).removeEventListener(valueEventListener);
    }

    @Override
    public void addActiveAssistant(String assistantName)
    {
        fb.child(ACTIVE_ASSISTANTS_CHILD).push().setValue(assistantName);
    }

    @Override
    public void removeActiveAssistant(String assistantName)
    {
        fb.child(ACTIVE_ASSISTANTS_CHILD).child(assistantName).removeValue();
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
