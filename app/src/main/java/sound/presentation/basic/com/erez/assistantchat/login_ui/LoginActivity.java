package sound.presentation.basic.com.erez.assistantchat.login_ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import sound.presentation.basic.com.erez.assistantchat.R;
import sound.presentation.basic.com.erez.assistantchat.connection_ui.ConnectionActivity;
import sound.presentation.basic.com.erez.assistantchat.misc.ActivityRouter;
import sound.presentation.basic.com.erez.assistantchat.misc.App;
import sound.presentation.basic.com.erez.assistantchat.misc.IModel;
import sound.presentation.basic.com.erez.assistantchat.network.IServerMediator;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText password = (EditText) findViewById(R.id.password_button);
        final EditText name = (EditText) findViewById(R.id.nameText);
        Button button = (Button) findViewById(R.id.loginButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                IModel model = App.getModel();
                model.setAssistantName(name.getText().toString());
                model.setPassword(password.getText().toString());
                App.getServerMediator().login(new IServerMediator.ILoginAuthenciation()
                {
                    @Override
                    public void onLoginSuccess()
                    {
                        ActivityRouter.changeActivity(LoginActivity.this, ConnectionActivity.class);
                    }

                    @Override
                    public void onLoginFailed()
                    {
                        Log.d("onClick", "Login failed!");
                    }
                });
            }
        });

    }
}
