package com.numan.fakestoreapp.views.fragments.cart;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.numan.fakestoreapp.R;
import com.numan.fakestoreapp.common.DateHelper;
import com.numan.fakestoreapp.common.adapters.CartAdapter;
import com.numan.fakestoreapp.common.dtos.CartItem;
import com.numan.fakestoreapp.common.dtos.Product;
import com.numan.fakestoreapp.common.interfaces.OnQuantityUpdateListener;
import com.numan.fakestoreapp.databinding.FragmentCartBinding;
import com.numan.fakestoreapp.views.activities.BaseActivity;
import com.numan.fakestoreapp.views.activities.home.MainActivity;
import com.numan.fakestoreapp.views.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class CartFragment extends BaseFragment implements OnQuantityUpdateListener {

    private final String TAG = CartFragment.class.getSimpleName();

    private CartViewModel mViewModel;
    private FragmentCartBinding mBinding;
    private ArrayList<Product> mProductsList = new ArrayList<>();
    private ArrayList<CartItem> mCartItemsList = new ArrayList<>();
    private double mTotalAmount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        mViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        mBinding.setViewModel(mViewModel);

        try {
            ((BaseActivity) getActivity()).showProgress(getActivity().getApplicationContext(), "Loading Cart..");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        //fetch all products
        mViewModel.getAllProducts();

        //fetch cart items after filtering actual products..
        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewModel.getUserCart();
            }
        }, 1000);


        initViews();
        observeViewModel();

        return mBinding.getRoot();
    }

    /**
     * manage view bindings.
     */
    private void initViews() {

        mBinding.btnUpdateCart.setOnClickListener(v -> {

            //create update cart API payload.
            HashMap<String, Object> map = new HashMap<>();
            map.put("userId", 1);
            map.put("date", DateHelper.getCurrentDate());

            //create items for cart.
            ArrayList<CartItem> list = new ArrayList<>();
            for (Product product : mProductsList) {
                CartItem item = new CartItem();
                item.setProductId(String.valueOf(product.getId()));
                item.setQuantity(String.valueOf(product.getQuantity()));
                list.add(item);
            }
            map.put("products", list);

            try {
                ((BaseActivity) getActivity()).showProgress(getContext());
            } catch (Exception e) {
                e.printStackTrace();
            }

            mViewModel.updateCart(map);
        });

    }

    /**
     * Observe view model LiveData mutable variables.
     */
    private void observeViewModel() {

        mViewModel.getUpdateCartResponse().observe(getViewLifecycleOwner(), response -> {

            try {
                ((BaseActivity) getActivity()).hideProgress();
                if (response != null) {
                    ((MainActivity) getActivity()).showToast("Cart Updated!");
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        });

        mViewModel.getUserCartResponse().observe(getViewLifecycleOwner(), itemsList -> {

            try {
                ((BaseActivity) getActivity()).hideProgress();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (itemsList != null && itemsList.size() > 0 && mProductsList.size() > 0) {

                ArrayList<Product> filteredList = new ArrayList<>();

                for (CartItem item : itemsList) {
                    //considering the fact that data at hand has IDs sorted.
                    //so the productID would also be the position in the actual prod list.
                    int pos = Integer.parseInt(item.getProductId()) - 1;
                    Product product = mProductsList.get(pos);
                    product.setQuantity(Integer.parseInt(item.getQuantity()));
                    filteredList.add(product);
                }

                mProductsList = filteredList;
                CartAdapter adapter = new CartAdapter(getActivity().getApplicationContext(), mProductsList, this);
                mBinding.rvCart.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                calculateTotal();
                Log.d("CART", "adapter initialized: ");

            }

        });

        mViewModel.getAllProductsResponse().observe(getViewLifecycleOwner(), products -> {

            try {
                ((BaseActivity) getActivity()).hideProgress();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (products != null && products.size() > 0) {
                mProductsList = products;
            }

        });

    }

    private void initializeAdapter() {

    }

    //calculate cart total.
    private void calculateTotal() {
        // update total
        mTotalAmount = 0.0;
        for (Product prod : mProductsList) {
            mTotalAmount = mTotalAmount + (Double.valueOf(prod.getQuantity()) * prod.getPrice());
        }
        mBinding.setTotalAmount(Math.round(mTotalAmount));
        Log.i(TAG, "mTotalAmount - >  " + mTotalAmount);
    }

    @Override
    public void onUpdate(Product product, int quantity) {
        Log.i(TAG, "product -> " + product.getId() + " quantity -> " + quantity);
        calculateTotal();
    }
}