package com.c.dompetabata.PulsaPrabayar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.c.dompetabata.R;
import com.squareup.picasso.Picasso;

public class PulsaPrabayar_activity extends AppCompatActivity {

    ImageView iconpulsa;

    EditText nomorbelipulsa;
    Drawable d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulsa_prabayar_activity);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Pulsa Prabayar <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);


        nomorbelipulsa = findViewById(R.id.nomorbelipulsa);
        nomorbelipulsa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(nomorbelipulsa.length() ==4){
                    String provider = nomorbelipulsa.getText().toString();

                    if(provider.equals("0829")){
                        Picasso.get().load("https://res.cloudinary.com/diagsydjq/image/upload/v1623662306/workbrench/icon_prodsubcat/icon-axis_jhmnem.png").into(iconpulsa);


                        Drawable img = getApplicationContext().getDrawable(R.drawable.iconaxis);
                        Bitmap bitmap = ((BitmapDrawable)img).getBitmap();
                        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                        nomorbelipulsa.setCompoundDrawablesWithIntrinsicBounds(null,null,d,null);

                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
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


}