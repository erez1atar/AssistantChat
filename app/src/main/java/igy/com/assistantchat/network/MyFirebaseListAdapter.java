package igy.com.assistantchat.network;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import java.util.HashMap;

import igy.com.assistantchat.R;
import igy.com.assistantchat.message.ChatMessage;
import igy.com.assistantchat.misc.TimerUtility;

/**
 * Created by user on 29 מאי 2016.
 */
public class MyFirebaseListAdapter extends FirebaseListAdapter {

    private final boolean lastMessagesVisible;
    private int offset;
    private boolean sendLastMessagesRequest;
    private final HashMap<String,String> timeStamper = new HashMap<>(100);

    public MyFirebaseListAdapter(Activity activity, Class modelClass, int modelLayout, Firebase ref, boolean lastMessagesVisible) {
        super(activity, modelClass, modelLayout, ref);
        this.lastMessagesVisible = lastMessagesVisible;
    }

    public MyFirebaseListAdapter(Activity activity, Class modelClass, int modelLayout, Query ref, boolean lastMessagesVisible) {
        super(activity, modelClass, modelLayout, ref);
        this.lastMessagesVisible = lastMessagesVisible;
    }



    @Override
    public int getCount(){
        if (lastMessagesVisible ){
            offset = 0;
            return super.getCount();
        }

        if (sendLastMessagesRequest){
            sendLastMessagesRequest = false;
            return super.getCount();
        }

        int userCountSize = 0;
        int realSize = super.getCount();
        for (int i = 0 ; i < realSize; i++){
            ChatMessage chatMessage = (ChatMessage)super.getItem(i);
            if (!chatMessage.getLastMsg()){
                userCountSize++;
            }
        }
        offset = realSize - userCountSize;
        return userCountSize;
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage model = (ChatMessage)this.getItem(position);
        return (Integer.parseInt(model.getSenderID()) == 0) ? 0 : 1;
    }


    @Override
    public Object getItem(int position){
        if (sendLastMessagesRequest){
            sendLastMessagesRequest = false;
            return super.getItem(position);
        }
        return super.getItem(position + offset);
    }


    public Object getRealItem(int position){
        sendLastMessagesRequest = true;
        return super.getItem(position);
    }

    public int getRealCount(){
        sendLastMessagesRequest = true;
        return super.getCount();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ChatMessage model = (ChatMessage)this.getItem(position);

        if(view == null) {

            if (getItemViewType(position) == 0){//user layout
                Log.d("MyFirebaseListAdapter","test if " + model.getSenderID());
                view = this.mActivity.getLayoutInflater().inflate(this.mLayout, viewGroup, false);
            }else{//assistant layout
                Log.d("MyFirebaseListAdapter","test else " + model.getSenderID() );
                view = this.mActivity.getLayoutInflater().inflate(R.layout.chat_assistant_list_item, viewGroup, false);
            }
        }

        this.populateView(view, model, position);
        return view;
    }


    @Override
    protected void populateView(View view, Object message, int i) {
        TextView msg = (TextView) view.findViewById(R.id.chat_message);
        TextView date = (TextView) view.findViewById(R.id.chat_date);
        TextView senderName = (TextView) view.findViewById(R.id.chat_name);
        TextView ip = (TextView)view.findViewById(R.id.chat_ip);
        if (ip != null){
            ip.setText(((ChatMessage)message).getIp());
        }
        String time = TimerUtility.currentTime();
        String key = getRef(i).getKey();
        if (! timeStamper.containsKey(key)){
            timeStamper.put(key,time);
        } else {
            time = timeStamper.get(key);
        }
        if (((ChatMessage) message).getLastMsg()){
            view.setBackgroundColor(Color.rgb(102, 255, 178));
            date.setText(((ChatMessage) message).getDate());
        }else{
            view.setBackgroundColor(Color.WHITE);
            date.setText(time);
        }
        senderName.setText(((ChatMessage) message).getSendingName());
        msg.setText(((ChatMessage)message).getMsg());
    }
}
