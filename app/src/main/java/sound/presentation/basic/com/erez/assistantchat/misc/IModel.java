package sound.presentation.basic.com.erez.assistantchat.misc;

import sound.presentation.basic.com.erez.assistantchat.user.IUserData;

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

    IUserData getUserData();

    void setUserData(IUserData iUserData);
}
