package com.jadrehaoui.inventoryv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jadrehaoui.inventoryv3.model.Product;
import com.jadrehaoui.inventoryv3.repo.ProductRepository;
import com.jadrehaoui.inventoryv3.repo.UserRepository;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;
public class ProductsActivity extends AppCompatActivity implements View.OnClickListener {

    private UserRepository userRepo;
    private ProductAdapter mProductAdapter;
    private ProductRepository productRepo;
    private ArrayList<Product> products;

    GetProductsTask getProductsTask;
    FloatingActionButton addBtn;

    TextView loadingText;

    RecyclerView recycler;
    AppCompatActivity mProductActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setContentView(R.layout.activity_products);
        products = new ArrayList<Product>();
        userRepo = UserRepository.getInstance(this);
        loadingText = findViewById(R.id.loadingTextId);
        productRepo = ProductRepository.getInstance(getApplicationContext());

        recycler = findViewById(R.id.productRecyclerId);
        RecyclerView.LayoutManager gridLayoutManager =
                new GridLayoutManager(getApplicationContext(), 1);
        recycler.setLayoutManager(gridLayoutManager);

        addBtn = findViewById(R.id.addProductBtnId);
        addBtn.setOnClickListener(this);

        getProductsTask = new GetProductsTask();
        getProductsTask.execute();
        mProductActivity = this;


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        products = productRepo.refreshProducts();
        updateUI(products);
    }

    public void updateUI(ArrayList<Product> products) {
        mProductAdapter = new ProductAdapter(products);
        recycler.setAdapter(mProductAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.account_controls, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.settingsAppbarId:
                Log.d("CLICK", "APPBAR-Settings");
                break;
            case R.id.accountMenuId:
                Log.d("CLICK", "APPBAR-Account");
                break;
            case R.id.logoutMenuId:
                Log.d("CLICK", "APPBAR-Logout");
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.addProductBtnId) {
            Intent i = new Intent(this, ProductFormActivity.class);
            i.putExtra("formType", "Create");
            startActivity(i);
        }
    }

    private class GetProductsTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            Log.d("PRE EXEC", products.size()+"");
            loadingText.setText(R.string.loading);
            loadingText.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            products = productRepo.refreshProducts();
            Log.d("PRODUCTS DO IN BACK EXEC", products.size() +"");
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            loadingText.setVisibility(View.GONE);
            updateUI(products);
        }
    }

    private class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView titleV;
        private TextView skuV;
        private TextView priceV;
        private ImageView imageV;
        private Product mProduct;

        private int mPosition;

        public ProductHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.recycler_view_items, parent, false));
            itemView.setOnClickListener(this);
            titleV = itemView.findViewById(R.id.productTextTitleId);
            imageV = itemView.findViewById(R.id.productImageId);
            skuV = itemView.findViewById(R.id.skuTextId);
            priceV = itemView.findViewById(R.id.priceTextId);
        }

        public void bind(Product product, int position) {
            mProduct = product;
            titleV.setText(product.getTitle());
            skuV.setText(product.getSku());
            priceV.setText("$"+product.getPrice());
            mPosition = position;
            Picasso.get().load(product.getImage()).into(imageV);
            try {
                InputStream is = (InputStream) new URL(product.getImage()).getContent();
                imageV.setImageBitmap(BitmapFactory.decodeStream(is));
            } catch(Exception e) {
                Log.d("EXCEPTION", e.toString());
            }
            //imageV.setImageResource();
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mProductActivity, ItemActivity.class);
            intent.putExtra("item", mProduct);
            intent.putExtra("position", mPosition);
            startActivity(intent);
        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductHolder>{
        private final ArrayList<Product> adapterProducts;

        public ProductAdapter(ArrayList<Product> products) {
            adapterProducts = products;
        }

        @NonNull
        @Override
        public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            return new ProductHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
            holder.bind(adapterProducts.get(position), position);
        }

        @Override
        public int getItemCount() {
            return adapterProducts.size();
        }
    }

}