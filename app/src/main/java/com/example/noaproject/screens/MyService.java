package com.example.noaproject.screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.noaproject.R;
import com.example.noaproject.models.Item;

public class MyService extends AppCompatActivity {


//    Intent takeit;
//
//    ArrayList<Item>productArrayList=new ArrayList<>();
//
//    ArrayAdapter<Product>adapter;
//
//    ProductAdapter adapter2;
//    ListView lvProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_service);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


//        lvProduct=findViewById(R.id.lvProduct);
//
//        takeit=getIntent();
//
//        productArrayList= (ArrayList<Product>) takeit.getSerializableExtra("data");
//
//        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,productArrayList);
//
//
//
//        lvProduct.setAdapter(adapter2);
//         adapter2=new ProductAdapter(this,0,0,productArrayList);

    }
}
