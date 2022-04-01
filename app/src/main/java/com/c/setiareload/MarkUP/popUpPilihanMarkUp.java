package com.c.setiareload.MarkUP;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.setiareload.MarkUP.markupSpesifik.MarkUpSpesifikActi;
import com.c.setiareload.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class popUpPilihanMarkUp extends BottomSheetDialogFragment {

Button GlobalpopUp,SpesifikpopUp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pilihanmarkup, container, false);

        GlobalpopUp = v.findViewById(R.id.GlobalpopUp);
        SpesifikpopUp = v.findViewById(R.id.SpesifikpopUp);

        GlobalpopUp.setOnClickListener(view -> {

            Intent intent = new Intent(getContext(),markup.class);
            startActivity(intent);
            dismiss();

        });

        SpesifikpopUp.setOnClickListener(view -> {

            Intent intent = new Intent(getContext(), MarkUpSpesifikActi.class);
            startActivity(intent);
            dismiss();

        });

        return v;

    }
}