package ca.mcgill.ecse321.artgallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * Represent a dash board containing buttons to redirect to other activities.
 * Can enter this activity by login customer account
 */
public class DashBoard extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TextView email = findViewById(R.id.Email);
        email.setText(((Global) getApplicationContext()).email);

    }

    /**
     * ViewProduct Button will trigger this method to start ViewProduct activity
     * @param view
     */
    public void viewProduct(View view){
        Intent intent = new Intent(this,ViewProduct.class);
        startActivity(intent);
    }

    public void viewPersonalInfo(View view){
        Intent intent = new Intent(this,CustomerProfile.class);
        startActivity(intent);
    }


    public void makeOrder(View view){
        Intent intent = new Intent(this,MakeOrder.class);
        startActivity(intent);
    }
    public void viewPromotion(View view){
        Intent intent = new Intent(this,ViewPromotion.class);
        startActivity(intent);
    }


    /**
     * A function to call /customer-logout on the backend.
     * @param view A view containing a component that calls this function.
     */
    public void logout(View view) {

        RequestParams rp = new RequestParams();
        rp.add("customer", ((Global) getApplicationContext()).email);

        final String[] error = new String[1];
        HttpUtils.put("customer-logout", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // unreachable because puts has no response upon success
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error[0] = errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error[0] = e.getMessage();
                }
                error[0] += errorResponse;
            }
        });

        if (error[0] == null || error[0].length() == 0) {
            String empty = "";
            ((Global) getApplicationContext()).setEmail(empty);
            ((Global) getApplicationContext()).setPassword(empty);
            Intent intent = new Intent(DashBoard.this, MainActivity.class);
            startActivity(intent);
        }
    }


}
