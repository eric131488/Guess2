package com.tom.guess

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {
    val secretNumber = SecretNumber()
    val TAG = MaterialActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.replay_game))
                .setMessage(getString(R.string.are_you_sure))
                .setPositiveButton(getString(R.string.ok)) { _, _ ->
                    secretNumber.reset()
                    counter.text = secretNumber.count.toString()
                    number.setText("")
                }
                .setNeutralButton(getString(R.string.cancel), null)
                .show()
        }

        counter.text = secretNumber.count.toString()
    }

    fun check(view: View) {
        val n = number.text.toString().toInt()
        println("number : $n")
        Log.d(TAG , "number: $n")
        val diff = secretNumber.validate(n)

        var message = when {
            diff < 0 -> getString(R.string.bigger)
            diff > 0 -> getString(R.string.smaller)
            secretNumber.count < 3 -> getString(R.string.excellent_the_number_is) + secretNumber.secret
            else -> getString(R.string.yes_you_got_it)
        }

        counter.text = secretNumber.count.toString()

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .show()

    }

}
