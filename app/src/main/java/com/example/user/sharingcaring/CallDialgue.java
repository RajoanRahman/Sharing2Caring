package com.example.user.sharingcaring;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import static android.Manifest.permission.CALL_PHONE;

public class CallDialgue extends AppCompatDialogFragment {

    EditText editText;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.layout_dialogue,null);
        builder.setView(view)
                .setTitle("Contact")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(Intent.ACTION_DIAL);
                        String number=editText.getText().toString();
                        intent.setData(Uri.parse("tel:"+number));
                        startActivity(intent);

                        //if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CALL_PHONE))

                        /*if (ContextCompat.checkSelfPermission(getContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            startActivity(intent);
                        } else {
                            requestPermissions(new String[]{CALL_PHONE}, 1);
                        }*/
                    }
                });

        editText=view.findViewById(R.id.copy_number);
        return builder.create();
    }

    public interface CallDialogListener{
        void applyText(String number);
    }
}
