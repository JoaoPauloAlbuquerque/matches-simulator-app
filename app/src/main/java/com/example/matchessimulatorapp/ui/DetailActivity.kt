package com.example.matchessimulatorapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.matchessimulatorapp.databinding.ActivityDetailBinding
import com.example.matchessimulatorapp.domain.Match

class DetailActivity : AppCompatActivity() {

    object Extras{
        const val MATCH = "EXTRA_MATCH"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadMatchFromExtra()
    }

    private fun loadMatchFromExtra(){
        intent?.extras?.getParcelable<Match>(Extras.MATCH)?.let{
            Glide.with(this).load(it.place.imagem).into(this.binding.ivPlace)
            supportActionBar?.title = it.place.nome

            this.binding.tvDescription.text = it.descricao

            Glide.with(this).load(it.homeTeam.image).into(this.binding.ivHomeTeam)
            this.binding.tvHomeTeamName.text = it.homeTeam.name
            this.binding.rbHomeTeamStars.rating = it.homeTeam.stars.toFloat()
            if(it.homeTeam.score != null){
                this.binding.tvHomeTeamScore.text = it.homeTeam.score.toString()
            }

            Glide.with(this).load(it.awayTeam.image).into(this.binding.ivAwayTeam)
            this.binding.tvAwayTeamName.text = it.awayTeam.name
            this.binding.rbAwayTeamStars.rating = it.awayTeam.stars.toFloat()
            if(it.awayTeam.score != null){
                this.binding.tvAwayTeamScore.text = it.awayTeam.score.toString()
            }
        }
    }
}