package sound.presentation.basic.com.erez.assistantchat.login_ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import sound.presentation.basic.com.erez.assistantchat.R;
import sound.presentation.basic.com.erez.assistantchat.connection_ui.ConnectionActivity;
import sound.presentation.basic.com.erez.assistantchat.misc.App;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = (Button)findViewById(R.id.loginButton);
        final EditText name = (EditText)findViewById(R.id.nameText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                App.getiModel().setAssistantName(name.getText().toString());
                Intent intent = new Intent(LoginActivity.this, ConnectionActivity.class);
                startActivity(intent);
            }
        });
    }
}
