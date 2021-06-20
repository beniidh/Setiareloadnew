package com.c.dompetabata.Fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.c.dompetabata.Adapter.SliderAdapter;
import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.Load;
import com.c.dompetabata.Helper.ResponMenu;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Model.MBanner;
import com.c.dompetabata.Model.Micon;
import com.c.dompetabata.Model.SliderItem;
import com.c.dompetabata.R;
import com.c.dompetabata.Transaksi.TopupSaldoServer;
import com.c.dompetabata.homelainnya;
import com.c.dompetabata.sharePreference.Preference;
import com.c.dompetabata.topup_saldoku_activity;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    ImageView lainnya;
    TextView saldoku;
    LinearLayout linsaldoserver;
    ImageView pulsa, paketdata, pulsapasca, listrikpln, plnpascabayar, paketsmstelp, uangelektronik, pdam, vochergame;
    String icon;
    HomeViewModel homeViewModel;
    SliderView sliderView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);


        //ID Definition
        pulsa = v.findViewById(R.id.iconpulsa);
        paketdata = v.findViewById(R.id.iconpaketdata);
        pulsapasca = v.findViewById(R.id.iconpulsapascabayar);
        listrikpln = v.findViewById(R.id.iconlistrikpln);
        plnpascabayar = v.findViewById(R.id.iconplnpascabayar);
        paketsmstelp = v.findViewById(R.id.iconpaketsmsdantelpon);
        uangelektronik = v.findViewById(R.id.iconuangelektronik);
        pdam = v.findViewById(R.id.iconairpdam);
        vochergame = v.findViewById(R.id.iconvouchergame);
//
//       Load.loadiconPulsa(getContext());


        sliderView = v.findViewById(R.id.imageSlider);

//set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!



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

        linsaldoserver = v.findViewById(R.id.LinSaldoServer);
        linsaldoserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TopupSaldoServer.class);
                startActivity(intent);

            }
        });

        return v;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        ViewModelProviders.of(getActivity()).get(HomeViewModel.class).getIcon().observe(getViewLifecycleOwner(), new Observer<ArrayList<Micon>>() {
            @Override
            public void onChanged(ArrayList<Micon> arrayList) {

                for (int i = 0; i < arrayList.size(); i++) {

                    if (arrayList.get(i).getId().equals("CATID061602100000004")) {

                        Picasso.get().load(arrayList.get(i).getIcon()).into(pulsapasca);
                    } else if (arrayList.get(i).getId().equals("CATID060802100000003")) {
                        Picasso.get().load(arrayList.get(i).getIcon()).into(vochergame);
                    } else if (arrayList.get(i).getId().equals("CATID060802100000002")) {
                        Picasso.get().load(arrayList.get(i).getIcon()).into(paketdata);
                    } else if (arrayList.get(i).getId().equals("CATID052702100000001")) {
                        Picasso.get().load(arrayList.get(i).getIcon()).into(pulsa);

                    }
                }


            }
        });

        ViewModelProviders.of(getActivity()).get(HomeViewModel.class).getIconBanner().observe(getViewLifecycleOwner(), new Observer<ArrayList<MBanner>>() {
            @Override
            public void onChanged(ArrayList<MBanner> mBanners) {
                SliderAdapter adapter = new SliderAdapter(getContext());
                for (int i = 0; i < mBanners.size(); i++) {
                    adapter.addItem(new SliderItem(null,mBanners.get(i).getImage()));

                }
                sliderView.setSliderAdapter(adapter);
                sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
                sliderView.setIndicatorSelectedColor(Color.WHITE);
                sliderView.setIndicatorUnselectedColor(Color.GRAY);
                sliderView.setScrollTimeInSec(2);
                sliderView.setAutoCycle(true);
                sliderView.startAutoCycle();
            }
        });

    }


}


