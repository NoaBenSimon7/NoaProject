package com.example.noaproject.screens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.MediaStore;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.noaproject.R;
import com.example.noaproject.models.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddItem extends AppCompatActivity implements View.OnClickListener {

    private static final int GALLERY_INTENT = 1;  // הגדרת קוד הבקשה לגישה לגלריה
    private static final int CAMERA_INTENT = 0;   // הגדרת קוד הבקשה לצילום

    EditText etItemName, etItemPrice;
    Spinner spItemType, spItemSize,spItemFabric,spItemColor;
    Button btnGalleryD,btnTakePicD,btnAddItem;
    ImageView ivD;


    String imageRef;
    String dedc;
    String itemName, stPrice, type;
    int price;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_item);

        initViews();
        database= FirebaseDatabase.getInstance();
        myRef=database.getReference("Items").push();

        btnTakePicD.setOnClickListener(this);

        btnAddItem.setOnClickListener(this);
        btnGalleryD.setOnClickListener(this);

    }

    private void initViews() {
        btnAddItem=findViewById(R.id.btnAddItem);
        btnGalleryD=findViewById(R.id.btnGalleryD);
        btnTakePicD=findViewById(R.id.btnTakePicD);

        ivD=findViewById(R.id.ivD);


        spItemType=findViewById(R.id.spItemType);

        etItemName=findViewById(R.id.etItemName);
        etItemPrice=findViewById(R.id.etItemPrice);

    }

    @Override
    public void onClick(View v) {

        if(v==btnTakePicD)
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 0);

        }
        if(v==btnGalleryD) {

            Intent intent2=new Intent(Intent.ACTION_PICK);
            intent2.setType("image/*");
            startActivityForResult(intent2,GALLERY_INTENT);

        }

        if(v==btnAddItem)
    {



        type=spItemType.getSelectedItem().toString();



        itemName=etItemName.getText()+"";

        stPrice=etItemPrice.getText().toString();

        etItemPrice=Integer.parseInt(stPrice);

        if (bitmap != null) {

            //  uid ="thisisUid"; //FirebaseAuth.getInstance().getCurrentUser().getUid();

            String itemid=myRef.getKey().toString();

            Item newItem;
            newItem = new Item(itemName,type,imageRef, dedc,price);


            //  item1.setImageRef("gs://who-needed.appspot.com\n"+item1.getItemKey()+"");
            myRef.setValue(newItem);

            HandleImage.LoadImageFile(bitmap, AddItem.this, itemid);
            //startActivity(intent2);

            Intent go=new Intent(this,AdminPage.class);
            startActivity(go);

        } else {
            Toast.makeText(AddItem.this, "Please take pic!", Toast.LENGTH_SHORT).show();
        }
    }


    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0)//coming from camera
        {
            if (resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");
                ivD.setImageBitmap(bitmap);
            }
        }

        if(requestCode==GALLERY_INTENT && resultCode==RESULT_OK && data !=null) {
            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                ivD.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.menuGoStore){
            Intent go=new Intent(this,AfterLogin.class);
            startActivity(go);

        }

        if(id==R.id.menuGoPersonal){

            Intent go=new Intent(this,UserProfile.class);

            // go.putExtra("nameList",listNames);
            startActivity(go);

        }

        if(id==R.id.menuGoMyCart) {
            Intent go=new Intent(this,MyCart.class);

            startActivity(go);


        }
        if(id==R.id.menuGoAboutUs) {
            Intent go=new Intent(this,AboutUs.class);

            startActivity(go);


        }

        if(id==R.id.menuGoAfterLogin){
            Intent go=new Intent(this,AfterLogin.class);
            startActivity(go);

        }
        if(id==R.id.menuGoAllOrders){
            Intent go=new Intent(this,AllOrders.class);
            startActivity(go);

        }
        return super.onOptionsItemSelected(item);
    }

//    public boolean onOptionsItemSelected(MenuItem menuitem) {
//        int itemid = menuitem.getItemId();
//        if (itemid == R.id.menuGoStore) {
//            Intent goadmin = new Intent(AddItem.this, SearchItem.class);
//            startActivity(goadmin);
//        }
//        if (itemid == R.id.menuGoAddItem) {
//            Intent goadmin = new Intent(AddItem.this, AddItem.class);
//            startActivity(goadmin);
//        }
//        if (itemid == R.id.menuGoWishList) {
//            Intent goadmin = new Intent(AddItem.this, WishList.class);
//            startActivity(goadmin);
//        }
//        if (itemid == R.id.menuGoPersonal) {
//             Intent goadmin = new Intent(AddItem.this, UserProfile.class);
//             startActivity(goadmin);
//        }
//        if (itemid == R.id.menuGoAboutUs) {
//            Intent goadmin = new Intent(AddItem.this, AboutUs.class);
//            startActivity(goadmin);
//        }
//        if (itemid == R.id.menuGoAfterLogin) {
//            Intent goadmin = new Intent(AddItem.this, AfterLogin.class);
//            startActivity(goadmin);
//        }
//        if (itemid == R.id.menuGoAdminPage) {
//            String admin = "edenkario@gmail.com";
//            if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(admin)){
//                Intent go = new Intent(AddItem.this, AdminPage.class);
//                startActivity(go);
//            }
//            else{
//                Toast.makeText(AddItem.this,"עמוד זה למנהלים בלבד!", Toast.LENGTH_LONG).show();
//            }
//        }
//
//        return super.onOptionsItemSelected(menuitem);
//    }
//
//
//    @SuppressLint("RestrictedApi")
//    public boolean onCreateOptionsMenu (Menu menu){
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        if (menu instanceof MenuBuilder) {
//            MenuBuilder m = (MenuBuilder) menu;
//            m.setOptionalIconsVisible(true);
//        }
//        return true;
//    }







}











