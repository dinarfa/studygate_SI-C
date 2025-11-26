package com.f52123078.aplikasibelajarmandiri.view; // Sesuaikan package

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

// Sesuaikan import package
import com.f52123078.aplikasibelajarmandiri.databinding.FragmentResourcesBinding;


public class ResourcesFragment extends Fragment {

    private FragmentResourcesBinding binding;

    public ResourcesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentResourcesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Nanti kita tambahkan logika untuk menampilkan RecyclerView dan filter di sini
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
