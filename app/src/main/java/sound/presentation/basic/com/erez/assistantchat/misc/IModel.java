package sound.presentation.basic.com.erez.assistantchat.misc;

/**
 * Interface for model to save connection data across the app components.
 */
public interface IModel
{
    void setPassword(String password);

    String getPassword();

    String getEmail();

    void setEmail(String email);

    void setID(String id);

    String getID();
}
