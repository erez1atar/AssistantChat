package sound.presentation.basic.com.erez.assistantchat.connection_ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import sound.presentation.basic.com.erez.assistantchat.R;
import sound.presentation.basic.com.erez.assistantchat.misc.App;

public class ConnectionActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        final ControllerConnection controller = new ControllerConnection();

        App.getServerMediator().addActiveAssistant(App.getiModel().getAssistantName()); // TODO: 22/05/2016 Remove

        final Switch availableSwitch = (Switch)findViewById(R.id.availableSwitch);
        if (availableSwitch != null)
        {
            availableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    App.getServerMediator().changeAvailableStatus(isChecked);
                    if(isChecked)
                    {
                        App.getServerMediator().registerOpenSessionsListener(controller);
                    }
                    else
                    {
                        App.getServerMediator().clearOpenSessionsListener();
                    }
                }
            });
        }

    }
}
