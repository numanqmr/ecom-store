package com.numan.fakestoreapp.common.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.numan.fakestoreapp.R;
import com.numan.fakestoreapp.common.GlideHelper;
import com.numan.fakestoreapp.common.dtos.Product;
import com.numan.fakestoreapp.common.interfaces.CustomClickListener;
import com.numan.fakestoreapp.common.interfaces.OnQuantityUpdateListener;
import com.numan.fakestoreapp.databinding.ItemCartBinding;
import com.numan.fakestoreapp.databinding.ItemProductBinding;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.GenericViewHolder> {

    private final Context mContext;
    private ArrayList<Product> mProductsList;
    private final OnQuantityUpdateListener mListener;

    private final String TAG = CartAdapter.class.getSimpleName();

    public CartAdapter(Context context, ArrayList<Product> list, OnQuantityUpdateListener listener) {

        this.mContext = context;
        this.mProductsList = list;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        GenericViewHolder holder;

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemCartBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_cart, parent, false);
        holder = new GenericViewHolder(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {

        Product product = mProductsList.get(holder.getAdapterPosition());

        holder.itemCard.setTotal(calculateTotal(product));

        holder.itemCard.setProduct(product);

        GlideHelper.loadImageWithGlide(mContext,
                product.getImage(),
                product.getId().toString(),
                holder.itemCard.ivProductImage);

        holder.itemCard.tvSubtract.setOnClickListener(v -> {
            int mQuantity = product.getQuantity();
            Log.i(TAG, "quantity to sub: " + mQuantity);
            if (mQuantity > 1) {
                mQuantity--;
                updateQuantity(product, mQuantity, holder);
            }
        });

        holder.itemCard.tvAdd.setOnClickListener(v -> {
            int mQuantity = product.getQuantity();
            Log.i(TAG, "quantity to add: " + mQuantity);
            if (mQuantity < 10) {
                mQuantity++;
                updateQuantity(product, mQuantity, holder);
            }
        });

    }

    private void updateQuantity(Product product, int mQuantity, CartAdapter.GenericViewHolder holder){
        product.setQuantity(mQuantity);
        holder.itemCard.setProduct(product);
        holder.itemCard.setTotal(calculateTotal(product));
        mListener.onUpdate(product, product.getQuantity());
    }

    private Double calculateTotal(Product product){
        return  Double.valueOf(product.getQuantity()) * product.getPrice();
    }

    @Override
    public int getItemCount() {
        return mProductsList.size();
    }

    class GenericViewHolder extends RecyclerView.ViewHolder {
        ItemCartBinding itemCard;

        GenericViewHolder(ItemCartBinding itemCard) {
            super(itemCard.getRoot());
            this.itemCard = itemCard;
        }

    }

}
