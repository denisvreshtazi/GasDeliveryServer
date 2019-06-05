package com.example.gasdeliveryserver.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.gasdeliveryserver.Interface.ItemClickListener;
import com.example.gasdeliveryserver.R;

import info.hoang8f.widget.FButton;

public class OrderViewHolder  extends RecyclerView.ViewHolder {
    public TextView txtOrderId, txtOrderStatus, txtOrderAddress, txtOrderTime, txtOrderPhone;

    public FButton btnShipped, btnDetails, btnRemove, btnDirections;

    public CheckBox checkBox;


    public OrderViewHolder(View itemView) {
        super(itemView);
        txtOrderId = (TextView)itemView.findViewById(R.id.order_id);
        txtOrderStatus = (TextView)itemView.findViewById(R.id.order_status);
        txtOrderAddress = (TextView)itemView.findViewById(R.id.order_address);
        txtOrderTime = (TextView)itemView.findViewById(R.id.order_time_line);
        txtOrderPhone = (TextView)itemView.findViewById(R.id.order_phone);

        checkBox =(CheckBox)itemView.findViewById(R.id.checkBox);

        btnDetails = (FButton)itemView.findViewById(R.id.btnDetails);
        btnRemove = (FButton)itemView.findViewById(R.id.btnRemove);
        btnDirections = (FButton)itemView.findViewById(R.id.btnDirections);
        btnShipped = (FButton)itemView.findViewById(R.id.btnShipped);

    }

}
