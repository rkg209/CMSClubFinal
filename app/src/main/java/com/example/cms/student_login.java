package com.example.cms;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import java.util.Random;

import papaya.in.sendmail.SendMail;

public class student_login extends Fragment {
    public EditText ed_email;
    public AppCompatButton btn_login,btn_otp_validate;
    public EditText ed1,ed2,ed3,ed4,ed5;
    public TextView txt_email_sent_to;

    public static final String regex = "^([_A-Za-z0-9-+]+\\.?[_A-Za-z0-9-+]+@(walchandsangli.ac.in))$";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_student_login, container, false);
        ed_email = view.findViewById(R.id.et_email);
        btn_login = view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(view1 -> {
            String email = ed_email.getText().toString();
            if(email.matches(regex))
            {
                Toast.makeText(getContext(), "Email is correct", Toast.LENGTH_SHORT).show();
                Random rnd = new Random();
                int min = 10000;
                int number = min + rnd.nextInt(89999);
                SendMail mail = new SendMail("clubmanagementsystem84@gmail.com","cms@989810",
                        email,
                        "Email Verification",
                        "Your Verification code : "+number);
                mail.execute();
                final AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
                View view2 = getLayoutInflater().inflate(R.layout.custom_dialog_box,null);
                alert.setView(view2);
                final AlertDialog alertDialog = alert.create();
                ed1 = view2.findViewById(R.id.ed_1);
                ed2 = view2.findViewById(R.id.ed_2);
                ed3 = view2.findViewById(R.id.ed_3);
                ed4 = view2.findViewById(R.id.ed_4);
                ed5 = view2.findViewById(R.id.ed_5);
                txt_email_sent_to = view2.findViewById(R.id.txt_email_sent);
                txt_email_sent_to.setText(email);

                btn_otp_validate = view2.findViewById(R.id.btn_validate);
                numberOtpMove();
                btn_otp_validate.setOnClickListener(view3 -> {
                    int int_otp = 0;
                    String otp = ed1.getText().toString()+ed2.getText().toString()
                            +ed3.getText().toString()+ed4.getText().toString()+ed5.getText().toString();
                    try {
                        int_otp = Integer.parseInt(otp);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getContext(),"OTP is not valid",Toast.LENGTH_SHORT).show();
                    }


                    if(number == int_otp)
                    {
                        Toast.makeText(getContext(),"Email Validated!",Toast.LENGTH_SHORT).show();
                        alertDialog.cancel();
                    }
                    else
                    {
                        Toast.makeText(getContext(),"OTP is not valid!!!",Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
            else
            {
                Toast.makeText(getContext(), "Email is incorrect!!!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public void numberOtpMove()
    {
        ed1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ed2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ed2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ed3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ed3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ed4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ed4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ed5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ed5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btn_otp_validate.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}