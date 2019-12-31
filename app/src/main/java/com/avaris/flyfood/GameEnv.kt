package com.avaris.flyfood


import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.game_env.*



class GameEnv : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_env)


        translationRange = screenWidth.toFloat() + 200.0f


        if (hitMediaPlayer ==  null) {
            hitMediaPlayer = MediaPlayer.create(this, R.raw.slice)
        }

    }


    override fun onResume() {
        super.onResume()

        val randomMode = (0 .. 3).random()

        randTargetId = flyingThings[randomMode].random()
        Target.setBackgroundResource(randTargetId)
        repThingId = flyingThings[randomMode].random()

        prepLeftTurns()
        prepRightTurns()

        stopFlying = false
        score = 0
        delay = 500L


        targetsSetter(arrayOf(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10),
                      arrayOf(f11, f12, f13, f14, f15, f16, f17, f18, f19, f20))

        setFlyingThings(randomMode)

        startFlyingRight()
        startFlyingLeft()

        mediaPlayer!!.start()
        if (hitMediaPlayer ==  null) {
            hitMediaPlayer = MediaPlayer.create(this, R.raw.slice)
        }

        setGameTimer(TimerText)

    }


    override fun onPause() {
        super.onPause()

        mediaPlayer!!.pause()
        hitMediaPlayer!!.release()
        hitMediaPlayer = null

    }


    override fun onStop() {
        super.onStop()

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


//*****************************************

    fun flyCatch(view: View){

        if (gotcha(view)){

            score++

            if (hitMediaPlayer!!.isPlaying){
                hitMediaPlayer?.apply {
                    stop()
                    prepare()
                }
            }

            hitMediaPlayer!!.start()

            pop(view)

            score_text.text = score.toString()

            val tempThingId = repThingId
            repThingId = randTargetId
            view.setBackgroundResource(tempThingId)
            randTargetId = flyingThings[gameMode].random()
            Target.setBackgroundResource(randTargetId)

        }

        else {

            gameFinished()

        }
    }

    var gotcha= {
            view: View ->

        view.background.constantState == Target.background.constantState

    }


    private var gameTimer: CountDownTimer? = null
    private fun setGameTimer(view: TextView){
        gameTimer = object : CountDownTimer(90000, 1000){

            override fun onTick(remainingTime: Long) {
                view.text = (remainingTime/1000).toString()
            }

            override fun onFinish() {
                gameFinished()
            }
        }.start()
    }

    @SuppressLint("ApplySharedPref")
    private fun gameFinished() {

        disableTouch.visibility = View.VISIBLE
        gameTimer!!.cancel()

        val sharedPref = this.getSharedPreferences("HighScore", Context.MODE_PRIVATE)
        if (score > sharedPref.getInt("highScore", 0)) {
            with(sharedPref.edit()){
                putInt("highScore", score)
                commit()
            }
        }

        GameOver_Container.visibility = View.VISIBLE
        val gameOverTransaction = supportFragmentManager.beginTransaction()
        val gameOverFragment = GameOver()
        gameOverTransaction.add(R.id.GameOver_Container, gameOverFragment)
        gameOverTransaction.commit()

        stopFlying = true

    }


}




var hitMediaPlayer: MediaPlayer? = null

//********************************************

var gameMode = 0
var repThingId = 0
var randTargetId = 0

fun setFlyingThings(mode: Int){
    gameMode = mode

    leftThings[0].setBackgroundResource(flyingThings[mode][0])
    rightThings[0].setBackgroundResource(flyingThings[mode][0])
    leftThings[1].setBackgroundResource(flyingThings[mode][0])
    rightThings[1].setBackgroundResource(flyingThings[mode][0])
    leftThings[2].setBackgroundResource(flyingThings[mode][1])
    rightThings[2].setBackgroundResource(flyingThings[mode][1])
    leftThings[3].setBackgroundResource(flyingThings[mode][1])
    rightThings[3].setBackgroundResource(flyingThings[mode][1])
    leftThings[4].setBackgroundResource(flyingThings[mode][2])
    rightThings[4].setBackgroundResource(flyingThings[mode][2])
    leftThings[5].setBackgroundResource(flyingThings[mode][2])
    rightThings[5].setBackgroundResource(flyingThings[mode][2])
    leftThings[6].setBackgroundResource(flyingThings[mode][3])
    rightThings[6].setBackgroundResource(flyingThings[mode][3])
    leftThings[7].setBackgroundResource(flyingThings[mode][3])
    rightThings[7].setBackgroundResource(flyingThings[mode][3])
    leftThings[8].setBackgroundResource(flyingThings[mode][4])
    rightThings[8].setBackgroundResource(flyingThings[mode][4])
    leftThings[9].setBackgroundResource(flyingThings[mode][4])
    rightThings[9].setBackgroundResource(flyingThings[mode][4])
}


var leftTurns = Array(10){0}
var rightTurns = Array(10){0}

fun prepLeftTurns(){
    val rawTurns1 = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

    for (i in 0 .. 9){
        val x = rawTurns1.random()
        rawTurns1.remove(x)
        leftTurns[i] = x
    }
}
fun prepRightTurns(){
    val rawTurns2 = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

    for (i in 0 .. 9){
        val x = rawTurns2.random()
        rawTurns2.remove(x)
        rightTurns[i] = x
    }
}


var leftThings: Array<ImageButton> = arrayOf()
var rightThings: Array<ImageButton> = arrayOf()

fun targetsSetter(left: Array<ImageButton>, right: Array<ImageButton>){
    leftThings = left
    rightThings = right
}


//********************************************

var stopFlying = false
var score: Int = 0

var initialDuration = 6000L
var delay = 500L
const val difficulty: Int = 750
const val difficultyPace: Int = 15

var translationRange = 0.0f
var rotationRange = 1080.0f


fun pop(view: View){

    var forwarded = 1
    var reversed = -1
    var reshape = true

    object : CountDownTimer(350L, 5){
        override fun onTick(remaining: Long) {
            view.scaleX = 1 + ((forwarded + reversed*(remaining.toFloat()/350)) * 0.4f)
            view.scaleY = 1 + ((forwarded + reversed*(remaining.toFloat()/350)) * 0.4f)
        }

        override fun onFinish() {
            forwarded = 0
            reversed = 1

            if (reshape){
                reshape = false
                start()
            }
        }
    }.start()
}


fun startFlyingRight() {
    fly(leftThings[leftTurns[0]], 0, delay); delay += 500
    fly(leftThings[leftTurns[1]], 0, delay); delay += 500
    fly(leftThings[leftTurns[2]], 0, delay); delay += 500
    fly(leftThings[leftTurns[3]], 0, delay); delay += 500
    fly(leftThings[leftTurns[4]], 0, delay); delay += 500
    fly(leftThings[leftTurns[5]], 0, delay); delay += 500
    fly(leftThings[leftTurns[6]], 0, delay); delay += 500
    fly(leftThings[leftTurns[7]], 0, delay); delay += 500
    fly(leftThings[leftTurns[8]], 0, delay); delay += 500
    fly(leftThings[leftTurns[9]], 0, delay); delay  = 500
}

fun startFlyingLeft() {
    fly(rightThings[rightTurns[0]], 1, delay); delay += 500
    fly(rightThings[rightTurns[1]], 1, delay); delay += 500
    fly(rightThings[rightTurns[2]], 1, delay); delay += 500
    fly(rightThings[rightTurns[3]], 1, delay); delay += 500
    fly(rightThings[rightTurns[4]], 1, delay); delay += 500
    fly(rightThings[rightTurns[5]], 1, delay); delay += 500
    fly(rightThings[rightTurns[6]], 1, delay); delay += 500
    fly(rightThings[rightTurns[7]], 1, delay); delay += 500
    fly(rightThings[rightTurns[8]], 1, delay); delay += 500
    fly(rightThings[rightTurns[9]], 1, delay)
}


fun fly(view: View, atRight: Int, delay: Long = 0L){

    if (delay != 0L) {
        object : CountDownTimer(delay, delay) {
            override fun onFinish() {

                if (atRight == 1) {
                    view.translationX = translationRange
                    animateFlying(view, 0, 1)
                } else animateFlying(view)
            }

            override fun onTick(millisUntilFinished: Long) {}
        }.start()
    }

    else{
        if (atRight == 1) {
            view.translationX = translationRange
            animateFlying(view, 0, 1)
        } else animateFlying(view)
    }
}

fun animateFlying(view: View, forward: Int = 1, reverse: Int = -1, duration: Long = initialDuration){
    object : CountDownTimer(duration, 5) {

        override fun onTick(remaining: Long) {
            view.translationX = (forward + reverse*(remaining.toFloat()/duration)) * translationRange
            view.rotation = (forward + reverse*(remaining.toFloat()/ duration)) * rotationRange

            if (stopFlying){
                cancel()}
        }

        override fun onFinish() {
            var forwarded = 0;  if (forward == 0) forwarded = 1
            var reversed = 1;   if (reverse == 1) reversed = -1

            var newDuration = initialDuration - ((score/difficultyPace) * difficulty)
            if (newDuration < 500)
                newDuration = 500

            if (!stopFlying)
                animateFlying(view, forwarded, reversed, newDuration)
            else
                view.visibility = View.GONE
        }
    }.start()
}


