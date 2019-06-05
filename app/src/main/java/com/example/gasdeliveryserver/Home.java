package com.example.gasdeliveryserver;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.gasdeliveryserver.Common.Common;
import com.example.gasdeliveryserver.Model.Request;
import com.example.gasdeliveryserver.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    LocationRequest locationRequest;
    Location mLastLocation;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;
    FirebaseDatabase db;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //check if have permision for location and the phonecalls
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.CALL_PHONE}, Common.REQUEST_CODE);
        } else {
            //Set the location
            buildLocationRequest();
            buildLocationCallBack();

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Firebase
        db = FirebaseDatabase.getInstance();
        requests = db.getReference("Requests");

        // LOAD THE ORDERS
        recyclerView = (RecyclerView) findViewById(R.id.recycler_orders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadOrders();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void loadOrders() {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, final Request model, final int position) {
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderAddress.setText(model.getAddress());
                viewHolder.txtOrderPhone.setText(model.getPhone());
                viewHolder.txtOrderTime.setText(model.getTime());

                //ADD THE BUTTONS AND THE CHECKBOX
                viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        deleteOrder(adapter.getRef(position).getKey());
                    }
                });

                viewHolder.btnDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent orderDetail = new Intent(Home.this, OrderDetails.class);
                        Common.currentRequest = model;
                        orderDetail.putExtra("OrderId", adapter.getRef(position).getKey());
                        startActivity(orderDetail);
                    }
                });

                viewHolder.btnDirections.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Common.currentRequest = model;
                        Common.currentKey = adapter.getRef(position).getKey();
                        Intent  trackingOrder = new Intent(Home.this, TrackingOrder.class);
                        trackingOrder.putExtra("OrderId", adapter.getRef(position).getKey());
                        startActivity(trackingOrder);
                    }
                });

                viewHolder.btnShipped.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        completeOrder(adapter.getRef(position).getKey(),
                                adapter.getItem(position));
                    }
                });

                viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        updateOrderStatus(adapter.getRef(position).getKey(),
                                adapter.getItem(position), b);
                    }
                });

                if (model.getStatus().equals("0")) {
                    viewHolder.checkBox.setChecked(false);
                    viewHolder.txtOrderStatus.setTextColor(Color.parseColor("#9f0000"));
                    viewHolder.btnShipped.setTextColor(Color.parseColor("#FFFFFF"));
                    viewHolder.btnShipped.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#29434e")));

                } else if (model.getStatus().equals("1")) {
                    viewHolder.checkBox.setChecked(true);
                    viewHolder.txtOrderStatus.setTextColor(Color.parseColor("#66BB6A"));
                    viewHolder.btnShipped.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#29434e")));
                    viewHolder.btnShipped.setTextColor(Color.parseColor("#FFFFFF"));

                } else if (model.getStatus().equals("2")) {
                    viewHolder.btnShipped.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#aa5c9f")));
                    viewHolder.btnShipped.setTextColor(Color.parseColor("#000000"));
                    viewHolder.txtOrderStatus.setTextColor(Color.parseColor("#aa5c9f"));
                }
            }

        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void deleteOrder(String key) {
        requests.child(key).removeValue();
        adapter.notifyDataSetChanged();
    }

    private void updateOrderStatus(String key, Request item, boolean b) {
    if(b) {
        item.setStatus("1");
        requests.child(key).setValue(item);

    }else
        {
            item.setStatus("0");
            requests.child(key).setValue(item);
        }
    }

    private void completeOrder(String key, Request item) {
        if(!item.getStatus().equals("2")) {
            item.setStatus("2");
            requests.child(key).setValue(item);
            Toast.makeText(getBaseContext(), "The order is Shipped to "+item.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == Common.REQUEST_CODE) {
                    if (grantResults.length > 0) {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            buildLocationRequest();
                            buildLocationCallBack();
                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                        } else {
                            Toast.makeText(Home.this, "Add permission !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
            }
    }

    private void buildLocationRequest () {
            locationRequest = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setSmallestDisplacement(3);
            locationRequest.setInterval(3000);
    }

    private void buildLocationCallBack () {
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    mLastLocation = locationResult.getLastLocation();
                Toast.makeText(Home.this, new StringBuilder("").append(mLastLocation.getLatitude())
                        .append('/')
                        .append(mLastLocation.getLongitude()).toString(),Toast.LENGTH_SHORT).show();
                }
            };
    }


    protected void onStop() {
        if(fusedLocationProviderClient != null)
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        super.onStop();
    }


    @Override
        public void onBackPressed () {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.home, menu);
            return true;
        }


        @SuppressWarnings("StatementWithEmptyBody")
       // @Override
        public boolean onNavigationItemSelected (MenuItem item){
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.nav_orders) {
                // Handle the camera action
            } else if (id == R.id.nav_log_out) {
                Intent signIn = new Intent(Home.this, MainActivity.class);
                signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signIn);

            }

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }

