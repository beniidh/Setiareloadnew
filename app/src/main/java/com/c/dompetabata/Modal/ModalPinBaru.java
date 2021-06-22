package com.c.dompetabata.Modal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.dompetabata.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ModalPinBaru extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_pinbaru, container, false);

        return v;
    }
}
