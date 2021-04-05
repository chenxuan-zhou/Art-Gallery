package ca.mcgill.ecse321.artgallery;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This class is mainly to display all promotions that we have currently
 */
public class ViewPromotion extends AppCompatActivity {

    private String error = null;
    private ArrayList<String> promotionID = new ArrayList<>();
    private ArrayList<String> productID = new ArrayList<>();
    private ArrayList<String> startDate = new ArrayList<>();
    private ArrayList<String> endDate = new ArrayList<>();
    RecyclerView recyclerView;

    /**
     * Reveal all the promotins and set the man view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);
        seePromotions();
        refreshErrorMessage();
    }

    /**
     * Show details of our currently promotion
     * Promotion information consissts of PrmotionID, productID,startDate and endDate
     */
    private void seePromotions(){
        //Restful call: all product
        HttpUtils.get("/promotions/", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                promotionID.clear();
                productID.clear();
                startDate.clear();
                endDate.clear();

                for (int i = 0; i < response.length(); i++) {
                    try {

                            promotionID.add(response.getJSONObject(i).getString("id"));
                            startDate.add(response.getJSONObject(i).getString("startDate"));
                            endDate.add(response.getJSONObject(i).getString("endDate"));
                            productID.add(response.getJSONObject(i).getString("productDto"));



                        initRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("ViewPromotion", e.getMessage());
                    }
                }


            }

            /**
             * Thies method is desgined for catching the failures
             * @param statusCode
             * @param headers
             * @param throwable
             * @param errorResponse
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Log.d("ViewPromotion","Fail");
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }


    /**
     * This method is designed for reloading views
     */
    private void initRecyclerView(){
        recyclerView = findViewById(R.id.viewPromotion);
        PromotionAdapter adapter=new  PromotionAdapter(this,productID,promotionID,startDate,endDate);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * This method is designed for showing up the error messages
     */
            private void refreshErrorMessage() {
                // set the error message
                TextView tvError = (TextView) findViewById(R.id.error_Promotion);
                tvError.setText(error);

                if (error == null || error.length() == 0) {
                    tvError.setVisibility(View.GONE);
                } else {
                    tvError.setVisibility(View.VISIBLE);
                }
            }
        }



