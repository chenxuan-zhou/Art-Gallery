package ca.mcgill.ecse321.artgallery;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * This class manages the behaviour of the customer profile activity, which is created upon clicking
 * "Personal Info" button in dashboard, it allows user to change his name and password.
 *
 * @author bokunzhao
 */
public class CustomerProfile extends AppCompatActivity {

    private String error = "";
    int green = Color.rgb(0, 150, 0);

    /**
     * Enhanced refreshErrorMessage method, able to set error message color.
     *
     * @author bokunzhao
     * @param color the color of the error message
     */
    private void refreshErrorMessage(int color) {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);
        tvError.setTextColor(color);

    }

    /**
     * Upon creating of the activity fetch the current
     * name and email of the customer and display it on screen.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerprofile);

        TextView name = findViewById(R.id.cutsomer_name);
        name.setText(((Global) this.getApplication()).name);

        TextView email = findViewById(R.id.cutsomer_email);
        email.setText(((Global) this.getApplication()).email);

        refreshErrorMessage(Color.RED);

    }

    /**
     * Change the display name of the customer, called upon pressing Change Name button.
     *
     * @author bokunzhao
     * @param v
     */
    public void changeName(View v) {

        // fetch active accountID from current session, used in url
        String email = ((Global) getApplicationContext()).email;

        error = "";

        final TextView tvNewName = (TextView) findViewById(R.id.new_name_input_box);
        final TextView displayName = (TextView) findViewById(R.id.cutsomer_name);
        final String newName = tvNewName.getText().toString();

        RequestParams rp = new RequestParams();
        rp.add("customer", email);
        rp.add("new_name", newName);

        HttpUtils.put("change-customer-name/", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Update message
                error = "Successfully changed name";
                refreshErrorMessage(green);
                // Clear input Field
                tvNewName.setText("");
                //update displayed name to new name
                displayName.setText(newName);

                System.out.println("OnSuccess: " + displayName);

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    // have to use errorResponse even though it's "".
                    error += errorResponse.get("message").toString() + "Name cannot be empty";
                    System.out.println("OnFailure-Try: " + errorResponse.toString());
                } catch (JSONException e) {
                    error += e.getMessage();
                    System.out.println("OnFailure-Catch: " + error);
                }
                refreshErrorMessage(Color.RED);
            }
        });
    }

    /**
     * change password, similar to changing name, only with an additional confirmation.
     *
     * @author bokunzhao
     * @param v
     */
    public void changePassword(View v) {
        String email = ((Global) getApplicationContext()).email;
        error = "";
        final TextView tvNewPassword = (TextView) findViewById(R.id.new_password_input_box);
        final TextView tvConfirmPassword = (TextView) findViewById(R.id.confirm_password_input_box);
        final String newPassword = tvNewPassword.getText().toString();
        final String confirmPassword = tvConfirmPassword.getText().toString();

        RequestParams rp = new RequestParams();
        rp.add("customer", email);
        rp.add("newPassword", newPassword);
        rp.add("confirmPassword", confirmPassword);

        HttpUtils.put("change-customer-password/", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                error = "Successfully changed password";
                refreshErrorMessage(green);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    if (newPassword == null || newPassword.equals("")) {
                        //have to throw error, even we know it's always ""
                        errorResponse.get("message").toString();
                        error += "Password cannot be empty";
                    } else {
                        error += "password does not match";
                        System.out.println(errorResponse.toString());
                    }
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage(Color.RED);
            }
        });
    }
}
