package com.c.setiareload.MarkUP.markupSpesifik;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.MarkUP.ResponMarkup;
import com.c.setiareload.R;
import com.c.setiareload.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModalInputSpesifikMarkup extends BottomSheetDialogFragment {

Button markupButtonMS;
TextView produkNameMS;
EditText markupNilaiMS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.input_spesifik_mu, container, false);

        markupButtonMS = v.findViewById(R.id.markupButtonMS);
        produkNameMS = v.findViewById(R.id.produkNameMS);
        markupNilaiMS = v.findViewById(R.id.markupNilaiMS);

        produkNameMS.setText(getArguments().getString("name"));
        String id = getArguments().getString("id");
        String user_id = getArguments().getString("user_id");
        String server_code = getArguments().getString("server_code");
        String status = getArguments().getString("status");
        String sales_code = getArguments().getString("sales_code");
        String product_id = getArguments().getString("product_id");

        markupButtonMS.setOnClickListener(view -> {
            if(!markupNilaiMS.getText().toString().isEmpty()){
                setHarga(id,user_id,server_code,status,sales_code,markupNilaiMS.getText().toString(),product_id,getContext());
                dismiss();
            }else {
                Toast.makeText(getContext(),"harga tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }

        });
        return v;

    }

    private void setHarga(String id, String user_id, String server_code, String status, String sales_code
    , String jumlah, String product_id, Context context){

        String token ="Bearer "+ Preference.getToken(getContext());

        RequestBody user_idd = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody server_codee = RequestBody.create(MediaType.parse("text/plain"), server_code);
        RequestBody statuss = RequestBody.create(MediaType.parse("text/plain"), status);
        RequestBody sales_codee = RequestBody.create(MediaType.parse("text/plain"), sales_code);
        RequestBody jumlahh = RequestBody.create(MediaType.parse("text/plain"), jumlah);
        RequestBody product_idd = RequestBody.create(MediaType.parse("text/plain"), product_id);

        Api api = RetroClient.getApiServices();
        Call<ResponMarkup> call = api.markupSpesifik(token,id,user_idd,server_codee,sales_codee,product_idd,jumlahh,statuss);
        call.enqueue(new Callback<ResponMarkup>() {
            @Override
            public void onResponse(Call<ResponMarkup> call, Response<ResponMarkup> response) {
                if(response.body().getCode().equals("200"))
                    Toast.makeText(context, "Berhasil", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getContext(),response.body().getError(),Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponMarkup> call, Throwable t) {
                Toast.makeText(getContext(),"Berhasil",Toast.LENGTH_SHORT).show();
            }
        });



    }
}