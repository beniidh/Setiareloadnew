package com.c.dompetabata.Profil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.R;
import com.c.dompetabata.Respon.Respon;
import com.c.dompetabata.Respon.ResponEditKec;
import com.c.dompetabata.Respon.ResponEditLokasi;
import com.c.dompetabata.Respon.ResponEditPost;
import com.c.dompetabata.Respon.ResponEditkel;
import com.c.dompetabata.Respon.ResponK;
import com.c.dompetabata.Respon.ResponKEditKab;
import com.c.dompetabata.Respon.ResponProfil;
import com.c.dompetabata.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ubah_profil extends AppCompatActivity {

    EditText editnama,editnamakonter,editalamat,editnomor,editemail,editprovinsi,editkecamatan,editkelurahan,editkabupaten,editkodepos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_profil);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Ubah Profil <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        editnama = findViewById(R.id.editnamaprofil);
        editnamakonter = findViewById(R.id.editnamakonter);
        editnomor = findViewById(R.id.editnomortelpon);
        editemail = findViewById(R.id.editemail);
        editalamat = findViewById(R.id.editalamat);
        editprovinsi = findViewById(R.id.editprovinsi);
        editkabupaten = findViewById(R.id.editkabupaten);
        editkecamatan = findViewById(R.id.editkecamatan);
        editkelurahan = findViewById(R.id.editkelurahan);
        editkodepos = findViewById(R.id.editkodepos);
        getContentProfil();


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void getContentProfil() {

        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(Call<ResponProfil> call, Response<ResponProfil> response) {
                editnama.setText(response.body().getData().getName());
                editnomor.setText(response.body().getData().getPhone());
                editnamakonter.setText(response.body().getData().getStore_name());
                editalamat.setText(response.body().getData().getAddress());
                editemail.setText(response.body().getData().getEmail());
                getProvinsi(response.body().getData().getProvince_id());
                getKabupaten(response.body().getData().getRegencies_id());
                getKecamatan(response.body().getData().getDistricts_id());
                getKelurahan(response.body().getData().getSub_districts_id());
                getPost(response.body().getData().getPostal_code_id());

            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {

            }
        });
    }

    public void getProvinsi(String id){
        long idp = Long.parseLong(id);
        Api api = RetroClient.getApiServices();
        Call<ResponEditLokasi> call = api.getProvinsiByIdd(idp);
        call.enqueue(new Callback<ResponEditLokasi>() {
            @Override
            public void onResponse(Call<ResponEditLokasi> call, Response<ResponEditLokasi> response) {

                editprovinsi.setText(response.body().getData().getName());
            }

            @Override
            public void onFailure(Call<ResponEditLokasi> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });

    }

    public void getKabupaten(String id){
        long idp = Long.parseLong(id);
        Api api = RetroClient.getApiServices();
        Call<ResponKEditKab> call = api.getKabupatenById(idp);
        call.enqueue(new Callback<ResponKEditKab>() {
            @Override
            public void onResponse(Call<ResponKEditKab> call, Response<ResponKEditKab> response) {

                editkabupaten.setText(response.body().getData().getName());
            }

            @Override
            public void onFailure(Call<ResponKEditKab> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });

    }

    public void getKecamatan(String id){
        long idp = Long.parseLong(id);
        Api api = RetroClient.getApiServices();
        Call<ResponEditKec> call = api.getKecamatanById(idp);
        call.enqueue(new Callback<ResponEditKec>() {
            @Override
            public void onResponse(Call<ResponEditKec> call, Response<ResponEditKec> response) {

                editkecamatan.setText(response.body().getData().getName());
            }

            @Override
            public void onFailure(Call<ResponEditKec> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });

    }

    public void getKelurahan(String id){
        long idp = Long.parseLong(id);
        Api api = RetroClient.getApiServices();
        Call<ResponEditkel> call = api.getKelurahanById(idp);
        call.enqueue(new Callback<ResponEditkel>() {
            @Override
            public void onResponse(Call<ResponEditkel> call, Response<ResponEditkel> response) {

                editkelurahan.setText(response.body().getData().getName());
            }

            @Override
            public void onFailure(Call<ResponEditkel> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });

    }

    public void getPost(String id){
        long idp = Long.parseLong(id);
        Api api = RetroClient.getApiServices();
        Call<ResponEditPost> call = api.getPostById(idp);
        call.enqueue(new Callback<ResponEditPost>() {
            @Override
            public void onResponse(Call<ResponEditPost> call, Response<ResponEditPost> response) {

                editkodepos.setText(response.body().getData().getPostal_code());
            }

            @Override
            public void onFailure(Call<ResponEditPost> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });

    }


}