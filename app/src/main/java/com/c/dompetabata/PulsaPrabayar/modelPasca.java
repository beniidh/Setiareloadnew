package com.c.dompetabata.PulsaPrabayar;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Respon.ResponSubCategory;
import com.c.dompetabata.sharePreference.Preference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class modelPasca extends ViewModel {

    private MutableLiveData<String> iconurl;

    public void init()
    {
        iconurl = new MutableLiveData<>();
    }

    public void sendIconurl(String icon)
    {
        iconurl.setValue(icon);
    }
    public LiveData<String> getIconurl()
    {
        return iconurl;
    }

}
