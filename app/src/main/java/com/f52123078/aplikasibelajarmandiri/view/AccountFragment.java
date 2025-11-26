package com.f52123078.aplikasibelajarmandiri.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.f52123078.aplikasibelajarmandiri.databinding.FragmentAccountBinding;
import com.f52123078.aplikasibelajarmandiri.model.HomeModel;
import com.f52123078.aplikasibelajarmandiri.model.Resource;
import com.f52123078.aplikasibelajarmandiri.viewModel.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountFragment extends Fragment implements HomeModel.HomeDataListener {

    private FragmentAccountBinding binding;
    private HomeModel homeModel;
    private FirebaseUser currentUser;

    public AccountFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeModel = new HomeModel();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        showLoading(true);
        if (currentUser == null) {
            showLoading(false);
            Toast.makeText(getContext(), "User belum login", Toast.LENGTH_SHORT).show();
            goToLogin();
            return;
        }

        // Panggil load username, bungkus try-catch jika HomeModel memanggil Google API
        try {
            homeModel.loadUserName(currentUser.getUid(), this);
        } catch (SecurityException se) {
            showLoading(false);
            Toast.makeText(getContext(), "Layanan tidak tersedia", Toast.LENGTH_LONG).show();
        }

        binding.etAccountEmail.setText(currentUser.getEmail());

        binding.btnLogoutAccount.setOnClickListener(v -> {
            homeModel.logout();
            goToLogin();
        });
    }

    @Override
    public void onUserDataLoaded(String userName) {
        showLoading(false);
        if (binding == null) return;
        binding.etAccountName.setText(userName != null ? userName : "");
    }

    // Interface memiliki dua overload — implement keduanya
    @Override
    public void onRecentResourcesLoaded(List<Resource> resources, List<String> ids) {
        // Tidak dipakai di AccountFragment, tapi harus ada
    }


    @Override
    public void onLastAccessedLoaded(@Nullable Map<String, Object> lastAccessedData) {
        // Tidak dipakai di sini
    }

    @Override
    public void onDataLoadError(String error) {
        showLoading(false);
        if (getContext() != null) {
            Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_LONG).show();
        }
    }

    private void showLoading(boolean isLoading) {
        if (binding == null) return;
        binding.progressBarAccount.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private void goToLogin() {
        if (getActivity() == null) return;
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
