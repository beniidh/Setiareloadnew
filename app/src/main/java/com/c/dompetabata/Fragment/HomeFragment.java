package com.c.dompetabata.Fragment;

import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.c.dompetabata.Adapter.AdapterKabupaten;
import com.c.dompetabata.Adapter.AdapterMenuUtama;
import com.c.dompetabata.Adapter.SliderAdapter;
import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Model.MBanner;
import com.c.dompetabata.Model.Micon;
import com.c.dompetabata.Model.ModelKabupaten;
import com.c.dompetabata.Model.ModelMenuUtama;
import com.c.dompetabata.Model.SliderItem;
import com.c.dompetabata.PulsaPrabayar.PulsaPrabayar_activity;
import com.c.dompetabata.R;
import com.c.dompetabata.Respon.ResponMenuUtama;
import com.c.dompetabata.Transaksi.TopupSaldoServer;
import com.c.dompetabata.homelainnya;
import com.c.dompetabata.sharePreference.Preference;
import com.c.dompetabata.topup_saldoku_activity;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    ImageView lainnya;
    TextView saldoku,saldoserver;
    LinearLayout linsaldoserver;
    ImageView pulsa, paketdata, pulsapasca, listrikpln, plnpascabayar, paketsmstelp, uangelektronik, pdam, vochergame;
    String icon;
    HomeViewModel homeViewModel;
    SliderView sliderView;
    AdapterMenuUtama adapterMenuUtama;
    RecyclerView reymenu;
    ArrayList<ModelMenuUtama> menuUtamas = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);

        reymenu = v.findViewById(R.id.ReyMenuUtama);
        getAllmenu();
        int numberOfColumns = 6;
        reymenu.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns,GridLayoutManager.VERTICAL,false));
        adapterMenuUtama = new AdapterMenuUtama(getActivity(), menuUtamas);
        reymenu.setAdapter(adapterMenuUtama);

        sliderView = v.findViewById(R.id.imageSlider);

        saldoku = v.findViewById(R.id.saldoku);
        saldoserver = v.findViewById(R.id.saldoserver);
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

//        pulsa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(getActivity(), PulsaPrabayar_activity.class);
//                startActivity(intent);
//            }
//        });

        return v;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
//        ViewModelProviders.of(getActivity()).get(HomeViewModel.class).getIcon().observe(getViewLifecycleOwner(), new Observer<ArrayList<Micon>>() {
//            @Override
//            public void onChanged(ArrayList<Micon> arrayList) {
//
//                for (int i = 0; i < arrayList.size(); i++) {
//
//                    if (arrayList.get(i).getId().equals("CATID061602100000004")) {
//
//                        Picasso.get().load(arrayList.get(i).getIcon()).into(pulsapasca);
//
//                    } else if (arrayList.get(i).getId().equals("CATID060802100000003")) {
//
//                        Picasso.get().load(arrayList.get(i).getIcon()).into(vochergame);
//                    } else if (arrayList.get(i).getId().equals("CATID060802100000002")) {
//                        Picasso.get().load(arrayList.get(i).getIcon()).into(paketdata);
//                    } else if (arrayList.get(i).getId().equals("CATID052702100000001")) {
//                        Picasso.get().load(arrayList.get(i).getIcon()).into(pulsa);
//
//                    }
//                }
//
//
//            }
//        });

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

        ViewModelProviders.of(getActivity()).get(HomeViewModel.class).getPayLater().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                String statusPaylater = s;
                if(statusPaylater.equals("0")){
                    saldoserver.setText("-");
                    linsaldoserver.setEnabled(false);

                }

            }
        });

        ViewModelProviders.of(getActivity()).get(HomeViewModel.class).getSaldoku().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                saldoku.setText(s);

            }
        });

    }

    public void getAllmenu(){

        Api api = RetroClient.getApiServices();
       Call<ResponMenuUtama>call = api.getAllMenu("Bearer "+ Preference.getToken(getActivity()));
       call.enqueue(new Callback<ResponMenuUtama>() {
           @Override
           public void onResponse(Call<ResponMenuUtama> call, Response<ResponMenuUtama> response) {
               String code = response.body().getCode();
               if(code.equals("200")){


                   menuUtamas = (ArrayList<ModelMenuUtama>) response.body().getData();

                   ArrayList<ModelMenuUtama> arrayList = new ArrayList<>();
                   int panjang =menuUtamas.size();

                   for(int i =0; i<panjang; i++){

                       if(menuUtamas.get(i).getStatus().equals("0")){
                           menuUtamas.remove(i);

                       } else {

                           arrayList.add(menuUtamas.get(i));

                       }

                   }

                   adapterMenuUtama = new AdapterMenuUtama(getContext(), arrayList);
                   reymenu.setAdapter(adapterMenuUtama);



               }

           }

           @Override
           public void onFailure(Call<ResponMenuUtama> call, Throwable t) {

           }
       });

    }


}


