package com.openteam.ot.gui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.openteam.ot.R;
import com.openteam.ot.utils.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoz on 07/10/2016.
 */

public class SignUpValidationFragment extends AbstractFragment {

    private static final String STARTING_PHONE_NUMBER = "+";

    private TextView confirm;
    private ImageView deleteBtn;
    private TextView phoneNumberText;
    private List<View> buttons;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.sign_up_validation,container,false);
        getBasicActivity().getToolbarTitle().setText(getResources().getString(R.string.sign_up).toUpperCase());
        phoneNumberText = (TextView) view.findViewById(R.id.phone_number_text);
        confirm = (TextView) view.findViewById(R.id.sign_up_confirm_validation);
        deleteBtn = (ImageView) view.findViewById(R.id.delete);

        buttons = new ArrayList<>();
        buttons.add(view.findViewById(R.id.key_1));
        buttons.add(view.findViewById(R.id.key_2));
        buttons.add(view.findViewById(R.id.key_3));
        buttons.add(view.findViewById(R.id.key_4));
        buttons.add(view.findViewById(R.id.key_5));
        buttons.add(view.findViewById(R.id.key_6));
        buttons.add(view.findViewById(R.id.key_7));
        buttons.add(view.findViewById(R.id.key_8));
        buttons.add(view.findViewById(R.id.key_9));
        buttons.add(view.findViewById(R.id.key_0));


        requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PackageManager.PERMISSION_GRANTED);
        return view;
    }

    @Override
    public void initListeners() {
        phoneNumberText.setKeyListener(null);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAbsActivity().replaceFragment(new SignUpConfirmationFragment(), R.anim.trans_left_in, R.anim.trans_left_out, R.anim.trans_right_in, R.anim.trans_right_out);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence charSequence = phoneNumberText.getText();
                if(charSequence != null && charSequence.length() > 0){
                    String value = String.valueOf(charSequence);
                    phoneNumberText.setText(value.substring(0, value.length()-1));
                }
            }
        });
        View.OnClickListener listener = createKeyListener();
        for(View view : buttons){
            view.setOnClickListener(listener);
        }
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

    public View.OnClickListener createKeyListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button)v;
                CharSequence value = button.getText();
                String newValue = phoneNumberText.getText()+value.toString();
                phoneNumberText.setText(newValue);
            }
        };

    }
}
