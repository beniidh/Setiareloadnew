package com.c.dompetabata.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.c.dompetabata.Adapter.SliderAdapter;
import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.ResponMenu;
import com.c.dompetabata.Helper.RetroClient;
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
    ImageView pulsa,paketdata,pulsapasca,listrikpln,plnpascabayar,paketsmstelp,uangelektronik,pdam,vochergame;


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

       // Method for load Icon menu
       getIconVoucherGame();

        final SliderView sliderView = v.findViewById(R.id.imageSlider);

        SliderAdapter adapter = new SliderAdapter(getContext());
        adapter.addItem(new SliderItem("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png"));
        adapter.addItem(new SliderItem("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"));
        adapter.addItem(new SliderItem("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"));

        sliderView.setSliderAdapter(adapter);
//set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(2);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();



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
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

    public void getIconVoucherGame(){

        Api api = RetroClient.getApiServices();
        Call<ResponMenu> call = api.getIconVoucherGame("Bearer "+ Preference.getToken(getContext()));
        call.enqueue(new Callback<ResponMenu>() {
            @Override
            public void onResponse(Call<ResponMenu> call, Response<ResponMenu> response) {
                String code = response.body().getCode();
                if(code.equals("200")){
                    Glide.with(getView()).load(response.body().getData().getIcon()).into(vochergame);
                }
            }

            @Override
            public void onFailure(Call<ResponMenu> call, Throwable t) {

            }
        });

    }

}


