package com.arthi.moviebooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity implements View.OnClickListener{

    StringRequest jsonObjectRequest;
    RequestQueue requestQueue;
    List<MoviesContent> data;
    String URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=d82820e04f8c9e368c7b38e2fb0caf71&language=en-US&page=1";
    RecyclerView recyclerView;
    private static final String TAG = "HomePage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        recyclerView = findViewById(R.id.recyclerview);
        data = new ArrayList<>();
        getAPIResponse();


    }


    private void getAPIResponse() {
        jsonObjectRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d(TAG, "onResponse: "+ response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++) {
                        MoviesContent content = new MoviesContent();
                        JSONObject subObject = array.getJSONObject(i);
                        content.setMovie_id(subObject.getInt("id"));
                        content.setOriginal_title(subObject.getString("original_title"));
                        content.setOverview(subObject.getString("overview"));
                        content.setPoster_path(subObject.getString("poster_path"));
                        content.setReleasedate(subObject.getString("release_date"));
                        content.setVote_average(Double.toString(subObject.getDouble("vote_average")));
                        String genres = getGenres(subObject.getJSONArray("genre_ids"));
                        content.setGenres_ids(genres);
                        data.add(content);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setGridView(data);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue = Volley.newRequestQueue(HomePage.this);
        requestQueue.add(jsonObjectRequest);
    }


    private void setGridView(List<MoviesContent> data) {
        CustomGridView adapter = new CustomGridView(HomePage.this,data);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);
    }

    private String getGenres(JSONArray genre_ids) throws JSONException {
        StringBuilder genres = new StringBuilder();
        for (int i = 0; i < genre_ids.length(); i++) {
            switch (genre_ids.getInt(i)) {
                case 28:
                    genres.append("Action, ");
                    break;
                case 12:
                    genres.append("Adventure, ");
                    break;
                case 16:
                    genres.append("Animation, ");
                    break;
                case 35:
                    genres.append("Comedy, ");
                    break;
                case 80:
                    genres.append("Crime, ");
                    break;
                case 99:
                    genres.append("Documentary, ");
                    break;
                case 18:
                    genres.append("Drama, ");
                    break;
                case 10751:
                    genres.append("Family, ");
                    break;
                case 14:
                    genres.append("Fantasy, ");
                    break;
                case 36:
                    genres.append("History, ");
                    break;
                case 27:
                    genres.append("Horror, ");
                    break;
                case 10402:
                    genres.append("Music, ");
                    break;
                case 9648:
                    genres.append("Mystery, ");
                    break;
                case 10749:
                    genres.append("Romance, ");
                    break;
                case 878:
                    genres.append("Science Fiction, ");
                    break;
                case 10770:
                    genres.append("TV movie, ");
                    break;
                case 53:
                    genres.append("Thriller, ");
                    break;
                case 10752:
                    genres.append("War, ");
                    break;
                case 37:
                    genres.append("Western, ");
                    break;
                default:
                    genres.append("Unknown, ");
                    break;
            }

        }
        return genres.toString();
    }

    @Override
    public void onClick(View view) {
        int i = recyclerView.getChildLayoutPosition(view);
        MoviesContent moviesContent = data.get(i);
        startActivity(new Intent(HomePage.this,BookingPage.class).putExtra("movie",moviesContent));
    }
}
