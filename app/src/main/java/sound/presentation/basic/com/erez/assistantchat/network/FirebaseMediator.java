package sound.presentation.basic.com.erez.assistantchat.network;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
/**
 * Created by LENOVO on 22/05/2016.
 */
public class FirebaseMediator implements IServerMediator
{

    private static final String FIREBASE_ADDRESS = "https://incandescent-inferno-5809.firebaseio.com";
    private static final String ACTIVE_ASSISTANTS_CHILD = "active_assistants";
    private static final String ASSISTANTS_DETAILS_CHILD = "assistants";
    private static final String OPENED_SESSIONS_CHILD = "opened_sessions";

    private Firebase fb;


}
