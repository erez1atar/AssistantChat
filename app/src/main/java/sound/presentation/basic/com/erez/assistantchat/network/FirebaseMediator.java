package sound.presentation.basic.com.erez.assistantchat.network;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import sound.presentation.basic.com.erez.assistantchat.message.IMessage;

import sound.presentation.basic.com.erez.assistantchat.misc.App;
import sound.presentation.basic.com.erez.assistantchat.misc.IModel;

/**
 * Get access to server methods utilizing firebase as a server-side.
 */
public class FirebaseMediator implements IServerMediator
{
    String assisName;

    private static final String FIREBASE_ADDRESS = "https://incandescent-inferno-5809.firebaseio.com";
    private static final String ACTIVE_ASSISTANTS_CHILD = "active_assistants";
    private static final String ASSISTANTS_DETAILS_CHILD = "assistants";
    private static final String OPENED_SESSIONS_CHILD = "opened_sessions";
    private static final String AVAILABLE_ASSISTANT_FLAG_CHILD = "available";
    private static final String MESSAGES_CHILD = "chat";
    private static final String LAST_MESSAGES_CHILD = "last_messages";
    private static final String ASSISTANT_NAME_CHILD = "name";
    private static final String TAG = "FirebaseMediator";

    public static final String CONNECTED = "connected";

    private Firebase fb;
    private ValueEventListener listener;
    private OpenSessionsListener openSessionsListener;
    private ValueEventListener valueEventListener;

    public FirebaseMediator()
    {
        Firebase.setAndroidContext(App.getInstance());
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        fb = new Firebase(FIREBASE_ADDRESS);
    }

    @Override
    public void changeAvailableStatus(boolean available)
    {
        fb.child(ASSISTANTS_DETAILS_CHILD).child(App.getModel().getID()).child(AVAILABLE_ASSISTANT_FLAG_CHILD).setValue(available);
    }

    @Override
    public void login(final ILoginAuthentication authentication)
    {
        final IModel model = App.getModel();
        fb.authWithPassword(model.getEmail(), model.getPassword(), new Firebase.AuthResultHandler()
        {
            @Override
            public void onAuthenticated(AuthData authData)
            {
                Log.d(TAG + "_login", "authentication successful!");
                model.setID(authData.getUid());
                authentication.onLoginSuccess();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError)
            {
                Log.e(TAG + "_login", firebaseError.getMessage());
                authentication.onLoginFailed();
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
                if(dataSnapshot.hasChild(App.getModel().getID()))
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
        if(openSessionsListener != null)
        {
            openSessionsListener = null;
            fb.child(OPENED_SESSIONS_CHILD).removeEventListener(valueEventListener);
        }
    }

    @Override
    public void addActiveAssistant(String assistantName)
    {
        fb.child(ACTIVE_ASSISTANTS_CHILD).child(assistantName).setValue(assistantName);
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
        Log.d("Debug", "FirebaseMediator::endConversation");
        fb.child(OPENED_SESSIONS_CHILD).child(App.getModel().getID()).child(CONNECTED).setValue(false);
    }

    public Firebase getMessagesDB()
    {
        return fb.child(OPENED_SESSIONS_CHILD).child(App.getModel().getID()).child(MESSAGES_CHILD);
    }

    public Firebase getLastMessagesDB()
    {
        return fb.child(OPENED_SESSIONS_CHILD).child(App.getModel().getID()).child(LAST_MESSAGES_CHILD);
    }

    public void executeListeningConnected() {
        fb.child(OPENED_SESSIONS_CHILD).child(App.getModel().getID()).child(CONNECTED).addValueEventListener(listener);
        //.addListenerForSingleValueEvent(listener);//child(CONNECTED).
    }

    public void sendMessage(IMessage message)
    {
        fb.child(OPENED_SESSIONS_CHILD).child(App.getModel().getID()).child(MESSAGES_CHILD).push().setValue(message);
    }

    public String getAssistantName() {

        Log.d("Debug", "getAssistantName:ID:  " + App.getModel().getID() + '\n');
        Firebase asistNameFireBase = fb.child(ASSISTANTS_DETAILS_CHILD).child(App.getModel().getID()).child(ASSISTANT_NAME_CHILD);
        asistNameFireBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                assisName = snapshot.getValue(String.class);
                Log.d("Debug", "getAssistantName:onDataChange:name:  " + assisName + '\n');
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("Debug", "getAssistantName:name:  " + assisName + '\n');
        return assisName;
    }


}
