package com.avaris.flyfood


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.game_over.*


class GameOver : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.game_over, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FinalScore.text = "$score"


        menu.setOnClickListener {
            //val menuAct = Intent(activity, Menu::class.java)
            //startActivity(menuAct)

            activity!!.finish()
        }

        replay.setOnClickListener {
            val gameAct = Intent(activity, GameEnv::class.java)
            activity!!.finish()
            startActivity(gameAct)
        }

    }


}
