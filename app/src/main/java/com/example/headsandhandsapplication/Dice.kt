package com.example.headsandhandsapplication

import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

internal class Dice {

    private val dice = arrayOfNulls<Drawable>(6)

    //TODO Специальное допущение - контекст всегда нужно проверять на null перед использованием
    fun rollDice(context: AppCompatActivity, diceImage: ImageView, timesToRoll: Int): Int {
        rollDice(context, diceImage)
        val damage = damage(timesToRoll)
        Toast.makeText(context, "$damage damage", Toast.LENGTH_SHORT).show()
        return damage
    }

    private fun damage(timesToRoll: Int): Int {
        var result = 0
        var count = timesToRoll
        while (count != 0) {
            count--
            val diceRoll = (1..6).random()
            result += diceRoll
        }
        return result
    }

    private fun rollDice(context: AppCompatActivity, diceImage: ImageView) {
        createDice(context)
        val rollAnimationsDuration = 50
        val roll: IntArray = intArrayOf(6, 6)
        val animationHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                diceImage.setImageDrawable(dice[roll[0]])
            }
        }

        Thread {
            for (i in 0 until rollAnimationsDuration) {
                doRoll(context, roll, animationHandler)
            }
        }.start()
    }

    private fun createDice(context: AppCompatActivity) {
        if (dice[0] == null) {
            val diceImages = intArrayOf(R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5, R.drawable.d6)
            for (i in 0..5) {
                dice[i] = ContextCompat.getDrawable(context, diceImages[i])
            }
        }
    }

    private fun doRoll(context: AppCompatActivity, roll: IntArray, animationHandler: Handler) {
        val delayTime = 15
        roll[0] = (0..5).random()
        synchronized(context.layoutInflater) { animationHandler.sendEmptyMessage(0) }
        try { // delay for smooth animation
            Thread.sleep(delayTime.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}