package com.example.noaproject.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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
import androidx.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView itemName, itemType, itemFabric, itemDesc, itemPrice;
    private ImageView itemImage;
    Spinner spColor, spSize;
    String color, size;
    ArrayList<String> arrColor, arrSizes;
    ArrayAdapter<String> adapterC, adapterS;
    Button btnAddToCart, btnBack;

    private Cart cart;

    private TextView totalPriceText;
    DatabaseService databaseService;
    AuthenticationService authenticationService;
    User user = null;

    String uid = "";
    private int amont = 1;

    Button btnPlus, btnMinus;
    TextView tvAmount;
    private Item item;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_detail);

        databaseService = DatabaseService.getInstance();
        authenticationService = AuthenticationService.getInstance();
        uid = authenticationService.getCurrentUserId();
        fetchCartFromFirebase();

        arrColor = new ArrayList<>();
        arrSizes = new ArrayList<>();

        // Initialize views
        itemName = findViewById(R.id.item_name);
        itemType = findViewById(R.id.item_type);
        spColor = findViewById(R.id.spColorDetail);
        spSize = findViewById(R.id.spSizeDetail);
        itemFabric = findViewById(R.id.item_fabric);
        itemDesc = findViewById(R.id.item_desc);
        itemPrice = findViewById(R.id.item_price);
        itemImage = findViewById(R.id.item_image);
        tvAmount = findViewById(R.id.tvAmont);
        tvAmount.setText("1");

        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnAddToCart.setOnClickListener(this);
        totalPriceText = findViewById(R.id.item_price);
        btnMinus = findViewById(R.id.imbMinus);
        btnMinus.setOnClickListener(this);
        btnPlus = findViewById(R.id.imbPlus);
        btnPlus.setOnClickListener(this);

        // Get the item passed from the intent
        item = (Item) getIntent().getSerializableExtra("item");

        if (item != null) {
            itemImage.setImageBitmap(ImageUtil.convertFrom64base(item.getImageRef()));
            itemName.setText(item.getItemName());
            itemType.setText(item.getType());

            String colorString = item.getColor();
            if (colorString.contains(",")) {
                String[] colorArray = colorString.split(",");
                for (String c : colorArray) {
                    arrColor.add(c.trim());
                }
            } else arrColor.add(colorString);

            adapterC = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrColor);
            spColor.setAdapter(adapterC);

            String sizeString = item.getSize();
            if (sizeString.contains(",")) {
                String[] sizeArray = sizeString.split(",");
                for (String s : sizeArray) {
                    arrSizes.add(s.trim());
                }
            } else {
                arrSizes.add(sizeString);
            }

            adapterS = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrSizes);
            spSize.setAdapter(adapterS);

            itemFabric.setText(item.getFabric());
            itemDesc.setText(item.getDesc());
            itemPrice.setText(String.valueOf(item.getPrice()));
        }
    }

    private void fetchCartFromFirebase() {
        databaseService.getCart(uid, new DatabaseService.DatabaseCallback<Cart>() {
            @Override
            public void onCompleted(Cart cart2) {
                cart = cart2;
                if (cart == null) {
                    cart = new Cart();
                }
            }

            @Override
            public void onFailed(Exception e) {
                cart = new Cart();
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    public void addItemToCart(ItemCart itemCart) {
        cart.addItemToCart(itemCart);

        Toast.makeText(ItemDetailActivity.this, cart.getItems().toString() + "  ", Toast.LENGTH_SHORT).show();

        databaseService.updateCart(cart, uid, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {
                updateTotalPrice();
                Toast.makeText(ItemDetailActivity.this, "המוצר נוסף לעגלה", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Exception e) {
            }
        });
    }

    private void updateTotalPrice() {
        double totalPrice = 0;
        for (ItemCart item : this.cart.getItems()) {
            totalPrice += item.getItem().getPrice() * amont;
        }
        totalPriceText.setText("סך הכל: ₪" + totalPrice);
    }

    @Override
    public void onClick(View v) {
        amont = Integer.parseInt(tvAmount.getText().toString());

        if (v == btnAddToCart) {
            ItemCart itemCart = new ItemCart(item, amont);
            addItemToCart(itemCart);
        }

        if (v == btnPlus) {
            if (amont < 3) {
                amont++;
                tvAmount.setText(String.valueOf(amont));
            }
        }

        if (v == btnMinus) {
            if (amont > 1) {
                amont--;
                tvAmount.setText(String.valueOf(amont));
            }
        }

        if (v == btnBack) {
            Intent intent = new Intent(ItemDetailActivity.this, ShowItems.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuHomePageU) {
            Intent go = new Intent(getApplicationContext(), ShowItems.class);
            startActivity(go);
        } else if (id == R.id.menuCartu) {
            Intent go = new Intent(getApplicationContext(), CartActivity.class);
            startActivity(go);
        } else if (id == R.id.menuHPersonu) {
            Intent go = new Intent(getApplicationContext(), UpdateUserActivity.class);
            startActivity(go);
        } else if (id == R.id.menuLogOutu) {
            AuthenticationService.getInstance().signOut();
            Intent go = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(go);
        }
        return true;
    }
}
