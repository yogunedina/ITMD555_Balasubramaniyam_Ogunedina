package com.arthi.moviebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PaymentsActivity extends AppCompatActivity {


        final Context context = this;
        @Override
        protected void onCreate (Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_payments);
            Intent intent = getIntent();
            String amount = intent.getStringExtra("amount");
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.activity_user_information);
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
            final EditText namecard = (EditText) dialog.findViewById(R.id.Cardname);
            final EditText creditcard = (EditText) dialog.findViewById(R.id.creditcard);
            final EditText securitycode = (EditText) dialog.findViewById(R.id.SecurityCode);
            final EditText month = (EditText) dialog.findViewById(R.id.month);
            final EditText year = (EditText) dialog.findViewById(R.id.year);
            Button order = (Button) dialog.findViewById(R.id.Submitorder);
            order.setText("Complete Order ( Total " + amount + ")");
            // if button is clicked, close the custom dialog
            order.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if ((namecard.getText().toString().isEmpty()) || (creditcard.getText().toString().isEmpty()) || (securitycode.getText().toString().isEmpty()) || (month.getText().toString().isEmpty())) {
                        Toast.makeText(getApplicationContext(), "Please insert the data", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Intent intent;
                        intent = new Intent(com.arthi.moviebooking.PaymentsActivity.this, UserInformation.class);
                        // once confirmation page is complete, switch MainActivity Class
                        // Confirmation starts after PaymentInformation
                        startActivity(intent);
                        dialog.dismiss();
                    }
                }
            });
            dialog.show();

        }


    }

