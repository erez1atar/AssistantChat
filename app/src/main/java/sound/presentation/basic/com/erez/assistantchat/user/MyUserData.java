package sound.presentation.basic.com.erez.assistantchat.user;

/**
 * Created by LENOVO on 30/05/2016.
 */
public class MyUserData implements IUserData
{
    private String name;
    private int avatar;

    public MyUserData()
    {

    }

    public MyUserData(String name, int avatar)
    {
        this.name = name;
        this.avatar = avatar;
    }

    @Override
    public String getName()
    {
        return null;
    }

    @Override
    public int getAvatar()
    {
        return 0;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setAvatar(int avatar)
    {
        this.avatar = avatar;
    }

    @Override
    public String toString()
    {
        return "MyUserData{" + "name='" + name + '\'' + ", avatar=" + avatar + '}';
    }
}
