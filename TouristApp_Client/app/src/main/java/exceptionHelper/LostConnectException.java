package exceptionHelper;

import android.content.Context;
import android.widget.Toast;

import remote.Client;

/**
 * Created by vincent on 15/5/2.
 */
public class LostConnectException extends Exception {
    private Context context=null;

    public LostConnectException(String msg) {
        super(msg);
    }

    public void setContext(Context context) {
        Toast.makeText(context, "Socket Lost Connection", Toast.LENGTH_SHORT).show();
        this.context = context;
    }

    public void handle_exception() {
        Client client = Client.getInstance();
        boolean repaired = client.repairConnection(this);
        if(repaired) {
            if(context!=null) {
                Toast.makeText(context, "Socket Connection repaired", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            if(context!=null) {
                Toast.makeText(context, "Can not repair socket connection", Toast.LENGTH_SHORT).show();
            }
        }
    }
}