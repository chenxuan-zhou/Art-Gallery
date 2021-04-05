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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * @author Chen
 * This class will display all products(artworks) in one activity. To achieve this functionality
 * we need RecyclerView component and adaptor class
 */
public class ViewProduct extends AppCompatActivity {

    RecyclerView recyclerView;
    private String error = null;
    private ArrayList<String> mIDs = new ArrayList<>();
    private ArrayList<String> mStatuses = new ArrayList<>();
    private ArrayList<String> mDeliveries = new ArrayList<>();
    private ArrayList<String> mPrices = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mPictures = new ArrayList<>();

    /**
     * Start activity
     * @author Chen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewprodcut);
        initProductInfo();
        refreshErrorMessage();
    }

    /**
     * This method is to get all products from backend. The HTTP request returns a JSONArray as a
     * product list. Then we reallocate attributes to their arrayList.
     * @author Chen
     */
    private void initProductInfo(){
        //Restful call: all product
        HttpUtils.get("/products/", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){

               //clear list
                mIDs.clear();
                mStatuses.clear();
                mDeliveries.clear();
                mPrices.clear();
                mNames.clear();
                mPictures.clear();

                for(int i=0; i<response.length();i++){
                    try {
                        if(response.getJSONObject(i).getString("productStatus").equals("Selling")
                        || response.getJSONObject(i).getString("productStatus").equals("DisplayOnly")
                        ){
                            mIDs.add(response.getJSONObject(i).getString("id"));
                            mStatuses.add(response.getJSONObject(i).getString("productStatus"));
                            mDeliveries.add(response.getJSONObject(i).getString("supportedDelivery"));
                            mPrices.add(response.getJSONObject(i).getString("price"));
                            mNames.add(response.getJSONObject(i).getString("name"));

                            mPictures.add(response.getJSONObject(i).getString("picutreUrl"));
                        }
                        initRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("ViewProduct",e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Log.d("ViewProduct","Restful Get failure");
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    /**
     * This method is to initialize all items in recyclerview by calling adaptor class.
     * Adaptor class handles items inside the recyclerview
     * @author Chen
     */
    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recycler_view_product);
        ProductAdapter adapter = new ProductAdapter(this,mIDs,mStatuses,mDeliveries,mPrices,mNames,mPictures);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * This method refresh error string and make it visible on the screen
     * @author Chen
     */
    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = findViewById(R.id.error_Product);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }
}
