package com.arthi.moviebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class PaymentsActivity extends AppCompatActivity {

    MoviesContent moviesContent;
    String seats;
    ImageView imageView;
    TextView title, txtseats, total;
    Button pay;
    private static final String TAG = "PaymentsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        if (getIntent().hasExtra("data") && getIntent().hasExtra("seats")) {
            moviesContent = (MoviesContent) getIntent().getSerializableExtra("data");
            seats = getIntent().getStringExtra("seats");
            Log.d(TAG, "onCreate: " + seats);
        } else {
            finish();
        }

        imageView = findViewById(R.id.imageView);
        txtseats = findViewById(R.id.textViewSeats);
        total = findViewById(R.id.textViewTotal);
        pay = findViewById(R.id.buttonPay);
        title = findViewById(R.id.textViewTitle);
        title.setText(moviesContent.getOriginal_title());


        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + moviesContent.getPoster_path()).resize(500, 700).into(imageView);
        txtseats.setText(seats);
        int price = 20 * Integer.parseInt(seats);
        String res = Integer.toString(price);
        total.setText(res+"$");


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(PaymentsActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.payment_dialog_layout, null);
                builder.setTitle("Payments Method").setView(dialoglayout).setPositiveButton("Pay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        builder.create().dismiss();
                        AlertDialog.Builder alert = new AlertDialog.Builder(PaymentsActivity.this);
                        alert.setCancelable(true);
                        alert.setTitle("Payments Update").setMessage("Payment Successful").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(PaymentsActivity.this,HomePage.class));
                                finish();
                            }
                        });
                        alert.create().show();
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }


}

