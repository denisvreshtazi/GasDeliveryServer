![](logo.png)

Gas Delivery Vlora
# GasDeliveryServer
### HCI Project

The Business part of Gas Delivery. 


The Activities are located at: 

    /app/src/main/java/com/example/gasdeliveryserver/

*  *MainActivity.java* - The Log in Page of th worker, is checked if the user exists in the database and redirect to the Home page. The layout is loaded from *layout/activity_main.xml*. 
*  *Home.java* - The list of orders. Load the orders from the database(Firebase). Asks for permission of the location and phone call. Load each order inside a *ViewHolder*. The view of the order is loaded from *layout/order_layout.xml* inside the container *ayout/content_home.xml*. The layout for the navigation is loaded from *layout/activity_home.xml*.
* *OrderDetails.java* - The Details of the selected order. The layout is loaded from *layout/activity_order_details.xml*. The first part is is a CardView where are listed the Detils of the order and below are the single products of it listed in a RecyclerView.
* *TrackingOrder.java* - A google Maps Activity. The layout is loaded from *layout/activity_tracking_order.xml*. Are created two markers **Te buyer** and **The worker**. This activity can redirect to *Call* or to *Google Maps*

* **Model**:

    - *Order* - public class where are instantiated the proprieties of the order
    
    - *User* -  public class where are instantiated the proprieties of the User
      
    - *Request*-  public class where are instantiated the proprieties of the Request
      
 * **ViewHolder**:

     - *MyViewHolder* - contains the products that are part of the Order. Name, price and a checkBox.
    
     - *OrderDetailsAdapter* - The layout of the order details. The layout is loaded from *layout/order_details_layout.xml*
      
     - *OrderViewHolder*  
 
  * **Common**:

      *Common.java* - When a User logs in all the actions he takes are made as a commonUser.  
    
 
 
  
The xml files are located at:

    GasDeliveryServer/app/src/main/res/
  
  
the layouts are found at : 
          
     app/src/main/res/layout

the menu home drawer is found at : 
        
      app/src/main/res/layout/menu

the images and icon are at: 

     res/drawable
     
     
## Authors

* **Denis Vreshtazi**
