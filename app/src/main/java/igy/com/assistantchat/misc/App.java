package igy.com.assistantchat.misc;

import android.app.Application;

import igy.com.assistantchat.network.FirebaseMediator;
import igy.com.assistantchat.network.IServerMediator;

/**
 * Application class for accessing persistent components.
 */
public class App extends Application {
    private static IModel model;
    private static IServerMediator serverMediator;
    private static App Instance;

    public App() {
        Instance = this;
    }

    public static IModel getModel() {
        if(model == null) {
            model = new Model();
        }
        return model;
    }

    public static App getInstance() {
        return Instance;
    }

    public static IServerMediator getServerMediator() {
        if(serverMediator == null) {
            serverMediator = new FirebaseMediator();
        }
        return serverMediator;
    }
}
