package com.example.healthsync.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.healthsync.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.cardIMC.setOnClickListener { startActivity(Intent(this, IMCActivity::class.java)) }
        binding.cardPerimetria.setOnClickListener { startActivity(Intent(this, PerimetriaActivity::class.java)) }
        binding.cardBioimpedancia.setOnClickListener { startActivity(Intent(this, BioimpedanciaActivity::class.java)) }
        binding.cardMassaMagra.setOnClickListener { startActivity(Intent(this, MassaMagraActivity::class.java)) }
        binding.cardComunidade.setOnClickListener { startActivity(Intent(this, ComunidadeActivity::class.java)) }
<<<<<<< HEAD
        binding.cardHistorico.setOnClickListener { startActivity(Intent(this, HistoricoActivity::class.java)) }
    }
}
=======
        binding.cardAPIAlimentos.setOnClickListener { startActivity(Intent(this, APIAlimentosActivity::class.java)) }
        binding.cardHistorico.setOnClickListener { startActivity(Intent(this, HistoricoActivity::class.java)) }
    }
}
>>>>>>> master
