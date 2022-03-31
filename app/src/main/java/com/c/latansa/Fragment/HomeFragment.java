package com.c.latansa.Fragment;

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.latansa.Adapter.AdapterMenuUtama;
import com.c.latansa.Adapter.AdapterSubMenuSide;
import com.c.latansa.Adapter.SliderAdapter;
import com.c.latansa.Api.Api;
import com.c.latansa.Fragment.Respon.MRuningText;
import com.c.latansa.Helper.RetroClient;
import com.c.latansa.Helper.utils;
import com.c.latansa.Model.MBanner;
import com.c.latansa.Model.MSubMenu;
import com.c.latansa.Model.ModelMenuUtama;
import com.c.latansa.Model.SliderItem;
import com.c.latansa.R;
import com.c.latansa.Respon.ResponMenuUtama;
import com.c.latansa.Respon.ResponProfil;
import com.c.latansa.SaldoServer.TopupSaldoServer;
import com.c.latansa.drawer_activity;
import com.c.latansa.sharePreference.Preference;
import com.c.latansa.TopUpSaldoku.topup_saldoku_activity;
import com.muddzdev.styleabletoast.StyleableToast;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    TextView saldoku, saldoserver,marqueeText;
    LinearLayout linsaldoserver,KlikSaldoku;
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
//        getContentProfil();

        int numberOfColumns = 5;
        reymenu.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns, GridLayoutManager.VERTICAL, false));
        adapterMenuUtama = new AdapterMenuUtama(getActivity(), menuUtamas);
        reymenu.setAdapter(adapterMenuUtama);

        marqueeText = v.findViewById(R.id.marqueeText);
        marqueeText.setSelected(true);

        sliderView = v.findViewById(R.id.imageSlider);
        KlikSaldoku = v.findViewById(R.id.KlikSaldoku);

        saldoku = v.findViewById(R.id.saldoku);
        saldoserver = v.findViewById(R.id.saldoserver);

        KlikSaldoku.setOnClickListener(v1 -> {

            Intent intent = new Intent(getActivity(), topup_saldoku_activity.class);
            intent.putExtra("saldoku", saldoku.getText().toString());
            startActivity(intent);
        });

        linsaldoserver = v.findViewById(R.id.kliksaldoserver);
//        linsaldoserver.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(getActivity(), TopupSaldoServer.class);
//                intent.putExtra("saldoku", saldoku.getText().toString());
//                startActivity(intent);
//
//            }
//        });

        SwipeRefreshLayout swipelainnya = v.findViewById(R.id.swipehome);
        swipelainnya.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllmenu();
                swipelainnya.setRefreshing(false);
                ((drawer_activity)getActivity()).getContentProfil();
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        ViewModelProviders.of(getActivity()).get(HomeViewModel.class).getIconBanner().observe(getViewLifecycleOwner(), new Observer<ArrayList<MBanner>>() {
            @Override
            public void onChanged(ArrayList<MBanner> mBanners) {
                SliderAdapter adapter = new SliderAdapter(getContext());
                for (int i = 0; i < mBanners.size(); i++) {
                    adapter.addItem(new SliderItem(null, mBanners.get(i).getImage()));

                }
                sliderView.setSliderAdapter(adapter);
                sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
                sliderView.setIndicatorSelectedColor(Color.WHITE);
                sliderView.setIndicatorUnselectedColor(Color.GRAY);
                sliderView.setScrollTimeInSec(5);
                sliderView.setAutoCycle(true);
                sliderView.startAutoCycle();
            }
        });



        linsaldoserver.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), TopupSaldoServer.class);
            intent.putExtra("saldoku", saldoku.getText().toString());
            startActivity(intent);

        });

//        ViewModelProviders.of(getActivity()).get(HomeViewModel.class).getPayLater().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//
//                if (s.equals("0")) {
//                    saldoserver.setText("0");
//                    linsaldoserver.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            Intent intent = new Intent(getActivity(), AjukanLimit.class);
//                            intent.putExtra("saldoku", saldoku.getText().toString());
//                            startActivity(intent);
//
//                        }
//                    });
//
//
//                } else {
//
//
//                }
//
//            }
//        });

        ViewModelProviders.of(getActivity()).get(HomeViewModel.class).getPayyLetter().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                saldoserver.setText(utils.ConvertRP(s));

            }
        });

        ViewModelProviders.of(getActivity()).get(HomeViewModel.class).getRunning().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.isEmpty()){
                    marqueeText.setText("Selamat datang di Aplikasi Latansa Reload, maksimalkan transaksi anda");
                }else {
                    marqueeText.setText(s);
                }

            }
        });



        ViewModelProviders.of(getActivity()).get(HomeViewModel.class).getSaldoku().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                saldoku.setText(utils.ConvertRP(s));

            }
        });

    }

    public void getAllmenu() {

        Api api = RetroClient.getApiServices();
        Call<ResponMenuUtama> call = api.getAllMenu("Bearer " + Preference.getToken(getActivity()));
        call.enqueue(new Callback<ResponMenuUtama>() {
            @Override
            public void onResponse(Call<ResponMenuUtama> call, Response<ResponMenuUtama> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {

                    menuUtamas = (ArrayList<ModelMenuUtama>) response.body().getData();
//                    ModelMenuUtama menuUtama = new ModelMenuUtama("lainnya", "https://res.cloudinary.com/diagsydjq/image/upload/v1624616125/c-software/icon_homemenu/iconlainnya_ybvupx.png", "lainnya");
//                    menuUtamas.add(menuUtama);
                    adapterMenuUtama = new AdapterMenuUtama(getContext(), menuUtamas);
                    reymenu.setAdapter(adapterMenuUtama);
                }

            }

            @Override
            public void onFailure(Call<ResponMenuUtama> call, Throwable t) {

            }
        });

    }




}


