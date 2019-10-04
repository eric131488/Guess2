package com.tom.guess

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {
    private val REQUEST_RECORD = 100
    val secretNumber = SecretNumber()
    val TAG = MaterialActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            reply()
        }

        counter.text = secretNumber.count.toString()
        Log.d(TAG, "onCreate: " + secretNumber.secret)

        val count = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getInt("REC_COUNTER", -1)
        val nick = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getString("REC_NICKNAME", null)
        Log.d(TAG, "data: $count / $nick")

    }

    private fun reply() {
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

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
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
            .setPositiveButton(getString(R.string.ok)) { diallog, which ->
                if (diff == 0) {
                    val intent = Intent(this, RecordActivity::class.java)
                    intent.putExtra("COUNTER", secretNumber.count)
//                    startActivity(intent)
                    startActivityForResult(intent, REQUEST_RECORD)
                }
            }
            .show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_RECORD) {
            if (resultCode == Activity.RESULT_OK) {
                val nickName = data?.getStringExtra("NICK")
                Log.d(TAG, "onActivityResult: $nickName")
                reply()
            }
        }
    }
}
