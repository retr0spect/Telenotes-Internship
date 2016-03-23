package com.intern.aditya.telenotes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aditya on 3/23/2016.
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurants;

    public RestaurantAdapter(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }


    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder restaurantViewHolder, int i) {
        Restaurant ci = restaurants.get(i);
        restaurantViewHolder.nameTV.setText(ci.getName());
        restaurantViewHolder.addressTV.setText(ci.getAddress());
        restaurantViewHolder.typeTV.setText(ci.getType());
        restaurantViewHolder.ratingTV.setText(ci.getRating());
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_row, viewGroup, false);

        return new RestaurantViewHolder(itemView);
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {

        protected TextView nameTV;
        protected TextView addressTV;
        protected TextView typeTV;
        protected TextView ratingTV;

        public RestaurantViewHolder(View v) {
            super(v);
            nameTV =  (TextView) v.findViewById(R.id.txtName);
            addressTV = (TextView)  v.findViewById(R.id.txtAddress);
            typeTV = (TextView)  v.findViewById(R.id.txtType);
            ratingTV = (TextView) v.findViewById(R.id.txtRating);
        }
    }
}