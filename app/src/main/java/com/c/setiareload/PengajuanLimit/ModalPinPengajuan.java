package com.c.setiareload.PengajuanLimit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Api.Value;
import com.c.setiareload.Helper.GpsTracker;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Helper.utils;
import com.c.setiareload.R;
import com.c.setiareload.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.muddzdev.styleabletoast.StyleableToast;
import com.oakkub.android.PinEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModalPinPengajuan extends BottomSheetDialogFragment {

    GpsTracker gpsTracker;
    PengajuanDompet pengajuanDompet;
    PinEditText pinpengajuan;
    TextView idCancelPengajuan;
    Button idPinPengajuanButton;

    public ModalPinPengajuan(PengajuanDompet pengajuanDompet) {
        this.pengajuanDompet = pengajuanDompet;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_pinpengajuan, container, false);
        getLocation();

        pinpengajuan = v.findViewById(R.id.pinpengajuan);
        idCancelPengajuan = v.findViewById(R.id.idCancelPengajuan);
        idPinPengajuanButton = v.findViewById(R.id.idPinPengajuanButton);
        idCancelPengajuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        idPinPengajuanButton.setOnClickListener(v1 -> {

            if (pinpengajuan.length() < 6) {
                StyleableToast.makeText(getContext(), "Lengkapi PIN", Toast.LENGTH_SHORT, R.style.mytoast2).show();

            } else {

                String pinn = utils.hmacSha(pinpengajuan.getText().toString());

                String jumlahpengajuan = getArguments().getString("jumlahpengajuan");
                double pengajuan = Double.valueOf(jumlahpengajuan);
                ajukanlimit(pinn, Value.getMacAddress(getContext()), getIPaddress(), getUserAgent(), gpsTracker.getLatitude(), gpsTracker.getLongitude(), pengajuan);
            }
        });


        return v;

    }

    private void ajukanlimit(String pin, String mac_address, String ip_address, String user_agent, double latitude, double longitude, double amount) {

        Api api = RetroClient.getApiServices();
        String token = "Bearer " + Preference.getToken(getContext());

        SendPengajuan pengajuan = new SendPengajuan(pin, mac_address, ip_address, user_agent, latitude, longitude, amount);

        Call<SendPengajuan> call = api.SetPayLetter(token, pengajuan);
        call.enqueue(new Callback<SendPengajuan>() {
            @Override
            public void onResponse(Call<SendPengajuan> call, Response<SendPengajuan> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    StyleableToast.makeText(getContext(), "Berhasil", Toast.LENGTH_SHORT, R.style.mytoast2).show();
                    pengajuanDompet.getPengajuanDompet();
                    pengajuanDompet.idPengajuanLimitEditText.setText("");
                    dismiss();

                } else {

                    StyleableToast.makeText(getContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast).show();
                }

            }

            @Override
            public void onFailure(Call<SendPengajuan> call, Throwable t) {
                StyleableToast.makeText(getContext(), "Periksa Koneksi", Toast.LENGTH_SHORT, R.style.mytoast).show();

            }
        });

    }

    private String getUserAgent() {

        String ua = new WebView(getContext()).getSettings().getUserAgentString();
        return ua;
    }

    private String getIPaddress() {

        String IP = utils.getIPAddress(true);
        return IP;
    }


    public void getLocation() {
        gpsTracker = new GpsTracker(getContext());
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

        } else {
            gpsTracker.showSettingsAlert();
        }
    }

}
