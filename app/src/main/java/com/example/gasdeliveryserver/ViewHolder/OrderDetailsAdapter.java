package com.example.gasdeliveryserver.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import com.example.gasdeliveryserver.Model.Order;
import com.example.gasdeliveryserver.R;

import java.util.List;


class MyViewHolder extends RecyclerView.ViewHolder{
    public TextView name, price;
    CheckBox    prodBox;


    public MyViewHolder(View itemView){
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.product_name);
        price = (TextView)itemView.findViewById(R.id.product_price);
        prodBox = (CheckBox)itemView.findViewById(R.id.prod_box);
    }
}
public class OrderDetailsAdapter extends RecyclerView.Adapter<MyViewHolder>{

    List<Order> myOrder;

    public OrderDetailsAdapter(List<Order> myOrder){
        this.myOrder = myOrder;
    }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details_layout,
                parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Order order = myOrder.get(position);
        holder.name.setText(String.format("Name : %s", order.getProductName()));
        holder.price.setText(String.format("Price : %s", order.getPrice()));
        holder.prodBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return myOrder.size();
    }
}
