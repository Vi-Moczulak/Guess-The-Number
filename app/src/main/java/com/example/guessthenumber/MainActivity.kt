package com.example.guessthenumber

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var number_input: EditText
    private lateinit var guess_button: Button
    private lateinit var new_game_button: Button
    private lateinit var reset_score: Button
    private lateinit var the_number: TextView
    private lateinit var return_msg: TextView
    private lateinit var score_text: TextView
    private lateinit var count_try_text: TextView


    private var user_number: Int = 0
    private var score: Int = 0
    private var count_try: Int = 0


    var guess = -1;
    override fun onCreate(savedInstanceState: Bundle?) {

        getRecord()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        guess_button = findViewById(R.id.guess_button)
        number_input = findViewById(R.id.number_input)
        the_number = findViewById(R.id.the_number)
        return_msg = findViewById(R.id.retun_msg)
        score_text = findViewById(R.id.score_text)
        new_game_button = findViewById(R.id.new_game_button)
        reset_score = findViewById(R.id.reset_score)
        count_try_text = findViewById(R.id.count_try_text)

        var randomValue = Random.nextInt(0, 20)
        the_number.text = randomValue.toString()

        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Win!")
        builder.setMessage("You guessed!")
        builder.setPositiveButton("OK"){ dialogInterface: DialogInterface, i: Int ->}
        val dialog: AlertDialog = builder.create()



        guess_button.setOnClickListener {
            try {

                user_number = number_input.getText().toString().toInt()
                if (user_number>20){
                    Toast.makeText(applicationContext, "Max 20", Toast.LENGTH_LONG).show()
                    number_input.getText().clear();
                }else{
                number_input.getText().clear();

                count_try++
                count_try_text.text="Try: "+count_try.toString()
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
                    score_text.text = "Score: " + score.toString()
                    count_try = 0
                    count_try_text.text="Try: "+count_try.toString()

                    randomValue = Random.nextInt(0, 20)
                    number_input.getText().clear();
                    //return_msg.text = "You guessed!"
                    setRecord()
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
                    return_msg.text = "Restart"
                    score_text.text = "Score: 0"
                    score = 0
                    count_try = 0
                    count_try_text.text = "Try: " + count_try.toString()

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
            count_try_text.text="Try: "+count_try.toString()

            randomValue = Random.nextInt(0, 20)
            number_input.getText().clear();
            return_msg.text = "New Game!"

        }
        reset_score.setOnClickListener {

            score_text.text = "Score: 0"
            count_try = 0
            count_try_text.text="Try: "+count_try.toString()

            randomValue = Random.nextInt(0, 20)
            number_input.getText().clear();
            return_msg.text = "Reset!"
        }



    }
    fun setRecord(){
        val sharedScore = this.getSharedPreferences("com.example.myapplication.shared",0)
        val edit = sharedScore.edit()
        edit.putInt("score", score)
        edit.apply()
    }

    fun getRecord(){
        val sharedScore = this.getSharedPreferences("com.example.myapplication.shared",0)
        score = sharedScore.getInt("score", 0)
    }
}

