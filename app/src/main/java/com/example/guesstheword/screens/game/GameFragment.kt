package com.example.guesstheword.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.guesstheword.R
import com.example.guesstheword.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel

    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)


        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
        }

        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
        }



        viewModel.word.observe(viewLifecycleOwner) { newWord ->

            binding.wordText.text = newWord

        }

        viewModel.score.observe(viewLifecycleOwner) { newScore ->
            binding.scoreText.text = newScore.toString()
        }

        viewModel.eventGameFinish.observe(viewLifecycleOwner) { isFinish ->
            if (isFinish) {
                val currentScore = viewModel.score.value ?: 0
                val action = GameFragmentDirections.actionGameToScore(currentScore)
                findNavController(this).navigate(action)
                viewModel.onGameFinishComplete()
            }

        }

        return binding.root
    }

}