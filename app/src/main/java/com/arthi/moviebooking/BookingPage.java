package com.arthi.moviebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookingPage extends AppCompatActivity {


    StringRequest jsonObjectRequest;
    RequestQueue requestQueue;
    MoviesContent moviesContent = null;
    private static final String TAG = "BookingPage";

    Button watchTrailer, seat_decrease, seat_increase, book;
    TextView title, rating, description, no_seats;
    ImageView movie_img;
    int seats = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);

        if (getIntent().hasExtra("movie")) {
            moviesContent = (MoviesContent) getIntent().getSerializableExtra("movie");

        } else {
            finish();
        }

        assert moviesContent != null;
        getVideoTrailers(moviesContent);

        movie_img = findViewById(R.id.movieImage);
        watchTrailer = findViewById(R.id.buttonTrailer);
        title = findViewById(R.id.movieTitle);
        rating = findViewById(R.id.textViewRating);
        description = findViewById(R.id.textViewDescription);
        seat_decrease = findViewById(R.id.buttonDecreaseSeats);
        seat_increase = findViewById(R.id.buttonIncreaseSeats);
        no_seats = findViewById(R.id.textViewSeats);
        book = findViewById(R.id.buttonBookNow);

        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + moviesContent.getPoster_path()).resize(500, 700).into(movie_img);

        title.setText(moviesContent.getOriginal_title());
        rating.setText(moviesContent.getVote_average() + "/10");
        description.setText(moviesContent.getOverview());

        watchTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(moviesContent.getVideo_url()));
                startActivity(intent);
            }
        });


        seat_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int i = Integer.parseInt(no_seats.getText().toString().trim());
                seats += 1;
                String res = Integer.toString(seats);
                no_seats.setText(res);
                Log.d(TAG, "onClick: ++");
            }
        });

        seat_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int i = Integer.parseInt(no_seats.getText().toString().trim());
                seats -= 1;
                if (seats < 0) {
                    seats = 0;
                }
                Log.d(TAG, "onClick: -- ");
                String res = Integer.toString(seats);
                no_seats.setText(res);
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(seats>0){
                    startActivity(new Intent(BookingPage.this, PaymentsActivity.class)
                            .putExtra("data", moviesContent)
                            .putExtra("seats", no_seats.getText().toString().trim()));
                }else{
                    Toast.makeText(BookingPage.this, "Seats should be greater than 0", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void getVideoTrailers(final MoviesContent moviesContent) {
        String URL = "https://api.themoviedb.org/3/movie/" + moviesContent.getMovie_id() + "/videos?api_key=d82820e04f8c9e368c7b38e2fb0caf71&language=en-US";
        jsonObjectRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        if (jsonArray.getJSONObject(i).has("type")) {
                            if (jsonArray.getJSONObject(i).getString("type").equals("Trailer")) {
                                moviesContent.setVideo_url("https://www.youtube.com/watch?v=" + jsonArray.getJSONObject(i).getString("key"));
                                break;
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue = Volley.newRequestQueue(BookingPage.this);
        requestQueue.add(jsonObjectRequest);
    }
}
