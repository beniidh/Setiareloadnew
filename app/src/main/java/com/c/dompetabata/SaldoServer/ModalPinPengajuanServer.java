package com.c.dompetabata.SaldoServer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.GpsTracker;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.PengajuanLimit.PengajuanDompet;
import com.c.dompetabata.PengajuanLimit.SendPengajuan;
import com.c.dompetabata.R;
import com.c.dompetabata.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.muddzdev.styleabletoast.StyleableToast;
import com.oakkub.android.PinEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModalPinPengajuanServer extends BottomSheetDialogFragment {

    GpsTracker gpsTracker;
    PengajuanDompet pengajuanDompet;
    PinEditText pinpengajuan;
    TextView idCancelPengajuan;
    Button idPinPengajuanButton;
    AjukanLimit ajukanLimit;

    public ModalPinPengajuanServer(AjukanLimit ajukanLimit) {
        this.ajukanLimit =ajukanLimit;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_pinpengajuanserver, container, false);
        getLocation();

        pinpengajuan = v.findViewById(R.id.pinpengajuanServer);
        idCancelPengajuan = v.findViewById(R.id.idCancelPengajuanServer);

        idCancelPengajuan.setOnClickListener(v12 -> dismiss());

        pinpengajuan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (pinpengajuan.length() == 6) {
                    String jumlahpengajuan = getArguments().getString("saldo");
                    double pengajuan = Double.valueOf(jumlahpengajuan);
                    String pinn = utils.hmacSha(pinpengajuan.getText().toString());

                    ajukanlimit(pinn, getMacAddress(), getIPaddress(), getUserAgent(), gpsTracker.getLatitude(), gpsTracker.getLongitude(), pengajuan);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

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
                    ajukanLimit.getStatusPayLatter();

//                    pengajuanDompet.idPengajuanLimitEditText.setText("");
                    dismiss();

                } else {

                    StyleableToast.makeText(getContext(), "Pin anda salah", Toast.LENGTH_SHORT, R.style.mytoast).show();
                }

            }

            @Override
            public void onFailure(Call<SendPengajuan> call, Throwable t) {
                StyleableToast.makeText(getContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();

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

    private String getMacAddress() {
        String MAC = utils.getMACAddress("wlan0");//phone if pc use eth0 if phone wlan0
        return MAC;

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
