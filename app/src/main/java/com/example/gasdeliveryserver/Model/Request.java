package com.example.gasdeliveryserver.Model;

import java.util.List;

public class Request {
    private String Phone;
    private String Name;
    private String Address;
    private String Time;
    private String  Status;
    //private String Date;
    private String Total;
    private List<Order> Products;
    private Double Lng;
    private Double Lat;

    public Request() {
    }

    public Request(String phone, String name, String address, String time, String total, List<Order> products,
                            Double lat, Double lng) {
        Phone = phone;
        Name = name;
        Address = address;
        Time = time;
        Total = total;
        Products = products;
        Status = "0" ;//default : 0:plced, 1:shipping, 2 shipped
        Lat=lat;
        Lng = lng;

    }

    public Double getLng() {
        return Lng;
    }

    public void setLng(Double lng) {
        Lng = lng;
    }

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public List<Order> getProducts() {
        return Products;
    }

    public void setProducts(List<Order> products) {
        Products = products;
    }
}
