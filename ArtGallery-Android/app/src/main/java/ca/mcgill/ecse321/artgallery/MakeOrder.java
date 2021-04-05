package ca.mcgill.ecse321.artgallery;

import android.os.Bundle;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * A class contains text fields and buttons for Make an Order.
 * @author Lide
 */
public class MakeOrder extends AppCompatActivity {

    private String error = null;
    private String productInfo = null;
    private String delivery;
    /**
     * Below are the fields needed for making an order and will
     * be assigned a value step be step.
     */
    private String deliveryMethod = null;
    private String address = null;
    private String orderedDate = null;
    private String shippedDate = null;
    private String sellerEmail = null;
    private String productID = null;
    private String price = null;
    private String customerEmail = null;


    /**
     * Initialize elements needed for making an order
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeorder);
        customerEmail = ((Global) this.getApplication()).email;
        refreshErrorMessage();
    }


    /**
     * Search for the product that the customer wants and display it on.
     *
     * @author Lide
     * @param v: button to trigger this function
     */
    public void searchProductByID(View v){
        error = "";
        final TextView tv = findViewById(R.id.productID_name);
        HttpUtils.get("/product?id=" + tv.getText().toString(), new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                showProductInfo(response.toString());
                try{
                    productID = response.getString("id");
                    price = response.getString("price");
                    sellerEmail = response.getString("seller");
                    delivery = response.getString("supportedDelivery");
                }catch (JSONException e){
                    e.printStackTrace();
                }
                refreshErrorMessage();
                refreshDeliveryChoice();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    /**
     * Place the order for the customer and display order information below
     *
     * @auther Lide
     * @param v: the button to trigger this function
     */
    public void placeOrder(View v){
        error = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        orderedDate = format.format(new Date());
        shippedDate = format.format(new Date());
        final TextView tv = findViewById(R.id.address_name);
        address = tv.getText().toString();

        HttpUtils.post("/create-order?dm=" + deliveryMethod + "&address=" + address + "&ordered=" + orderedDate
                + "&shipped=" + shippedDate + "&price=" + price + "&product=" + productID +
                "&seller=" + sellerEmail + "&customer=" + customerEmail, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                showOrder(response.toString());
                refreshErrorMessage();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    /*
     * Choose the selected radio button
     *
     * @author Lide
     * @param view
     */
    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_mail:
                if (checked)
                    deliveryMethod = "Mail";
                break;
            case R.id.radio_pickup:
                if (checked)
                    deliveryMethod = "Pickup";
                break;
        }
    }

    /*
     * Refresh error message if any error occur
     */
    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = findViewById(R.id.order_error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    /*
     * Display product information
     *
     * @author Lide
     * @param info: product information
     */
    private void showProductInfo(String info) {
        TextView tvInfo = (TextView) findViewById(R.id.productInfo);
        tvInfo.setText(info);

        if (info == null || info.length() == 0) {
            tvInfo.setVisibility(View.GONE);
        } else {
            tvInfo.setVisibility(View.VISIBLE);
        }
    }

    /*
     * Display order information
     *
     * @author Lide
     * @param info: product information
     */
    private void showOrder(String info) {
        TextView tvInfo = findViewById(R.id.order_success);
        tvInfo.setText(info);

        if (info == null || info.length() == 0) {
            tvInfo.setVisibility(View.GONE);
        } else {
            tvInfo.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method checks only supported delivery method will be shown on the screen.
     * @author: Chen
     */
    private void refreshDeliveryChoice() {
        RadioButton mail =findViewById(R.id.radio_mail);
        RadioButton pickup =findViewById(R.id.radio_pickup);
        if(delivery.equals("Pickup")){
            mail.setVisibility(View.GONE);
        }else{
            mail.setVisibility(View.VISIBLE);
        }

        if(delivery.equals("Mail")){
            pickup.setVisibility(View.GONE);
        }else{
            pickup.setVisibility(View.VISIBLE);
        }
    }
}