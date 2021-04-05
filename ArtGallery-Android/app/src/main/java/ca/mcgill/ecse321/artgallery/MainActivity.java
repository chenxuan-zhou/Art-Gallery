package ca.mcgill.ecse321.artgallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * This class represents the first activity when the app is opened
 * This activity is about login feature (customer only)
 * @author: Tracy, Chen
 */
public class MainActivity extends AppCompatActivity {

    private String error = null;

    /**
     * Default method to start activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        refreshErrorMessage();
    }

    /**
     * Default methods from project initialization
     * For initialize and menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Default methods from project initialization
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    /**
     * A function to call /customer-login on the backend. Redirects to Dashboard upon success.
     * @param view A view containing a component that calls this function.
     */
    public void login_customer(View view){

        final EditText email = (EditText) findViewById(R.id.account_email);
        final EditText password = (EditText) findViewById(R.id.account_password);

        RequestParams rp = new RequestParams();
        rp.add("customer", email.getText().toString());
        rp.add("password", password.getText().toString());

        error = "";
        HttpUtils.put("customer-login", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                ((Global) getApplicationContext()).setEmail(email.getText().toString());
                ((Global) getApplicationContext()).setPassword(password.getText().toString());
                try {
                    ((Global) getApplicationContext()).setName(response.get("name").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(MainActivity.this, DashBoard.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                error += "Wrong combination.";
                refreshErrorMessage();
            }
        });

    }

    /**
     * Refresh error message
     */
    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

}