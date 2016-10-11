package com.openteam.ot.gui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.openteam.ot.R;
import com.openteam.ot.gui.activity.DrawerActivity;
import com.openteam.ot.utils.PermissionUtils;

/**
 * Created by zoz on 07/10/2016.
 */

public class SignUpValidationFragment extends AbstractFragment {

    private static final String STARTING_PHONE_NUMBER = "+";

    private TextView confirm;
    private ImageView deleteBtn;
    private TextView phoneNumberText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.sign_up_validation,container,false);

        phoneNumberText = (TextView) view.findViewById(R.id.phone_number_text);
        confirm = (TextView) view.findViewById(R.id.sign_up_confirm_validation);
        deleteBtn = (ImageView) view.findViewById(R.id.delete);
        requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PackageManager.PERMISSION_GRANTED);
        return view;
    }

    @Override
    public void initListeners() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DrawerActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumberText.setText("");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        updateWithCurrentPhoneNumber();
    }

    private void updateWithCurrentPhoneNumber(){
        if(PermissionUtils.checkPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)){
            TelephonyManager mgr = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
            String phoneNumber = mgr.getLine1Number();

            if(phoneNumber != null && phoneNumber.startsWith(STARTING_PHONE_NUMBER)){
                phoneNumberText.setText(phoneNumber);
            }
        }
    }
}
