package igy.com.assistantchat.connection_ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;

import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

import igy.com.assistantchat.R;
import igy.com.assistantchat.misc.Utility;

public class ConnectionActivity extends AppCompatActivity implements IConnectionUI
{
    private Switch availableSwitch;
    private IControllerConnection controller;
    private boolean chatOpened;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        controller = new ControllerConnection(this);
        progressBar = (ProgressBar)findViewById(R.id.progressbarId);
        controller.changeAvailableStatus(false);
        controller.addToActiveAssistants();
        Utility.findUserIP();

        availableSwitch = (Switch)findViewById(R.id.availableSwitch);
        if (availableSwitch != null)
        {
            availableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    controller.changeAvailableStatus(isChecked);
                    progressBar.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
                }
            });
        }

        Button endOfShiftButton = (Button)findViewById(R.id.end_of_shift_button);
        if (endOfShiftButton != null) {
            endOfShiftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    controller.finishShift();
                    finish();
                }
            });
        }

    }

    @Override
    public void onAvailableStatusChanged(boolean isAvailable) {
        availableSwitch.setChecked(isAvailable);
    }

    @Override
    public void OnChatOpened() {
        chatOpened = true;
    }

    @Override
    protected void onStop() {
        Log.d("connectionActivity", "onStop");
        controller.changeAvailableStatus(false);
        if(! chatOpened) {
            Log.d("connectionActivity" ,"onStop : chatOpened = false"  );
            controller.finishShift();
        }
        Utility.resetIP();
        super.onStop();
    }

    @Override
    protected void onResume() {
        Log.d("connectionActivity", "onResume");
        chatOpened = false;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                controller.addToActiveAssistants();
            }
        }, 2000);
        availableSwitch.setChecked(false);
        progressBar.setVisibility(View.INVISIBLE);
        super.onResume();
    }

}
