package com.c.dompetabata.Fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.c.dompetabata.Model.MBanner;
import com.c.dompetabata.Model.Micon;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<ArrayList<MBanner>> iconbanner;
    private MutableLiveData<ArrayList<Micon>> iconarray;

    public void init()
    {
        iconbanner=new MutableLiveData<>();
        iconarray = new MutableLiveData<>();
    }

    public void sendDataIcon(ArrayList<Micon> iconn)
    {
        iconarray.postValue(iconn);
    }

    public void sendDataIconBanner(ArrayList<MBanner> mBanners)
    {
        iconbanner.postValue(mBanners);
    }
    public LiveData<ArrayList<Micon>> getIcon()
    {
        return iconarray;

    }
    public LiveData<ArrayList<MBanner>> getIconBanner()
    {
        return iconbanner;

    }


}