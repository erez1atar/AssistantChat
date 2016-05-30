package sound.presentation.basic.com.erez.assistantchat.misc;

import sound.presentation.basic.com.erez.assistantchat.user.IUserData;

/**
 * Model used for saving data on memory.
 */
public class Model implements IModel
{
    private String email;
    private String password;
    private String id;
    private IUserData userData;

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Override
    public void setID(String id)
    {
        this.id = id;
    }

    @Override
    public String getID()
    {
        return id;
    }

    @Override
    public IUserData getUserData()
    {
        return userData;
    }

    @Override
    public void setUserData(IUserData iUserData)
    {
        this.userData = iUserData;
    }

    @Override
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    @Override
    public String getPassword()
    {
        return password;
    }



}
