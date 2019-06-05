package com.example.gasdeliveryserver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gasdeliveryserver.Common.Common;
import com.example.gasdeliveryserver.ViewHolder.OrderDetailsAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import info.hoang8f.widget.FButton;

public class OrderDetails extends AppCompatActivity {
    TextView    order_id, order_name, order_adress, order_time, order_total, order_status;
    String  order_id_value = "";
    CheckBox    prodBox;
    RecyclerView    listprod;
    RecyclerView.LayoutManager  layoutManager;

    FButton btn_packed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        order_id = (TextView)findViewById(R.id.order_id);
        order_name = (TextView)findViewById(R.id.order_name);
        order_adress = (TextView)findViewById(R.id.order_address);
        order_time = (TextView)findViewById(R.id.order_time_line);
        order_total = (TextView)findViewById(R.id.order_total);
        order_status = (TextView)findViewById(R.id.order_status);

        btn_packed = (FButton)findViewById(R.id.btn_packed);

        btn_packed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                packedOrder();
                Intent done = new Intent(OrderDetails.this, Home.class);
                startActivity(done);
            }
        });

        listprod = (RecyclerView)findViewById(R.id.lstProd);
        listprod.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listprod.setLayoutManager(layoutManager);

        if(getIntent()!=null){
            order_id_value = getIntent().getStringExtra("OrderId");
        }

        order_id.setText(order_id_value);
        order_total.setText(Common.currentRequest.getTotal());
        order_name.setText(Common.currentRequest.getName());
        order_time.setText(Common.currentRequest.getTime());
        order_status.setText(Common.convertCodeToStatus(Common.currentRequest.getStatus()));
        order_adress.setText(Common.currentRequest.getAddress());

        OrderDetailsAdapter adapter = new OrderDetailsAdapter(Common.currentRequest.getProducts());
        adapter.notifyDataSetChanged();
        listprod.setAdapter(adapter);


    }

    private void packedOrder() {

        Map<String,Object> update_status = new HashMap<>();
        update_status.put("status","1");

        FirebaseDatabase.getInstance()
                .getReference("Requests")
                .child(order_id_value)
                .updateChildren(update_status)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(OrderDetails.this, "Order PACKED", Toast.LENGTH_SHORT).show();

                    }
                });
        finish();
    }


}
