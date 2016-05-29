package sound.presentation.basic.com.erez.assistantchat.login_ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import sound.presentation.basic.com.erez.assistantchat.R;
import sound.presentation.basic.com.erez.assistantchat.login_controller.MyLoginController;

public class LoginActivity extends AppCompatActivity implements ILoginUI
{
    private EditText password;
    private EditText name;
    private Button loginButton;
    private MyLoginController controller = new MyLoginController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        password = (EditText) findViewById(R.id.passwordText);
        name = (EditText) findViewById(R.id.nameText);
        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                controller.login();
            }
        });

    }

    @Override
    public String getName()
    {
        return name.getText().toString();
    }

    @Override
    public String getPassword()
    {
        return password.getText().toString();
    }
}
