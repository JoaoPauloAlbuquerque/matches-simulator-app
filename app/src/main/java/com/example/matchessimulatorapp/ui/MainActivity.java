package com.example.matchessimulatorapp.ui;

import android.animation.Animator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.matchessimulatorapp.R;
import com.example.matchessimulatorapp.data.MatchesAPI;
import com.example.matchessimulatorapp.databinding.ActivityMainBinding;
import com.example.matchessimulatorapp.domain.Match;
import com.example.matchessimulatorapp.ui.adapter.MatchesAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MatchesAPI matchesAPI;
    private MatchesAdapter matchesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupHttpClient();
        setupMatchesList();
        setupMatchesRefresh();
        setupFloatingActionButton();
    }

    private void setupHttpClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/JoaoPauloAlbuquerque/matches-simulator-api/master/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        matchesAPI = retrofit.create(MatchesAPI.class);
    }

    private void setupMatchesList(){
        this.binding.rvMatches.setHasFixedSize(true);
        this.binding.rvMatches.setLayoutManager(new LinearLayoutManager(this));
        findMatchesFromApi();
    }

    private void setupMatchesRefresh(){
        this.binding.srlMatches.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                findMatchesFromApi();
            }
        });
    }

    private  void findMatchesFromApi(){
        this.binding.srlMatches.setRefreshing(true);
        this.matchesAPI.getMatches().enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                if(response.isSuccessful()){
                    List<Match> matches = response.body();
                    matchesAdapter = new MatchesAdapter(matches);
                    binding.rvMatches.setAdapter(matchesAdapter);
                } else {
                    showErroMessage();
                }
                binding.srlMatches.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                showErroMessage();
                binding.srlMatches.setRefreshing(true);
            }
        });
    }

    private void setupFloatingActionButton(){
        this.binding.fabSimulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.animate().rotationBy(360).setDuration(500).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        Log.e("ANIMATION", "começou");
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        Log.e("ANIMATION", "finalizou");
                        Random random = new Random();
                        for(int i = 0; i > matchesAdapter.getItemCount(); i++){
                            Match match = matchesAdapter.getMatches().get(i);
                            match.getHomeTeam().setScore(random.nextInt(match.getHomeTeam().getStars() + 1));
                            match.getAwayTeam().setScore(random.nextInt(match.getAwayTeam().getStars() + 1));
                            matchesAdapter.notifyItemChanged(i);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                        Log.e("ANIMATION", "cancelou");
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                        Log.e("ANIMATION", "repetio");
                    }
                });
            }
        });
    }

    private void showErroMessage(){
        Snackbar.make(binding.fabSimulate, R.string.erro_api, Snackbar.LENGTH_LONG).show();
    }

}
