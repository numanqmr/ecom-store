package com.numan.fakestoreapp.views.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.numan.fakestoreapp.R;
import com.numan.fakestoreapp.common.dtos.User;
import com.numan.fakestoreapp.databinding.FragmentProductDetailsBinding;
import com.numan.fakestoreapp.databinding.FragmentProfileBinding;
import com.numan.fakestoreapp.views.activities.BaseActivity;
import com.numan.fakestoreapp.views.activities.home.MainActivity;
import com.numan.fakestoreapp.views.fragments.cart.CartViewModel;


public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private FragmentProfileBinding mBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        mBinding.setViewModel(mViewModel);

        try {
            ((BaseActivity) getActivity()).showProgress(getActivity().getApplicationContext(), "Loading Profile...");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        mViewModel.fetchUserData();
        observeViewModel();

        return mBinding.getRoot();
    }

    private void initViews(User user) {

        mBinding.setUserData(user);

        String fullName = user.getName().getFirstname() + ' ' + user.getName().getLastname();
        mBinding.tvFullName.setText(fullName);

        String address = user.getAddress().getNumber().toString() + ", "
                + user.getAddress().getStreet() + ", "
                + user.getAddress().getCity().toUpperCase();
        mBinding.tvAddress.setText(address);
    }

    /**
     * Observe view model LiveData mutable variables.
     */
    private void observeViewModel() {

        mViewModel.getUserDataResponse().observe(getViewLifecycleOwner(), userData -> {

            try {
                ((BaseActivity) getActivity()).hideProgress();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (userData != null) {
                initViews(userData);
            }

        });
    }
}