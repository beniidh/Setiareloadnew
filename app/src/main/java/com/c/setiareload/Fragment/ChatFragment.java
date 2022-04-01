package com.c.setiareload.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.c.setiareload.R;

public class ChatFragment extends Fragment {

    private ChatViewModel mViewModel;
    Button WaCenter,teleGramCenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chat_fragment, container, false);
        WaCenter = v.findViewById(R.id.WaCenter);
        WaCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isWhatappInstalled()){
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone="+"+6288269329166"+
                            "&text="+"hallo saya.."));
                    startActivity(i);

                }else {

                    Toast.makeText(getContext(),"Whatsapp is not installed",Toast.LENGTH_SHORT).show();

                }
            }
        });

        teleGramCenter = v.findViewById(R.id.TelegramCenter);
        teleGramCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isTelegramInstalled()) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/Cs_setia_reload"));
                    startActivity(i);

                } else {

                    Toast.makeText(getContext(), "Telegram is not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return v;
    }

    private boolean isWhatappInstalled(){

        PackageManager packageManager = getActivity().getPackageManager();
        boolean whatsappInstalled;

        try {
            packageManager.getPackageInfo("com.whatsapp",PackageManager.GET_ACTIVITIES);
            whatsappInstalled = true;
        }catch (PackageManager.NameNotFoundException e){

            whatsappInstalled = false;

        }
        return whatsappInstalled;

    }

    private boolean isTelegramInstalled(){

        PackageManager packageManager = getActivity().getPackageManager();
        boolean teleInstall;

        try {
            packageManager.getPackageInfo("org.telegram.messenger",PackageManager.GET_ACTIVITIES);
            teleInstall = true;
        }catch (PackageManager.NameNotFoundException e){

            teleInstall = false;

        }
        return teleInstall;

    }

    void intentMessageTelegram(String msg)
    {
        final String appName = "org.telegram.messenger";
        final boolean isAppInstalled = isAppAvailable(getActivity(), appName);
        if (isAppInstalled)
        {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            myIntent.setPackage(appName);
            myIntent.putExtra(Intent.EXTRA_TEXT, msg);//
            startActivity(Intent.createChooser(myIntent, "Share with"));
        }
        else
        {
            Toast.makeText(getActivity(), "Telegram not Installed", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isAppAvailable(Context context, String appName)
    {
        PackageManager pm = context.getPackageManager();
        try
        {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }

}