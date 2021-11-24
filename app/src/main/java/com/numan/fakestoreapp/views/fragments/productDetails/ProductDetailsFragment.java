package com.numan.fakestoreapp.views.fragments.productDetails;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.numan.fakestoreapp.R;
import com.numan.fakestoreapp.common.adapters.ProductsAdapter;
import com.numan.fakestoreapp.common.dtos.Product;
import com.numan.fakestoreapp.common.interfaces.CustomClickListener;
import com.numan.fakestoreapp.databinding.FragmentHomeBinding;
import com.numan.fakestoreapp.databinding.FragmentProductDetailsBinding;
import com.numan.fakestoreapp.views.activities.BaseActivity;
import com.numan.fakestoreapp.views.fragments.BaseFragment;


public class ProductDetailsFragment extends BaseFragment implements CustomClickListener {

    private ProductDetailsViewModel mViewModel;
    private FragmentProductDetailsBinding mBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false);
        mViewModel = new ViewModelProvider(this).get(ProductDetailsViewModel.class);
        mBinding.setViewModel(mViewModel);

        try {
            ((BaseActivity) getActivity()).showProgress(getContext());
        }catch (Exception e){
            e.printStackTrace();
        }

        initViews();

        observeViewModel();

        return mBinding.getRoot();

    }

    /**
     * view bindings.
     */
    private void initViews(){

        //mBinding.spCategories.seton

    }

    /**
     * Observe view model LiveData mutable variables.
     */
    private void observeViewModel() {

        mViewModel.getProductsList().observe(getViewLifecycleOwner(), products -> {

            try {
                ((BaseActivity) getActivity()).hideProgress();
            }catch (NullPointerException e){
                e.printStackTrace();
            }


        });

    }

    @Override
    public void onProductClick(Product product, int quantity) {

        //TODO: open product details fragment here.

    }
}