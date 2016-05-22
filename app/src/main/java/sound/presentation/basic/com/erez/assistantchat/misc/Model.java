package sound.presentation.basic.com.erez.assistantchat.misc;

/**
 * Created by LENOVO on 22/05/2016.
 */
public class Model implements IModel
{
    private String assistantName;

    public void setAssistantName(String assistantName)
    {
        this.assistantName = assistantName;
    }

    public String getAssistantName()
    {
        return assistantName;
    }
}
