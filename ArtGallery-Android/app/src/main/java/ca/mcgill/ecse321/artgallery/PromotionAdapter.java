package ca.mcgill.ecse321.artgallery;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder> {
    Context context;
    ArrayList<String> productID;
    ArrayList<String> promotionID;
    ArrayList<String> startDate;
    ArrayList<String> endDate;

    /**
     * Constructor
     * @param ct
     * @param productID
     * @param promotionID
     * @param startDate promotion starts
     * @param endDate promotion ends
     */

    public PromotionAdapter(Context ct, ArrayList<String>productID,ArrayList<String>promotionID,ArrayList<String>startDate,ArrayList<String>endDate){
        context = ct;
        this.productID=productID;
        this.promotionID=promotionID;
        this.startDate=startDate;
        this.endDate=endDate;
    }

    /**
     * This method sets the design of each row
     * calls the single_product pattern in layout
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public PromotionAdapter.PromotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_promotion,parent,false);
        return new PromotionAdapter.PromotionViewHolder(view);
    }


    /**
     * This method is to get specific information of our promotion
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull PromotionAdapter.PromotionViewHolder holder, int position) {
        holder.textId.setText(promotionID.get(position));
        holder.textProductID.setText(productID.get(position));
        holder.textStart.setText(startDate.get(position));
        holder.textEnd.setText(endDate.get(position));
    }

    /**
     * This method returns the number of rows
     * @return
     */
    @Override
    public int getItemCount() {
        return promotionID.size();
    }

    /**
     * this class describe an item's place within the RecyclerView.
     */
    public class PromotionViewHolder extends RecyclerView.ViewHolder {

        TextView textId, textProductID,textStart,textEnd;

        public PromotionViewHolder(@NonNull View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.promotionId);
            textProductID = itemView.findViewById(R.id.productId);
            textStart = itemView.findViewById(R.id.startDate);
            textEnd = itemView.findViewById(R.id.endDate);

        }
    }


}
