package com.c.setiareload.reseller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.R;
import com.c.setiareload.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModalApprove extends BottomSheetDialogFragment {

    Button setuju, tolak;
    private BottomSheetListener bottomSheetListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.approve, container, false);

//        String id = getArguments().getString("idapp");
        setuju = v.findViewById(R.id.setujuSaldoku);
        tolak = v.findViewById(R.id.tolakSaldoku);

        setuju.setOnClickListener(view -> {


            dismiss();

        });

        tolak.setOnClickListener(view -> {

//            mSetujuSaldo mSetujuSaldo = new mSetujuSaldo();
//            String token = "Bearer "+ Preference.getToken(getContext());
//            Api api = RetroClient.getApiServices();
//            Call<ResponSaldoReseller> call = api.ApproveSaldokuReselesser(token,);

            dismiss();

        });

        return v;
    }

    public interface BottomSheetListener {
        void onButtonClick();
    }
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        try {
//            bottomSheetListener = (BottomSheetListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
//        }
//    }
}
