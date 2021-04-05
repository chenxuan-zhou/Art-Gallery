package ca.mcgill.ecse321.artgallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author: Chen, Lide
 * This class represents the content of a RecyclerView
 * It requires a inner class, a constructor and 3 auto-generated methods:onCreateViewHolder,
 * onBindViewHolder,getItemCount. Forgetting any of this will lead to the failure.
 * Whenever using RecyclerView, an adapter class is always required!
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    ArrayList<String> data_Id;
    ArrayList<String> data_Status;
    ArrayList<String> data_Delivery;
    ArrayList<String> data_Price;
    ArrayList<String> data_Name;
    ArrayList<String> data_Url;

    /**
     * This is constructor to create a adapter.
     * From my understanding, adapter class is the content inside RecyclerView
     * It will match all the fields (data_Id, data_Status...) to the each item
     * inside the RecyclerView.
     * @author Chen
     * @param ct
     * @param id
     * @param status
     * @param delivery
     * @param price
     * @param name
     * @param url
     */
    public ProductAdapter(Context ct, ArrayList<String> id, ArrayList<String> status, ArrayList<String> delivery,
                          ArrayList<String> price, ArrayList<String> name, ArrayList<String> url){
        context = ct;
        data_Id = id;
        data_Status = status;
        data_Delivery = delivery;
        data_Price = price;
        data_Name = name;
        data_Url = url;
    }

    /**
     * This method indicates the sample row inside the RecyclerView
     * layout.my_product is the sample row.
     * @author Chen
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_product,parent,false);
        return new ProductViewHolder(view);
    }

    /**
     * There are many rows in the RecyclerView and this method matches Arraylists(data_id,data_name,etc..)
     * to the corresponding textView or imageView in specific position of a row
     * @author Chen, Lider
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.textId.setText(data_Id.get(position));
        holder.textName.setText(data_Name.get(position));
        holder.textPrice.setText(data_Price.get(position));
        holder.textStatus.setText(data_Status.get(position));
        holder.textDelivery.setText(data_Delivery.get(position));

//        Drawable img = LoadImageFromWebOperations(data_Url.get(position));
//        holder.imagePic.setImageDrawable(img);

        LoadImage loadImage = new LoadImage(holder.imagePic, position);
        loadImage.execute(data_Url.get(position));
    }

    /**
     * This method returns the number of rows
     * @author Chen, Lide
     * @return int
     *
     */
    @Override
    public int getItemCount() {
        return data_Id.size();
    }

    /**
     * this class describe an item view and metadata about its place within the RecyclerView.
     */
    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textId, textName, textPrice, textStatus,textDelivery;
        ImageView imagePic;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.productId);
            textName = itemView.findViewById(R.id.productName);
            textPrice = itemView.findViewById(R.id.productPrice);
            textStatus = itemView.findViewById(R.id.productStatus);
            textDelivery = itemView.findViewById(R.id.productDelivery);

            imagePic = itemView.findViewById(R.id.imageProduct);
        }
    }

    /**
     * Inner class LoadImage for downloading image from its URL
     * @author Lide
     */
    private class LoadImage extends AsyncTask<String, Void, Bitmap>{
        ImageView imageView;
        int position;
        private LoadImage(ImageView imagePic, int position){
            this.imageView = imagePic;
            this.position = position;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new java.net.URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            }catch (IOException e){
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

    /**
     * This method get an image from its URL
     * @author Lide
     * @param url
     * @return
     */
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
