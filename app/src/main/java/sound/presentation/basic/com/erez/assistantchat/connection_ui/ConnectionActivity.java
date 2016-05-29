package sound.presentation.basic.com.erez.assistantchat.connection_ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import sound.presentation.basic.com.erez.assistantchat.R;
import sound.presentation.basic.com.erez.assistantchat.login_ui.LoginActivity;
import sound.presentation.basic.com.erez.assistantchat.misc.App;

public class ConnectionActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        final ControllerConnection controller = new ControllerConnection();

        App.getServerMediator().addActiveAssistant(App.getiModel().getAssistantName());
        App.getServerMediator().changeAvailableStatus(false);

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

        final Button endOfShiftbutton = (Button)findViewById(R.id.end_of_shift_button);
        if (endOfShiftbutton != null) {
            endOfShiftbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    App.getServerMediator().removeActiveAssistant(App.getiModel().getAssistantName());
                    App.getServerMediator().changeAvailableStatus(false);
                    Intent intent = new Intent(App.getInstance(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }

    }
}
