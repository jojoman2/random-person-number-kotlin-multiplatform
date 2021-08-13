package se.randompersonnumber

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ageInput = findViewById<EditText>(R.id.ageInput)
        val button = findViewById<Button>(R.id.calculateButton)
        val personNumberResultView = findViewById<TextView>(R.id.personNumberResult)
        val genderSelector = findViewById<Spinner>(R.id.genderSelector)

        genderSelector.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Male", "Female")
        )

        button.setOnClickListener {
            val age = ageInput.text.toString().toIntOrNull()
            val gender = toGenderOrNull(genderSelector.selectedItem.toString())

            if (age == null) {
                Toast.makeText(this, "Not a number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (gender == null) {
                Toast.makeText(
                    this,
                    "Unfortunately, only male and female are currently allowed to be in person numbers",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val personNumberRandomizer = PersonNumberRandomizer()
            val personNumber = personNumberRandomizer.randomizePersonNumber(age, gender)
            personNumberResultView.text = personNumber
        }
    }
}

private fun toGenderOrNull(genderString: String): PersonNumberRandomizer.Gender? = when (genderString) {
    "Male" -> PersonNumberRandomizer.Gender.Male
    "Female" -> PersonNumberRandomizer.Gender.Female
    else -> null
}
