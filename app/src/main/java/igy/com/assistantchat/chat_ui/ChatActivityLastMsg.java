package igy.com.assistantchat.chat_ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import igy.com.assistantchat.R;
import igy.com.assistantchat.chat_controller.IChatController;
import igy.com.assistantchat.chat_controller.MyChatController;
import igy.com.assistantchat.message.ChatMessage;
import igy.com.assistantchat.misc.ActivityRouter;
import igy.com.assistantchat.misc.App;
import igy.com.assistantchat.network.FirebaseMediator;

public class ChatActivityLastMsg extends AppCompatActivity {

    private IChatController controller;
    private ListView conversationList;
    private Button continueChat;
//    private TextView userIP;
    //private MessagesAdapter adapterList;
    private FirebaseListAdapter<ChatMessage> adapterList;
    private FirebaseMediator mediator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_last_list);

        Log.d("chatActivityLastMsg","onCreate");
        controller = new MyChatController();
        mediator = (FirebaseMediator) App.getServerMediator();
        controller.setServerMediator(mediator);
        mediator.setListenerOnConnected((ValueEventListener) controller);
        mediator.executeListeningConnected();

//        final SavingLastMessage saveLastMessage = new SavingLastMessage(10);

        conversationList = (ListView) findViewById(R.id.conversation_list);
        continueChat = (Button) findViewById(R.id.cont_button);
//        userIP = (TextView) findViewById(R.id.user_ip);

        adapterList = new FirebaseListAdapter<ChatMessage>( this, ChatMessage.class, R.layout.item_list, mediator.getLastMessagesDB() )
        {
            @Override
            protected void populateView(View view, ChatMessage message, int i) {
                Log.d("fireAdap_populateView", "i is " + i + "\nand count is " + adapterList.getCount());
                Log.d("Debug", "populateView");
//controller.saveLastMessage(message);

                TextView msg = (TextView) view.findViewById(R.id.message);
                TextView date = (TextView) view.findViewById(R.id.date);
                TextView senderName = (TextView) view.findViewById(R.id.sender_name);
                TextView userIP = (TextView) findViewById(R.id.user_ip);
                msg.setText(message.getMsg());
                date.setText(message.getDate());
                senderName.setText(message.getSendingName());
                userIP.setText(message.getIp());
            }
        };

//        adapterList = new MessagesAdapter(this, R.layout.item_list);
//        adapterList.setMsgsArr(getLastMessages());
        conversationList.setAdapter(adapterList);

        continueChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "ActivityRouter.changeActivity:continueChat");
                ActivityRouter.changeActivity(App.getInstance(), ChatActivity.class);//ChatActivityLastMsg.this
//                finish();
            }
        });

    }




    //todo - once you create the firebase location (by getting the assistant)
    //you call the following function
    // mediator.sendLastMessages();


//
//        //todo - add in the on stop - on destroy - whatever...
//        List<IMessage> lastMessages = new ArrayList<>(10);
//        for (int i = 0; i < 10 ; i++){
//            lastMessages.add(adapterList.getItem(adapterList.getCount()- (10 - i)));
//        }
//        Persistance per = new Persistance();
//        per.saveLastMessage(lastMessages);
//

    private String currentDate()
    {
        SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        df.setTimeZone(TimeZone.getDefault() );
//      Calendar.getInstance().setTimeZone(TimeZone.getDefault() );
        return df.format( Calendar.getInstance().getTime() ) ; // + Calendar.getInstance().(Calendar.DST_OFFSET) ) ;//Calendar.getInstance().getTime()
    }

    private List<ChatMessage> getLastMessages()
    {
//        List<ChatMessage> chatMessageArr = new ArrayList<>();
//        while(  ) {//according token of user from server
//            chatMessageArr.add( controller.getMessageFromServer() ) ; //should be in loop (depandant in the storing form in fireBase)
//        }
//        return chatMessageArr;
        return new ArrayList<>();
    }

    private void sendMessage(String msg, String date, String sendingName, String ip)
    {
//        ChatMessage chatMessage = (ChatMessage) Factory.createMessage(msg, date, sendingName, ip);

//        controller.sendToServer(chatMessage);
    }

//    public void displayMessage(ChatMessage chatMessage) {
//
//        adapterList.addMsgs(chatMessage);
//        //conversationList.setText(finalMsg);
//    }

    @Override
    protected void onStop()
    {
       // mediator.endConversation();
        super.onStop();
    }



}