import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.datetime.*
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import se.randompersonnumber.PersonNumberRandomizer

fun main() {
    window.onload = {
        console.log("hej")
        val ageInput = document.getElementById("age-input") as HTMLInputElement
        val genderSelect = document.getElementById("gender-select") as HTMLSelectElement
        val generateButton = document.getElementById("generate-button") as HTMLButtonElement
        val resultView = document.getElementById("result-view") as HTMLDivElement

        generateButton.addEventListener("click", {
            val age = ageInput.value.toIntOrNull()
            val gender = toGenderOrNull(genderSelect.value)
            if (age == null) {
                window.alert("Please enter a number")
                return@addEventListener
            }

            if (gender == null) {
                window.alert("Unfortunately, only male and female are currently allowed to be in person numbers")
                return@addEventListener
            }

            val personNumberRandomizer = PersonNumberRandomizer()
            val personNumber = personNumberRandomizer.randomizePersonNumber(age, gender)
            resultView.innerText = personNumber
        })
    }
}

private fun toGenderOrNull(genderString: String): PersonNumberRandomizer.Gender? = when (genderString) {
    "male" -> PersonNumberRandomizer.Gender.Male
    "female" -> PersonNumberRandomizer.Gender.Female
    else -> null
}