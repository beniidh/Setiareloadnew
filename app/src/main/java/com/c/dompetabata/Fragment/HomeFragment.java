package com.c.dompetabata.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.c.dompetabata.R;
import com.c.dompetabata.homelainnya;
import com.c.dompetabata.topup_saldoku_activity;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    ImageView lainnya;
    TextView saldoku;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);


        lainnya = v.findViewById(R.id.lainnya);
        lainnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), homelainnya.class);
                startActivity(intent);
            }
        });

        saldoku = v.findViewById(R.id.saldoku);
        saldoku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), topup_saldoku_activity.class);
                startActivity(intent);
            }
        });

        return v;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }
}


