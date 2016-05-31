package sound.presentation.basic.com.erez.assistantchat.connection_ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import sound.presentation.basic.com.erez.assistantchat.R;

public class ConnectionActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        final IControllerConnection controller = new ControllerConnection();

        controller.changeAvailableStatus(false);
        controller.addToActiveAssistants();

        final Switch availableSwitch = (Switch)findViewById(R.id.availableSwitch);
        if (availableSwitch != null)
        {
            availableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                controller.changeAvailableStatus(isChecked);
                /*if(isChecked)
                {
                    App.getServerMediator().registerOpenSessionsListener(controller);
                }
                else
                {
                    App.getServerMediator().clearOpenSessionsListener();
                }*/
                }

            });
        }

        Button endOfShiftButton = (Button)findViewById(R.id.end_of_shift_button);
        if (endOfShiftButton != null) {
            endOfShiftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    controller.finishShift();
                    finish();
                }
            });
        }

    }
}
