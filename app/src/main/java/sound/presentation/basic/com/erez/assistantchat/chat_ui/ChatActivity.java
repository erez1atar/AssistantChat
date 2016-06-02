package sound.presentation.basic.com.erez.assistantchat.chat_ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import sound.presentation.basic.com.erez.assistantchat.R;
import sound.presentation.basic.com.erez.assistantchat.chat_controller.IChatController;
import sound.presentation.basic.com.erez.assistantchat.chat_controller.MyChatController;
import sound.presentation.basic.com.erez.assistantchat.message.ChatMessage;
import sound.presentation.basic.com.erez.assistantchat.message.IMessage;
import sound.presentation.basic.com.erez.assistantchat.misc.App;
import sound.presentation.basic.com.erez.assistantchat.misc.Factory;
import sound.presentation.basic.com.erez.assistantchat.misc.TimerUtility;
import sound.presentation.basic.com.erez.assistantchat.misc.Utility;
import sound.presentation.basic.com.erez.assistantchat.network.FirebaseMediator;
import sound.presentation.basic.com.erez.assistantchat.network.MyFirebaseListAdapter;

public class ChatActivity extends AppCompatActivity {

    private IChatController controller;
    private ListView conversationList;
    private EditText sendingMsg;
    private Button sendButton;
    private Button endConversationButton;
    private TextView userIpTextView;
    private String userIp;

    //private MessagesAdapter adapterList;
    private MyFirebaseListAdapter adapterList;
    private FirebaseMediator mediator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_list);
        Log.d("chatActivity", "onCreate");
        controller = new MyChatController();
        mediator = (FirebaseMediator) App.getServerMediator();
        controller.setServerMediator(mediator);
        mediator.setListenerOnConnected((ValueEventListener) controller);
        mediator.executeListeningConnected();
        userIp = Utility.getUserIP();

        Log.d("ChatActivity - onCreate", "user data : " + App.getModel().getUserData().getName() + " " + App.getModel().getUserData().getAvatar());
        //Log.d("ChatActivity - onCreate", "user data : " + " " + App.getModel().getUserData().getAvatar());

//        final SavingLastMessage saveLastMessage = new SavingLastMessage(10);
        conversationList = (ListView) findViewById(R.id.conversation_list);
        endConversationButton = (Button) findViewById(R.id.end_convrs_button);
        userIpTextView = (TextView)  findViewById(R.id.user_ip);

        sendingMsg = (EditText) findViewById(R.id.sending_msg);
        sendButton = (Button) findViewById(R.id.send_button);
        endConversationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediator.endConversation();
                Log.d("OnClickListener - Chat", "");
                finish();
            }
        });

        final String assistantName = mediator.getAssistantName();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String date, String sendingName get from server(fireBase) or another place

                ////
//                ChatMessage chatMessage = new ChatMessage("checking", currentDate(), "userCheck");
                //displayMessage(chatMessage);//bcuse pull the msg from server and adapter add it to listView
                ////
                sendMessage(String.valueOf(sendingMsg.getText()), TimerUtility.currentTime(), assistantName); //maybe after put it in server it display the messages on the screen for valid that the messages on the server
                sendingMsg.setText("");

            }
        });

        adapterList = new MyFirebaseListAdapter(this, ChatMessage.class, R.layout.chat_user_list_item, mediator.getMessagesDB(), true);

//        adapterList = new FirebaseListAdapter<ChatMessage>( this, ChatMessage.class, R.layout.item_list, mediator.getMessagesDB() )
//        {
//            @Override
//            protected void populateView(View view, ChatMessage message, int i) {
//                Log.d("fireAdap_populateView", "i is " + i + "\nand count is " + adapterList.getCount());
//                Log.d("Debug", "populateView");
////controller.saveLastMessage(message);
//
//                TextView msg = (TextView) view.findViewById(R.id.message);
//                TextView date = (TextView) view.findViewById(R.id.date);
//                TextView senderName = (TextView) view.findViewById(R.id.sender_name);
//                msg.setText(message.getMsg());
//                date.setText(currentDate());
//                senderName.setText(message.getSendingName());
//                userIP.setText(message.getIp());//ip only for user
//            }
//        };

//        adapterList = new MessagesAdapter(this, R.layout.item_list);
//        adapterList.setMsgsArr(getLastMessages());
        conversationList.setAdapter(adapterList);
    }



    private void sendMessage(String msg, String date, String sendingName)
    {
        IMessage chatMessage = Factory.createMessage(msg, date, sendingName, "", "1", false);
        controller.sendToServer(chatMessage);
    }

//    public void displayMessage(ChatMessage chatMessage) {
//
//        adapterList.addMsgs(chatMessage);
//        //conversationList.setText(finalMsg);
//    }

    @Override
    protected void onStop()
    {
        mediator.endConversation();
        super.onStop();
    }



}
