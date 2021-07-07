package com.c.dompetabata.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.dompetabata.Adapter.AdapterKabupaten;
import com.c.dompetabata.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Maintenance {

// create adapter template

    //view Holder
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        CheckBox chekP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameList);
            chekP = itemView.findViewById(R.id.chekProvinsi);

        }
    }

    //adapter oncreate view
//    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
//    AdapterKabupaten.ViewHolder holder = new AdapterKabupaten.ViewHolder(v);
//        return holder;

}
