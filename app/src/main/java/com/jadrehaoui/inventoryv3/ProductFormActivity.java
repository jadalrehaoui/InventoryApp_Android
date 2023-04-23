package com.jadrehaoui.inventoryv3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jadrehaoui.inventoryv3.model.Product;
import com.jadrehaoui.inventoryv3.repo.ProductRepository;

public class ProductFormActivity extends AppCompatActivity implements View.OnClickListener{
    ProductRepository repo;
    TextView title;
    Product product;
    EditText titleInput;
    EditText skuInput;
    EditText descriptionInput;
    EditText priceInput;
    EditText quantityInput;
    Button saveBtn;
    String formType;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_create);
        title = findViewById(R.id.formTitleId);
        repo = ProductRepository.getInstance(this);
        titleInput = findViewById(R.id.titleInputId);
        skuInput = findViewById(R.id.skuInputId);
        descriptionInput = findViewById(R.id.descriptionInputId);
        priceInput = findViewById(R.id.priceInputId);
        quantityInput = findViewById(R.id.quantityInputId);
        saveBtn = findViewById(R.id.saveBtnId);
        saveBtn.setOnClickListener(this);
        index = getIntent().getIntExtra("position", -1);
        product = (Product) getIntent().getSerializableExtra("product");
        formType = getIntent().getStringExtra("formType");
        Log.d("INDEX", index + " ");

        if(formType.equals("Create")) {
            title.setText("Create new product");
        } else {
            title.setText("Edit this product");
            if(product != null) {
                titleInput.setText(product.getTitle());
                skuInput.setText(product.getSku());
                descriptionInput.setText(product.getDescription());
                priceInput.setText(product.getPrice() + "");
                quantityInput.setText(product.getQuantity() + "");
            }
        }
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.saveBtnId) {
            if(formType.equals("Create")) {
                repo.createNewProduct(titleInput.getText().toString(), skuInput.getText().toString(), descriptionInput.getText().toString(), Float.parseFloat(priceInput.getText().toString()), Integer.parseInt(quantityInput.getText().toString()));
            }
            else {
                // update existing product
                repo.editExistingProduct(index, product, titleInput.getText().toString(), skuInput.getText().toString(), descriptionInput.getText().toString(), Float.parseFloat(priceInput.getText().toString()), Integer.parseInt(quantityInput.getText().toString()));
            }
            finish();
        }
    }
}