package presenter;

import android.content.Context;
import android.util.Log;

import viewPackage.ViewActions;

public class Presenter {

    // Presenter has no knowledge of the view, it just knows about the ViewAction Interface.
    private ViewActions viewActions;

    public Presenter(ViewActions viewActions) {
        this.viewActions = viewActions;
    }

    public void configure() {
        Log.d("aaa","configure");

        // Update the UI using the interface action.
        viewActions.defineProgressDialog((Context) viewActions);
    }
}
