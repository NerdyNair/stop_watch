package com.example.stop_watch

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var resetButton: Button
    private lateinit var timerTextView: TextView
    private var startTime: Long = 0
    private var handler: Handler? = null
    private var runnable: Runnable? = null
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)

        startButton = findViewById(R.id.start_button)
        stopButton = findViewById(R.id.stop_button)
        resetButton = findViewById(R.id.reset_button)
        timerTextView = findViewById(R.id.timer_textview)

        handler = Handler()

        startButton.setOnClickListener { startTimer() }
        stopButton.setOnClickListener { stopTimer() }
        resetButton.setOnClickListener { resetTimer() }
    }

    private fun startTimer() {
        if (!isRunning) {
            startTime = System.currentTimeMillis()
            isRunning = true
            runnable = object : Runnable {
                override fun run() {
                    val millis = System.currentTimeMillis() - startTime
                    val seconds = (millis / 1000).toInt()
                    val minutes = seconds / 60
                    val secondsRemaining = seconds % 60
                    timerTextView.text = String.format("%02d:%02d", minutes, secondsRemaining)
                    handler?.postDelayed(this, 500)
                }
            }
            handler?.postDelayed(runnable!!, 500)
        }
    }

    private fun stopTimer() {
        if (isRunning) {
            isRunning = false
            handler?.removeCallbacks(runnable!!)
        }
    }

    private fun resetTimer() {
        stopTimer()
        startTime = 0
        timerTextView.text = "00:00"
    }
}