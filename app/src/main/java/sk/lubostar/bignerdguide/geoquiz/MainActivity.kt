package sk.lubostar.bignerdguide.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        true_button.setOnClickListener {
                view: View -> Toast.makeText(this, R.string.correct_toast,
                Toast.LENGTH_SHORT).show()
        }

        false_button.setOnClickListener {
                view: View -> Toast.makeText(this, R.string.correct_toast,
            Toast.LENGTH_SHORT).show()
        }
    }
}