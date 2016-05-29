package sound.presentation.basic.com.erez.assistantchat.misc;

/**
 * Model used for saving data on memory.
 */
public class Model implements IModel
{
    private String assistantName;
    private String password;

    public void setAssistantName(String assistantName)
    {
        this.assistantName = assistantName;
    }

    @Override
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getAssistantName()
    {
        return assistantName;
    }

    @Override
    public String getPassword()
    {
        return password;
    }
}
