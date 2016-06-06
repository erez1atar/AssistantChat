package igy.com.assistantchat.login_ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import igy.com.assistantchat.R;
import igy.com.assistantchat.login_controller.MyLoginController;

public class LoginActivity extends AppCompatActivity implements ILoginUI
{
    private EditText password;
    private EditText email;
    private Button loginButton;
    private MyLoginController controller = new MyLoginController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.emailText);
        password = (EditText) findViewById(R.id.passwordText);
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
    public String getEmail()
    {
        return email.getText().toString();
    }

    @Override
    public String getPassword()
    {
        return password.getText().toString();
    }

    @Override
    public void invalidEmailOrPassword()
    {
        Toast.makeText(this, "Invalid Email or Password!", Toast.LENGTH_LONG).show();
    }
}
