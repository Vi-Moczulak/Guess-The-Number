package com.example.guessthenumber

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.guessthenumber.DB.DBHelper
import com.example.guessthenumber.DB.DBHelper_Users
import com.example.guessthenumber.data.message.message
import com.example.guessthenumber.ui.login.LoginActivity
import com.example.guessthenumber.ui.login.LoginViewModel
import com.example.guessthenumber.ui.login.LoginViewModelFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var number_input: EditText
    private lateinit var guess_button: Button
    private lateinit var new_game_button: Button
    private lateinit var ranking_button: Button
    private lateinit var the_number: TextView
    private lateinit var return_msg: TextView
    private lateinit var score_text: TextView
    private lateinit var count_try_text: TextView
    private lateinit var logOut_button: Button


    private var user_number: Int = 0
    private var score: Int = 0
    private var count_try: Int = 0

    //private lateinit var loginViewModel: LoginViewModel
    internal lateinit var db: DBHelper
    internal lateinit var db_user: DBHelper_Users


    var guess = -1;
    override fun onCreate(savedInstanceState: Bundle?) {


        var user = " "
        user = intent.getStringExtra("user").toString()


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        guess_button = findViewById(R.id.guess_button)
        number_input = findViewById(R.id.number_input)
        the_number = findViewById(R.id.the_number)
        return_msg = findViewById(R.id.retun_msg)
        score_text = findViewById(R.id.score_text)
        new_game_button = findViewById(R.id.new_game_button)
        ranking_button = findViewById(R.id.button_ranking)
        count_try_text = findViewById(R.id.count_try_text)
        logOut_button = findViewById(R.id.LogOut)

        var randomValue = Random.nextInt(0, 20)
        the_number.text = randomValue.toString()

        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Win!")
        builder.setMessage("You guessed!")
        builder.setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int -> }
        val dialog: AlertDialog = builder.create()

        db = DBHelper(this)
        db_user = DBHelper_Users(this)

        score = db_user.getScore(user)
        println("Score: "+score)
        score_text.text = "Score: " + score.toString()

        guess_button.setOnClickListener {
            try {

                user_number = number_input.getText().toString().toInt()
                if (user_number > 20) {
                    Toast.makeText(applicationContext, "Max 20", Toast.LENGTH_LONG).show()
                    number_input.getText().clear();
                } else {
                    number_input.getText().clear();

                    count_try++
                    count_try_text.text = "Try: " + count_try.toString()
                    return_msg.text = ""


                    if (user_number == randomValue) {
                        //1 razem - 5pkt, za 2 do 4 - 3pkt, za 5 do 6 - 2pkt, za 7 do 10 - 1pk
                        Log.d("myTag", "Tak");
                        score += if (count_try == 1) {
                            5
                        } else if (count_try < 5) {
                            3
                        } else if (count_try < 7) {
                            2
                        } else {
                            1
                        }
                        db_user.updateScore(user, score)

                        score_text.text = "Score: " + score.toString()
                        count_try = 0
                        count_try_text.text = "Try: " + count_try.toString()

                        randomValue = Random.nextInt(0, 20)
                        number_input.getText().clear();
                        //return_msg.text = "You guessed!"
                        //setRecord()
                        dialog.show()


                        the_number.text = randomValue.toString()

                    } else if (user_number > randomValue) {
                        //return_msg.text = "Too high!"
                        Toast.makeText(applicationContext, "Too high!", Toast.LENGTH_LONG).show()
                    } else if (user_number < randomValue) {
                        //return_msg.text = "Too low!"
                        Toast.makeText(applicationContext, "Too low!", Toast.LENGTH_LONG).show()

                    }
                    if (count_try == 10) {
                        val new = message(user, score)
                        db.addMessage(new)

                        return_msg.text = "Restart"
                        score_text.text = "Score: 0"
                        score = 0
                        count_try = 0
                        count_try_text.text = "Try: " + count_try.toString()

                        db_user.updateScore(user, score)
                        randomValue = Random.nextInt(0, 20)
                        number_input.getText().clear();

                        Toast.makeText(applicationContext, "You lose!", Toast.LENGTH_LONG).show()
                        the_number.text = randomValue.toString()


                    }
                }
            } catch (e: NumberFormatException) {
            }
        }
        new_game_button.setOnClickListener {

            count_try = 0
            count_try_text.text = "Try: " + count_try.toString()

            randomValue = Random.nextInt(0, 20)
            number_input.getText().clear();
            return_msg.text = "New Game!"

        }
        ranking_button.setOnClickListener {

            val thread = Thread {
                runOnUiThread {
                    val intent = Intent(this, Ranking::class.java)
                    startActivity(intent)
                }
            }
            thread.start()
        }


        logOut_button.setOnClickListener {
            finish()
        }


    }


}

