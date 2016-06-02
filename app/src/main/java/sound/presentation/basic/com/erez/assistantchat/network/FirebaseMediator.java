package sound.presentation.basic.com.erez.assistantchat.network;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.auth.core.FirebaseLoginDialog;

import sound.presentation.basic.com.erez.assistantchat.message.IMessage;

import sound.presentation.basic.com.erez.assistantchat.misc.App;
import sound.presentation.basic.com.erez.assistantchat.misc.IModel;
import sound.presentation.basic.com.erez.assistantchat.user.MyUserData;

/**
 * Get access to server methods utilizing firebase as a server-side.
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
    private static final String USER_DATA_CHILD = "user_data";
    private static final String NAME_USER_DATA_CHILD = "name";
    private static final String AVATAR_USER_DATA_CHILD = "avatar";

    private static final String ASSISTANT_NAME_CHILD = "name";
    private static final String TAG = "FirebaseMediator";

    public static final String CONNECTED = "connected";

    private Firebase fb;

    private String assisName;
    private ValueEventListener listenerOnConnected;
    private OpenSessionsListener openSessionsListener;
    private ValueEventListener valueEventListener;
    private IUpdateDataAssistantListener updateDataAssistantListener;
    private DataListener dataListener;
    private ValueEventListener valueEventListenerUserDetails;

    public FirebaseMediator()
    {
        Firebase.setAndroidContext(App.getInstance());
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        fb = new Firebase(FIREBASE_ADDRESS);
    }

    @Override
    public void setUpdateDataAssistantListener(IUpdateDataAssistantListener updateDataAssistantListener) {
        this.updateDataAssistantListener = updateDataAssistantListener;
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
    public void updateUserData()
    {
        final Firebase fbUserData = fb.child(OPENED_SESSIONS_CHILD).child(App.getModel().getID());
        valueEventListenerUserDetails = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.hasChild(CONNECTED))
                {
                    String name = dataSnapshot.child(USER_DATA_CHILD).child(NAME_USER_DATA_CHILD).getValue(String.class);
                    int avatar = Integer.parseInt(dataSnapshot.child(USER_DATA_CHILD).child(AVATAR_USER_DATA_CHILD).getValue(String.class));
                    MyUserData myUserData = new MyUserData(name, avatar);

                    App.getModel().setUserData(myUserData);
                    dataListener.onDetailsUpdated();
                    fbUserData.removeEventListener(valueEventListenerUserDetails); // TODO: 01/06/2016 check!!! maybe remove
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {

            }
        };
        fbUserData.addValueEventListener(valueEventListenerUserDetails);
        /*Firebase fbUserData = fb.child(OPENED_SESSIONS_CHILD).child(App.getModel().getID()).child(USER_DATA_CHILD);
        Log.d("Mediator-updateUserData", "onDataChange " + App.getModel().getID());
        if(fbUserData != null)
        {
            fbUserData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    Log.d("Mediator-updateUserData", "onDataChange " + "userData name = " + dataSnapshot.child(USER_DATA_CHILD).child("name").getValue(String.class));
                    MyUserData myUserData = new MyUserData(dataSnapshot.child("name").getValue(String.class), dataSnapshot.child("avatar").getValue(Integer.class));
                    Log.d("Mediator-updateUserData", "userData = " + myUserData.getAvatar() + " " + myUserData.getName());
                    App.getModel().setUserData(myUserData);
                    dataListener.onDetailsUpdated();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError)
                {
                    Log.e("updateUserData", firebaseError.getMessage());
                }
            });
        }*/
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
                    Log.d("FirebaseMediator", "registerOpenSessionsListener " + "id  = " + App.getModel().getID());
                    Log.d("FirebaseMediator", "hadChild  = " + dataSnapshot.hasChild(App.getModel().getID()));
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        Log.d(TAG, "onDataChange: child = " + snapshot);
                    }
                    openSessionsListener.onChatOpened();
                    
                    openSessionsListener = null; //// TODO: 01/06/2016 maybe remove 
                    fb.child(OPENED_SESSIONS_CHILD).removeEventListener(valueEventListener);
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
    public void registerDataDetailsListener(DataListener dataListener)
    {
        this.dataListener = dataListener;
        updateUserData();
    }

    @Override
    public void unregisterDataDetailsListener(DataListener dataListener)
    {
        Firebase fbUserData = fb.child(OPENED_SESSIONS_CHILD).child(App.getModel().getID());
        fbUserData.removeEventListener(valueEventListenerUserDetails);
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

    public void setListenerOnConnected(ValueEventListener listenerOnConnected) {
        this.listenerOnConnected = listenerOnConnected;
    }

    public void endConversation() {
        Log.d("Debug", "FirebaseMediator::endConversation");
        fb.child(OPENED_SESSIONS_CHILD).child(App.getModel().getID()).child(CONNECTED).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                Log.d("FirebaseMediator:endConve" , "connected =" +  snapshot.getValue(Boolean.class));
                if (snapshot.getValue(Boolean.class)) {//first one to disconnect
                    fb.child(OPENED_SESSIONS_CHILD).child(App.getModel().getID()).child(CONNECTED).setValue(false);
                } else {//second to disconnect
                    fb.child(OPENED_SESSIONS_CHILD).child(App.getModel().getID()).removeValue();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {
            }
        });



        //fb.child(OPENED_SESSIONS_CHILD).child(App.getModel().getID()).child(CONNECTED).setValue(false);
        unListeningConnected();
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
        fb.child(OPENED_SESSIONS_CHILD).child(App.getModel().getID()).child(CONNECTED).addValueEventListener(listenerOnConnected);
        //.addListenerForSingleValueEvent(listenerOnConnected);//child(CONNECTED).
    }

    public void unListeningConnected() {
        fb.child(OPENED_SESSIONS_CHILD).child(App.getModel().getID()).child(CONNECTED).removeEventListener(listenerOnConnected);

    }


    public void sendMessage(IMessage message)
    {
        fb.child(OPENED_SESSIONS_CHILD).child(App.getModel().getID()).child(MESSAGES_CHILD).push().setValue(message);
    }


    public void updateAssistantName() {

        Log.d("Debug", "getAssistantName:ID:  " + App.getModel().getID() + '\n');
        Firebase asistNameFireBase = fb.child(ASSISTANTS_DETAILS_CHILD).child(App.getModel().getID()).child(ASSISTANT_NAME_CHILD);
        asistNameFireBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                assisName = snapshot.getValue(String.class);
                Log.d("Debug", "getAssistantName:onDataChange:name:  " + assisName + '\n');
                updateDataAssistantListener.onUpdatedData();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        Log.d("Debug", "getAssistantName:name:  " + assisName + '\n');
    }

    @Override
    public String getAssistantName() {
        return assisName;
    }
}
