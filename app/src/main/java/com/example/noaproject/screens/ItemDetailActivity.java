package com.example.noaproject.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.noaproject.R;
import com.example.noaproject.models.Cart;
import com.example.noaproject.models.Item;
import com.example.noaproject.models.ItemCart;
import com.example.noaproject.models.User;
import com.example.noaproject.services.AuthenticationService;
import com.example.noaproject.services.DatabaseService;
import com.example.noaproject.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView itemName, itemType,   itemFabric, itemDesc, itemPrice;
    private ImageView itemImage;
    Spinner spColor, spSize;
    String color, size;
    ArrayList<String> arrColor, arrSizes;
    ArrayAdapter<String> adapterC, adapterS;
    Button  btnAddToCart;


    private Cart cart;

    private TextView totalPriceText;
    DatabaseService databaseService;
    AuthenticationService authenticationService;
    User user=null;

    String uid="";
    private int amont=1;

    ImageButton btnPlus, btnMinus;
    TextView tvAmount;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_detail);

        databaseService=DatabaseService.getInstance();


        authenticationService= AuthenticationService.getInstance();
        uid=authenticationService.getCurrentUserId();
        databaseService.getUser(uid, new DatabaseService.DatabaseCallback<User>() {
            @Override
            public void onCompleted(User object) {
                user=object;
            }

            @Override
            public void onFailed(Exception e) {

            }
        });

        fetchCartFromFirebase();


        arrColor=new ArrayList<>();
        arrSizes=new ArrayList<>();

        // Initialize views
        itemName = findViewById(R.id.item_name);
        itemType = findViewById(R.id.item_type);
        spColor = findViewById(R.id.spColorDetail);
        spSize = findViewById(R.id.spSizeDetail);
        itemFabric = findViewById(R.id.item_fabric);
        itemDesc = findViewById(R.id.item_desc);
        itemPrice = findViewById(R.id.item_price);
        itemImage = findViewById(R.id.item_image);
        tvAmount=findViewById(R.id.tvAmont);

        btnAddToCart=findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(this);

        btnMinus=findViewById(R.id.imbMinus);
        btnMinus.setOnClickListener(this);
        btnPlus=findViewById(R.id.imbPlus);

        btnPlus.setOnClickListener(this);

        // Get the item passed from the intent
      item = (Item) getIntent().getSerializableExtra("item");

        if (item != null) {

            itemImage.setImageBitmap(ImageUtil.convertFrom64base(item.getImageRef()));
//
            itemName.setText(item.getItemName());
            itemType.setText(item.getType());





            String colorString = item.getColor();
            if (colorString.contains(",")) {
                String[] colorArray = colorString.split("," );  // Split by commas

                for (String c : colorArray) {
                    arrColor.add(c.trim());  // Convert each part to an integer and add to the list
                }
            }
            else arrColor.add(colorString);

            adapterC=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrColor);
            spColor.setAdapter(adapterC);


            String sizeString = item.getSize();
            if (sizeString.contains(",")) {
                String[] sizeArray = sizeString.split(",");  // Split by commas

                for (String s : sizeArray) {
                    arrSizes.add(s.trim());  // Convert each part to an integer and add to the list
                }
            }
            arrSizes.add(sizeString);
            adapterS=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrSizes);
            spSize.setAdapter(adapterS);



            itemFabric.setText(item.getFabric());
            itemDesc.setText(item.getDesc());
            itemPrice.setText(String.valueOf(item.getPrice())+"");





            // Load image (you can use a library like Glide or Picasso to load images)
            // Glide.with(this).load(item.getImageRef()).into(itemImage);
        }
    }


    private void fetchCartFromFirebase() {

        databaseService.getCart(AuthenticationService.getInstance().getCurrentUserId(), new DatabaseService.DatabaseCallback<Cart>() {
            @Override
            public void onCompleted(Cart cart) {
                if(cart==null)
                {    ItemDetailActivity.this.cart=new Cart();}

              else   ItemDetailActivity.this.cart = cart;
            }

            @Override
            public void onFailed(Exception e) {

            }
        });

    }


    // הוספת מוצר לעגלה
    public void addItemToCart(ItemCart item) {

        if(user==null){
            return;
        }
        this.cart.addItem(item);

        Toast.makeText(ItemDetailActivity.this, "המוצר נוסף לעגלה", Toast.LENGTH_SHORT).show();


        databaseService.updateCart(this.cart, user.getId(), new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {
                updateTotalPrice();  // עדכון המחיר הכולל

            }

            @Override
            public void onFailed(Exception e) {

            }
        });

    }

    // עדכון המחיר הכולל בעגלה
    private void updateTotalPrice() {
        double totalPrice = 0;
        for (ItemCart item : this.cart.getItems()) {
            totalPrice += item.getItem().getPrice()*amont;
        }
        totalPriceText.setText("סך הכל: ₪" + totalPrice);
    }

    @Override
    public void onClick(View v) {


        if(v==btnAddToCart) {

            ItemCart itemCart=new ItemCart( item,amont);


            addItemToCart(itemCart);
        }
        if(v==btnPlus){

            amont++;
            tvAmount.setText(amont+"");


        }
        if(v==btnMinus){
            amont--;
            tvAmount.setText(amont+"");


        }

    }
}



