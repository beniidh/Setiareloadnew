package com.c.dompetabata.Notifikasi;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.c.dompetabata.R;

public class FragmentPesan extends Fragment {

    private FragmentPesanViewModel mViewModel;
    LinearLayout LinearFklik;

    public static FragmentPesan newInstance() {
        return new FragmentPesan();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_pesan_fragment, container, false);


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentPesanViewModel.class);
        // TODO: Use the ViewModel
    }



}