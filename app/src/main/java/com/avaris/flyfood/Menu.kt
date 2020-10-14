package com.avaris.flyfood

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import kotlinx.android.synthetic.main.menu.*



class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)



        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
            screenWidth = displayMetrics.widthPixels
            screenHeight = displayMetrics.heightPixels


        mediaPlayer = MediaPlayer.create(this, R.raw.sound_menu)
        mediaPlayer!!.isLooping = true
        mediaPlayer!!.start()


        bStart.setOnClickListener {
            val start = Intent(this, GameEnv::class.java)
            startActivity(start)
        }

        bQuit.setOnClickListener {
            finish()
        }

    }


    override fun onResume() {
        super.onResume()

        val sharedPref = this.getSharedPreferences("HighScore", Context.MODE_PRIVATE)
        highScore.text = sharedPref.getInt("highScore", 0).toString()

        if (!(mediaPlayer!!.isPlaying)){
            mediaPlayer!!.start()
        }
    }

    override fun onPause() {
        super.onPause()

        mediaPlayer!!.pause()
    }

    override fun onDestroy() {
        super.onDestroy()

        mediaPlayer!!.release()
        mediaPlayer = null
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideBars()
    }

    private fun hideBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }


}



var mediaPlayer: MediaPlayer? = null


var screenWidth: Int = 0
var screenHeight: Int = 0


val flyingThings = arrayOf(
    arrayOf(R.drawable.chiken01, R.drawable.fish01, R.drawable.meat01, R.drawable.cheese01, R.drawable.chiken_part01),
    arrayOf(R.drawable.bread02, R.drawable.butter02, R.drawable.chips02, R.drawable.sugar02, R.drawable.croissant02),
    arrayOf(R.drawable.jam03, R.drawable.milk03, R.drawable.liquor03, R.drawable.oliveoil03, R.drawable.water03),
    arrayOf(R.drawable.apple04, R.drawable.bananas04, R.drawable.pineapple04, R.drawable.broccoli04, R.drawable.tomato04)
)