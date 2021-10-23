package com.c.setiareload.Profil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Modal.ModalKabupaten;
import com.c.setiareload.Modal.ModalKecamatan;
import com.c.setiareload.Modal.ModalKelurahan;
import com.c.setiareload.Modal.ModalKodePos;
import com.c.setiareload.Modal.ModalProvinsi;
import com.c.setiareload.R;
import com.c.setiareload.Respon.ResponEditKec;
import com.c.setiareload.Respon.ResponEditLokasi;
import com.c.setiareload.Respon.ResponEditPost;
import com.c.setiareload.Respon.ResponEditkel;
import com.c.setiareload.Respon.ResponKEditKab;
import com.c.setiareload.Respon.ResponProfil;
import com.c.setiareload.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ubah_profil extends AppCompatActivity implements ModalProvinsi.BottomSheetListener,ModalKabupaten.BottomSheetListenerKabupaten,ModalKecamatan.BottomSheetListenerKecamatan,ModalKelurahan.BottomSheetListenerKelurahan,ModalKodePos.BottomSheetListenerPost{

    EditText editnama,editnamakonter,editalamat,editnomor,editemail,editprovinsi,editkecamatan,editkelurahan,editkabupaten,editkodepos;
    Button saveedit;
    int provinsiID,kabupatenID,kecamatanID,kelurahanID,kodePOSID;

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
        saveedit = findViewById(R.id.saveedit);
        getContentProfil();

        editprovinsi.setOnClickListener(view -> {
            ModalProvinsi modalProvinsi = new ModalProvinsi();
            modalProvinsi.show(getSupportFragmentManager(), "Modalprovinsi");
        });

        editkabupaten.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            ModalKabupaten modalKabupaten = new ModalKabupaten();
            bundle.putString("provinsikey",Preference.getIDProvinsi(getApplicationContext()));
            modalKabupaten.setArguments(bundle);
            modalKabupaten.show(getSupportFragmentManager(), "Modalkabupaten");
        });

        editkecamatan.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            ModalKecamatan modalKecamatan = new ModalKecamatan();
            bundle.putString("kabupatenkey",Preference.getIDKabupaten(getApplicationContext()));
            modalKecamatan.setArguments(bundle);
            modalKecamatan.show(getSupportFragmentManager(), "Modalkecamatan");
        });

        editkelurahan.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            ModalKelurahan modalKelurahan = new ModalKelurahan();
            bundle.putString("kecamatankey",Preference.getIDKecamatan(getApplicationContext()));
            modalKelurahan.setArguments(bundle);
            modalKelurahan.show(getSupportFragmentManager(), "Modalkelurahan");

        });

        editkodepos.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            ModalKodePos modalKodePos = new ModalKodePos();
            bundle.putString("kelurahankey",Preference.getIDKelurahan(getApplicationContext()));
            modalKodePos.setArguments(bundle);
            modalKodePos.show(getSupportFragmentManager(), "Modalkelurahan");

        });

        saveedit.setOnClickListener(view -> {

            edit();

        });


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
                setProvinsiID(Integer.parseInt(response.body().getData().getProvince_id()));
                Preference.setIDProvinsi(getApplicationContext(),response.body().getData().getProvince_id());
                getKabupaten(response.body().getData().getRegencies_id());
                setKabupatenID(Integer.parseInt(response.body().getData().getRegencies_id()));
                Preference.setIDKabupaten(getApplicationContext(),response.body().getData().getRegencies_id());
                getKecamatan(response.body().getData().getDistricts_id());
                Preference.setIDKecamatan(getApplicationContext(),response.body().getData().getDistricts_id());
                getKelurahan(response.body().getData().getSub_districts_id());
                Preference.setIDKelurahan(getApplicationContext(),response.body().getData().getSub_districts_id());
                getPost(response.body().getData().getPostal_code_id());
                Preference.setIDPost(getApplicationContext(),response.body().getData().getPostal_code_id());

            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {

            }
        });
    }

    public void edit(){

        String token = Preference.getToken(getApplicationContext());
        MProfilEdit edit = new MProfilEdit(editnama.getText().toString(),editnamakonter.getText().toString(),editalamat.getText().toString(),Integer.parseInt(Preference.getIDProvinsi(getApplicationContext())),Integer.parseInt(Preference.getIDKabupaten(getApplicationContext())),
                Integer.parseInt(Preference.getIDKecamatan(getApplicationContext())),Integer.parseInt(Preference.getIDKelurahan(getApplicationContext())),Integer.parseInt(Preference.getIDPost(getApplicationContext())));
        Api api = RetroClient.getApiServices();
        Call<ResEdit> call = api.editProfil("Bearer "+token,edit);
        call.enqueue(new Callback<ResEdit>() {
            @Override
            public void onResponse(Call<ResEdit> call, Response<ResEdit> response) {

                String code = response.body().getCode();
                if(code.equals("200")){
                    StyleableToast.makeText(getApplicationContext(),"Berhasil",Toast.LENGTH_SHORT,R.style.mytoast).show();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResEdit> call, Throwable t) {

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
        setKecamatanID(Integer.parseInt(id));
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
        setKelurahanID(Integer.parseInt(id));
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
        setKodePOSID(Integer.parseInt(id));
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

    @Override
    public void onButtonClick(String name, String id) {
        editprovinsi.setText(name);
      Preference.setIDProvinsi(getApplicationContext(),id);
    }
    @Override
    public void onButtonClickKabupaten(String name, String id) {
        editkabupaten.setText(name);
        Preference.setIDKabupaten(getApplicationContext(),id);
    }
    @Override
    public void onButtonClickKecamatan(String name, String id) {
        editkecamatan.setText(name);
        Preference.setIDKecamatan(getApplicationContext(),id);
    }
    @Override
    public void onButtonClickKelurahan(String name, String id) {
        editkelurahan.setText(name);
        Preference.setIDKelurahan(getApplicationContext(),id);
    }

    @Override
    public void onButtonClickPost(String postalcode, String id) {
        editkodepos.setText(postalcode);
        Preference.setIDPost(getApplicationContext(),id);
    }

    public int getProvinsiID() {
        return provinsiID;
    }

    public void setProvinsiID(int provinsiID) {
        this.provinsiID = provinsiID;
    }

    public int getKabupatenID() {
        return kabupatenID;
    }

    public void setKabupatenID(int kabupatenID) {
        this.kabupatenID = kabupatenID;
    }

    public int getKecamatanID() {
        return kecamatanID;
    }

    public void setKecamatanID(int kecamatanID) {
        this.kecamatanID = kecamatanID;
    }

    public int getKelurahanID() {
        return kelurahanID;
    }

    public void setKelurahanID(int kelurahanID) {
        this.kelurahanID = kelurahanID;
    }

    public int getKodePOSID() {
        return kodePOSID;
    }

    public void setKodePOSID(int kodePOSID) {
        this.kodePOSID = kodePOSID;
    }
}