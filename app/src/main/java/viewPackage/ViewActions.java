package viewPackage;

import android.content.Context;

public interface ViewActions {
    //void updateUI(String message); // Actions to perform on View.
    void defineProgressDialog(Context context);
    void onTaskCompleted(String response);
}
