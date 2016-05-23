package sound.presentation.basic.com.erez.assistantchat.network;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.security.token.TokenGenerator;

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

    private Firebase fb;
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
        if (admin_token == null)
        {
            Map<String, Object> payload = new HashMap<>();
            payload.put("uid", UUID.randomUUID());
            TokenGenerator tokenizer = new TokenGenerator(App.getInstance().getString(R.string.firebase_secret));
            admin_token = tokenizer.createToken(payload);
        }
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

}
