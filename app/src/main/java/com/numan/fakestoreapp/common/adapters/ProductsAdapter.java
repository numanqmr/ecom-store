package com.numan.fakestoreapp.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.numan.fakestoreapp.R;
import com.numan.fakestoreapp.common.GlideHelper;
import com.numan.fakestoreapp.common.dtos.Product;
import com.numan.fakestoreapp.common.interfaces.CustomClickListener;
import com.numan.fakestoreapp.databinding.ItemProductBinding;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.GenericViewHolder> {

    private final Context mContext;
    private ArrayList<Product> mProductsList;
    private final CustomClickListener mListener;

    public ProductsAdapter(Context context, ArrayList<Product> list, CustomClickListener listener) {

        this.mContext = context;
        this.mProductsList = list;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        GenericViewHolder holder;

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemProductBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_product, parent, false);
        holder = new GenericViewHolder(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {

        Product product = mProductsList.get(position);
        holder.itemCard.setProduct(product);

        GlideHelper.loadImageWithGlide(mContext,
                product.getImage(),
                product.getId().toString(),
                holder.itemCard.ivProductImage);

        holder.itemCard.rlContainer.setOnClickListener(view -> mListener.onProductClick(product, 1));

    }

    @Override
    public int getItemCount() {
        return mProductsList.size();
    }

    class GenericViewHolder extends RecyclerView.ViewHolder {
        ItemProductBinding itemCard;

        GenericViewHolder(ItemProductBinding itemCard) {
            super(itemCard.getRoot());
            this.itemCard = itemCard;
        }

    }

}
