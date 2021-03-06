package igy.com.assistantchat.chat_ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ValueEventListener;

import java.util.concurrent.TimeUnit;

import igy.com.assistantchat.R;
import igy.com.assistantchat.chat_controller.IChatController;
import igy.com.assistantchat.chat_controller.MyChatController;
import igy.com.assistantchat.message.ChatMessage;
import igy.com.assistantchat.message.IMessage;
import igy.com.assistantchat.misc.App;
import igy.com.assistantchat.misc.Factory;
import igy.com.assistantchat.misc.TimerUtility;
import igy.com.assistantchat.misc.Utility;
import igy.com.assistantchat.network.FirebaseMediator;
import igy.com.assistantchat.network.MyFirebaseListAdapter;

public class ChatActivity extends AppCompatActivity implements IChatUI {

    private IChatController controller;
    private ListView conversationList;
    private EditText sendingMsg;
    private Button sendButton;
    private Button endConversationButton;
    private TextView userIpTextView;
    private String userIp;
    private boolean homebuttonPressed = false;

    //private MessagesAdapter adapterList;
    private MyFirebaseListAdapter adapterList;
    private FirebaseMediator mediator;
    private boolean isDisconnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_list);
        Log.d("chatActivity", "onCreate");
        controller = new MyChatController(this);
        mediator = (FirebaseMediator) App.getServerMediator();
        controller.setServerMediator(mediator);
        mediator.setListenerOnConnected((ValueEventListener) controller);
        mediator.executeListeningConnected();
        userIp = Utility.getUserIP();

        Log.d("ChatActivity - onCreate", "user data : " + App.getModel().getUserData().getName() + " " + App.getModel().getUserData().getAvatar());
        conversationList = (ListView) findViewById(R.id.conversation_list);
        endConversationButton = (Button) findViewById(R.id.end_convrs_button);
//        userIpTextView = (TextView)  findViewById(R.id.user_ip);

        sendingMsg = (EditText) findViewById(R.id.sending_msg);
        sendButton = (Button) findViewById(R.id.send_button);
        endConversationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mediator.endConversation();
                homebuttonPressed = true;
                Log.d("OnClickListener - Chat", "endConversationButton");
                finish();
            }
        });

        final String assistantName = mediator.getAssistantName();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMessage(String.valueOf(sendingMsg.getText()), TimerUtility.currentTime(), assistantName); //maybe after put it in server it display the messages on the screen for valid that the messages on the server
                sendingMsg.setText("");

            }
        });

        adapterList = new MyFirebaseListAdapter(this, ChatMessage.class, R.layout.chat_user_list_item, mediator.getMessagesDB(), true);

        conversationList.setAdapter(adapterList);
    }



    private void sendMessage(String msg, String date, String sendingName)
    {
        IMessage chatMessage = Factory.createMessage(msg, date, sendingName, userIp, "1", false);
        controller.sendToServer(chatMessage);
    }


    @Override
    protected void onStop() {
        mediator.removeActiveAssistant(App.getModel().getID());
        if (! homebuttonPressed ) {
            Log.d("ChatActivity", "onStop: inside homebuttonPressed");
            finish();
        }
        Log.d("ChatActivity", "onStop");
        if (!isDisconnected) {
            mediator.endConversation();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("ChatActivity", "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onUserDisconnected() {
        isDisconnected = true;
        mediator.endConversation();
        Log.d("ChatActivity" , "onUserDisconnected");
        new AlertDialog.Builder(ChatActivity.this)
                .setTitle("Note")
                .setMessage("User disconnected")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        homebuttonPressed = true;
                        ChatActivity.this.finish();
                    }
                }).show();
    }
}
