package com.example.matchessimulatorapp.data;

import com.example.matchessimulatorapp.domain.Match;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MatchesAPI {

    @GET ("matches.json")
    Call<List<Match>> getMatches();
}
