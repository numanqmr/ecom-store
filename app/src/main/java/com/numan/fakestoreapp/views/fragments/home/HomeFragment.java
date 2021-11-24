package com.numan.fakestoreapp.views.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.numan.fakestoreapp.R;
import com.numan.fakestoreapp.common.Constants;
import com.numan.fakestoreapp.common.adapters.ProductsAdapter;
import com.numan.fakestoreapp.common.dtos.Product;
import com.numan.fakestoreapp.common.interfaces.CustomClickListener;
import com.numan.fakestoreapp.databinding.ActivityLoginBinding;
import com.numan.fakestoreapp.databinding.FragmentHomeBinding;
import com.numan.fakestoreapp.views.activities.BaseActivity;
import com.numan.fakestoreapp.views.activities.auth.LoginActivity;
import com.numan.fakestoreapp.views.activities.home.MainActivity;
import com.numan.fakestoreapp.views.fragments.BaseFragment;

import java.util.ArrayList;


public class HomeFragment extends BaseFragment implements CustomClickListener {

    private HomeViewModel mViewModel;
    private FragmentHomeBinding mBinding;

    private ArrayList<String> mCategoriesList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mBinding.setViewModel(mViewModel);

        try {
            ((BaseActivity) getActivity()).showProgress(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mCategoriesList = new ArrayList<>();
        //fetch categories from API
        mViewModel.getAllCategories();

        initViews();
        observeViewModel();

        return mBinding.getRoot();

    }

    /**
     * view bindings.
     */
    private void initViews() {

        mBinding.spCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    ((BaseActivity) getActivity()).showProgress(getContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (position == 0) {
                    //fetch all products from API
                    mViewModel.getAllProducts();
                } else {
                    String catName = mCategoriesList.get(position);
                    mViewModel.getProductsInCategory(catName);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * Observe view model LiveData mutable variables.
     */
    private void observeViewModel() {

        mViewModel.getCategoriesList().observe(getViewLifecycleOwner(), categories -> {

            try {
                ((BaseActivity) getActivity()).hideProgress();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (categories != null) {
                categories.add(0, "All");
                mCategoriesList.addAll(categories);
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, categories);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mBinding.spCategories.setAdapter(adapter);
            }
        });

        mViewModel.getProductsList().observe(getViewLifecycleOwner(), products -> {

            try {
                ((BaseActivity) getActivity()).hideProgress();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (products != null && products.size() > 0) {
                ProductsAdapter adapter = new ProductsAdapter(getActivity().getApplicationContext(), products, this);
                mBinding.setIsListEmpty(false);
                mBinding.rvProducts.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Log.d("HOME", "adapter initialized: ");

            } else {
                mBinding.setIsListEmpty(true);
            }
        });

    }

    @Override
    public void onProductClick(Product product, int quantity) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.KEY_ARGS_PRODUCT, product);
        Navigation.findNavController(getView())
                .navigate(R.id.navigation_details, bundle);

    }
}