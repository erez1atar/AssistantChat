package igy.com.assistantchat.login_ui;

/**
 * Created by Tom Vizel on 29/05/2016.
 */
public interface ILoginUI
{
    String getEmail();

    String getPassword();

    void invalidEmailOrPassword();
}
