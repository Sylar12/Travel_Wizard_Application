package exceptionHelper;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by vincent on 15/5/2.
 */

public class CustomException extends Exception {
    private Context context;
    private String message;
    public CustomException(String msg, Context context) {
        super(msg);
        this.message = msg;
        this.context = context;
    }

    public void handle_exception() {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }
}
