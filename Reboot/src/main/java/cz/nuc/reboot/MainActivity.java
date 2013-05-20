package cz.nuc.reboot;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(this.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        this.getWindow().setAttributes(lp);

        setTitle("Reboot now?");
    }

    public void reboot(View v)
    {
        (new RebootTask()).execute();
        this.setVisible(false);
    }

    public void close(View v)
    {
        finish();
    }

    private class RebootTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {

            if (Shell.SU.available() == false)
            {
                return false;
            }

            Shell.SU.run("reboot");
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result == false)
            {
                Toast.makeText(getApplicationContext(), "No root permission. Check if you have rooted phone and SuperSU installed", Toast.LENGTH_LONG).show();
            }

            finish();
        }
    }
}
