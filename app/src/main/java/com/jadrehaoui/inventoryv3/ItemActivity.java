package com.jadrehaoui.inventoryv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jadrehaoui.inventoryv3.model.Product;
import com.jadrehaoui.inventoryv3.repo.ProductRepository;
import com.squareup.picasso.Picasso;

public class ItemActivity extends AppCompatActivity implements View.OnClickListener {
    private Product product;
    ImageView productImage;
    TextView productSKU;
    TextView productTitle;
    TextView productPrice;
    TextView productDescription;
    TextView productQty;
    int position;

    ProductRepository repo;
    FloatingActionButton editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = ProductRepository.getInstance(this);
        setContentView(R.layout.activity_item);
        product = (Product) getIntent().getSerializableExtra("item");
        position = getIntent().getIntExtra("position", -1);
        productImage = findViewById(R.id.productImageThumbnailId);
        productDescription = findViewById(R.id.itemDescriptionId);
        productPrice = findViewById(R.id.itemPriceId);
        productSKU = findViewById(R.id.itemSKUId);
        productTitle = findViewById(R.id.itemTitleId);
        productQty = findViewById(R.id.itemQtyId);
        Picasso.get().load(product.getImage()).into(productImage);
        productDescription.setText(product.getDescription());
        productSKU.setText(product.getSku());
        productTitle.setText(product.getTitle());
        productPrice.setText("$" + product.getPrice());
        productQty.setText(product.getQuantity() + " piece/s");
        editBtn = findViewById(R.id.editBtnId);
        editBtn.setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        product = repo.refreshProducts().get(position);
        productDescription.setText(product.getDescription());
        productSKU.setText(product.getSku());
        productTitle.setText(product.getTitle());
        productPrice.setText("$" + product.getPrice());
        productQty.setText(product.getQuantity() + " piece/s");
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.editBtnId) {
            Intent i = new Intent(this, ProductFormActivity.class);
            i.putExtra("formType", "Edit");
            i.putExtra("product", product);
            i.putExtra("position", position);
            startActivity(i);
        }
    }
}