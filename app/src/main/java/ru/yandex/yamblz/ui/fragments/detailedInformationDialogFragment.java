package ru.yandex.yamblz.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import ru.yandex.yamblz.R;

/**
 * Created by Aleksandra on 07/08/16.
 */
public class DetailedInformationDialogFragment extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_detailed, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(v);
        return builder.create();
    }
}
