package com.example.noaproject.screens;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.noaproject.R;
import com.example.noaproject.models.Item;
import com.example.noaproject.services.DatabaseService;
import com.example.noaproject.utils.ImageUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;


public class AddItem extends AppCompatActivity implements View.OnClickListener {

    /// tag for logging

    private static final String TAG = "AddItemActivity";
    
    private Spinner spItemType, spItemSize, spItemFabric, spItemColor;
    private EditText etItemName, etItemPrice, etItemDesc;
    private Button btnAddItem, btnGoGallery, btnGoCamera;
    private ImageView ivItem;
    private DatabaseService databaseService;

    String color="", size="";

    EditText etSize, etColor;






    /// Activity result launcher for selecting image from gallery
    private ActivityResultLauncher<Intent> selectImageLauncher;

    /// Activity result launcher for capturing image from camera
    private ActivityResultLauncher<Intent> captureImageLauncher;


    // One Preview Image


    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        /// set the layout for the activity
        setContentView(R.layout.activity_add_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();



        /// request permission for the camera and storage
        ImageUtil.requestPermission(this);

        /// register the activity result launcher for selecting image from gallery
        selectImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImage = result.getData().getData();
                        ivItem.setImageURI(selectedImage);
                    }
                });

        /// register the activity result launcher for capturing image from camera
        captureImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        ivItem.setImageBitmap(bitmap);
                    }
                });



        /// get the instance of the database service
        databaseService = DatabaseService.getInstance();




    }

    private void initViews() {

        /// get the views
        etItemName = findViewById(R.id.etItemName);
        etItemPrice = findViewById(R.id.etItemPrice);
        btnAddItem = findViewById(R.id.btnAddItem);
        ivItem = findViewById(R.id.ivD);
        spItemType=findViewById(R.id.spItemType);
        spItemSize=findViewById(R.id.spItemSize);
        spItemFabric=findViewById(R.id.spItemFabric);
        spItemColor=findViewById(R.id.spItemColor);
        btnGoCamera=findViewById(R.id.btnTakePicD);
        btnGoGallery=findViewById(R.id.btnGalleryD);
        etColor=findViewById(R.id.etColor);
        etSize=findViewById(R.id.etSizes);
        etItemDesc=findViewById(R.id.etItemDesc);

        btnGoGallery.setOnClickListener(this);
        btnGoCamera.setOnClickListener(this);

        /// set the on click listeners

        btnAddItem.setOnClickListener(this);

        spItemSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                size+=parent.getItemAtPosition(position)+", ";
                etSize.setText(size);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spItemColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                color+=parent.getItemAtPosition(position)+", ";
                etColor.setText(color);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    @Override
    public void onClick(View v) {

        if (v.getId() == btnAddItem.getId()) {
            Log.d(TAG, "Add item button clicked");
            addItemToDatabase();
            return;
        }
        if (v.getId() == btnGoGallery.getId()) {
            // select image from gallery
            Log.d(TAG, "Select image button clicked");
            selectImageFromGallery();
            return;
        }
        if (v.getId() == btnGoCamera.getId()) {
            // capture image from camera
            Log.d(TAG, "Capture image button clicked");
            captureImageFromCamera();
        }
    }

    /// add the item to the database
    /// @see Item
    private void addItemToDatabase() {
        /// get the values from the input fields
        String name = etItemName.getText().toString();
        String priceText = etItemPrice.getText().toString();
        String itemName = etItemName.getText().toString();
        String type = spItemType.getSelectedItem().toString();

         color = etColor.getText().toString() ;
        size = etSize.getText().toString() ;
        String fabric = spItemFabric.getSelectedItem().toString();
        String desc= etItemDesc.getText().toString();
        String  imageRef = ImageUtil.convertTo64Base(ivItem);

        /// validate the input
        /// stop if the input is not valid
      //  if (!isValid(name, priceText, imageRef)) return;

        /// convert the price to double
        double price = Double.parseDouble(priceText);

        /// generate a new id for the item
        String id = databaseService.generateItemId();

        Log.d(TAG, "Adding item to database");
        Log.d(TAG, "ID: " + id);
        Log.d(TAG, "Name: " + name);
        Log.d(TAG, "Price: " + price);
        Log.d(TAG, "Image: " + imageRef);

        /// create a new item object




            Item item = new Item(id, itemName, type, size, color, fabric, desc, imageRef, price);

        /// save the item to the database and get the result in the callback
        databaseService.createNewItem(item, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {
                Log.d(TAG, "Item added successfully");
                Toast.makeText(AddItem.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                /// clear the input fields after adding the item for the next item
                Log.d(TAG, "Clearing input fields");
                etItemName.setText("");
                etItemPrice.setText("");
                ivItem.setImageBitmap(null);
                Intent goLog=new Intent(AddItem.this, AdminPage.class);
                startActivity(goLog);





            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "Failed to add item", e);
                Toast.makeText(AddItem.this, "Failed to add item", Toast.LENGTH_LONG).show();
            }
        });
    }


    /// select image from gallery
    private void selectImageFromGallery() {
        //   Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //  selectImageLauncher.launch(intent);

        imageChooser();
    }

    /// capture image from camera
    private void captureImageFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureImageLauncher.launch(takePictureIntent);
    }


    /// validate the input
    private boolean isValid(String name, String priceText, String imageBase64) {
        if (name.isEmpty()) {
            Log.e(TAG, "Name is empty");
            etItemName.setError("Name is required");
            etItemName.requestFocus();
            return false;
        }

        if (priceText.isEmpty()) {
            Log.e(TAG, "Price is empty");
            etItemPrice.setError("Price is required");
            etItemPrice.requestFocus();
            return false;
        }

        if (imageBase64 == null) {
            Log.e(TAG, "Image is required");
            Toast.makeText(this, "Image is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    void imageChooser() {


        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);



    }
    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    ivItem.setImageURI(selectedImageUri);
                }
            }
        }
    }



}

   
