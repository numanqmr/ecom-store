package com.numan.fakestoreapp.views.fragments.productDetails;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.numan.fakestoreapp.R;
import com.numan.fakestoreapp.common.Constants;
import com.numan.fakestoreapp.common.DateHelper;
import com.numan.fakestoreapp.common.GlideHelper;
import com.numan.fakestoreapp.common.dtos.CartItem;
import com.numan.fakestoreapp.common.dtos.Product;
import com.numan.fakestoreapp.common.interfaces.CustomClickListener;
import com.numan.fakestoreapp.databinding.FragmentProductDetailsBinding;
import com.numan.fakestoreapp.views.activities.BaseActivity;
import com.numan.fakestoreapp.views.fragments.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class ProductDetailsFragment extends BaseFragment {

    private static final String TAG = ProductDetailsFragment.class.getSimpleName();

    private ProductDetailsViewModel mViewModel;
    private FragmentProductDetailsBinding mBinding;

    private int mQuantity = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false);
        mViewModel = new ViewModelProvider(this).get(ProductDetailsViewModel.class);
        mBinding.setViewModel(mViewModel);

        Product product = (Product) getArguments().getSerializable(Constants.KEY_ARGS_PRODUCT);
        mBinding.setProduct(product);
        mBinding.setRating(product.getRating());
        mBinding.setQuantity(mQuantity);
        Log.i(TAG, "product: " + product);

        initViews(product);

        observeViewModel();

        return mBinding.getRoot();

    }

    /**
     * view bindings.
     */
    private void initViews(Product product) {

        GlideHelper.loadImageWithGlide(requireActivity().getApplicationContext(),
                product.getImage(),
                product.getId().toString(),
                mBinding.ivImage);

        mBinding.tvSubtract.setOnClickListener(v -> {
            Log.i(TAG, "quantity to sub: " + mQuantity);
            if (mQuantity > 1) {
                mQuantity--;
                mBinding.setQuantity(mQuantity);
            }

        });

        mBinding.tvAdd.setOnClickListener(v -> {
            Log.i(TAG, "quantity to add: " + mQuantity);
            if (mQuantity < 10) {
                mQuantity++;
                mBinding.setQuantity(mQuantity);
            }
        });

        mBinding.btnAddToCart.setOnClickListener(v -> {

            //prepare data for the API add to cart.
            HashMap<String, Object> map = new HashMap<>();
            map.put("userId", 1);
            map.put("date", DateHelper.getCurrentDate());
            ArrayList<CartItem> list = new ArrayList<>();
            CartItem item = new CartItem();
            item.setProductId(String.valueOf(mQuantity));
            item.setProductId(String.valueOf(product.getId()));
            map.put("products", list);

            try {
                ((BaseActivity) getActivity()).showProgress(getContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            mViewModel.addProductToCard(map);
        });

    }

    /**
     * Observe view model LiveData mutable variables.
     */
    private void observeViewModel() {

        mViewModel.getAddToCartResult().observe(getViewLifecycleOwner(), products -> {

            try {
                ((BaseActivity) getActivity()).hideProgress();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            //Go back to products.
            Navigation.findNavController(getView()).popBackStack();


        });

    }

}