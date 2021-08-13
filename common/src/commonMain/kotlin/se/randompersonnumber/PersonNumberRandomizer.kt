package se.randompersonnumber

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random
class PersonNumberRandomizer {
    fun randomizePersonNumber(age: Int, gender: Gender): String {
        val randomBirthday = randomizeBirthdayWithAge(age)
        val birthNumber = generateBirthNumber(gender)

        val lastDigitsOfYear = randomBirthday.year % 100
        val partWithoutControlNumber =
            numberToDoubleDigitString(lastDigitsOfYear) +
                    numberToDoubleDigitString(randomBirthday.monthNumber) +
                    numberToDoubleDigitString(randomBirthday.dayOfMonth) +
                    "-" +
                    birthNumber

        val controlNumber = calculateControlNumber(partWithoutControlNumber)
        return "$partWithoutControlNumber$controlNumber"
    }

    private fun randomizeBirthdayWithAge(age: Int): LocalDate {
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val latestPossibleBirthday = currentDate.minus(DatePeriod(age))

        val daysSinceLastBirthday = Random.nextInt(0, 364)
        return latestPossibleBirthday.minus(DatePeriod(0, 0, daysSinceLastBirthday))
    }

    private fun generateBirthNumber(gender: Gender): String {
        val firstNumber = Random.nextInt(0, 9)
        val secondNumber = Random.nextInt(0, 9)
        val thirdNumber = generateThirdBirthNumber(gender)

        return "$firstNumber$secondNumber$thirdNumber"
    }

    private fun generateThirdBirthNumber(gender: Gender): Int {
        val rangeToUse = when (gender) {
            Gender.Male -> 1..9 step 2
            Gender.Female -> 0..8 step 2
        }
        return rangeToUse.toList().random()
    }

    private fun calculateControlNumber(personNumber: String): Int {
        // See here: https://sv.wikipedia.org/wiki/Personnummer_i_Sverige#Kontrollsiffran
        val checksum = personNumber
            .mapNotNull { it.digitToIntOrNull() }
            .mapIndexed { index, number ->
                val currentFactor = if (index % 2 == 0) 2 else 1
                val product = number * currentFactor

                val productDigits = product.toString()
                return@mapIndexed productDigits.sumOf { it.digitToInt() }
            }.sum()

        return (10 - (checksum % 10)) % 10
    }

    private fun numberToDoubleDigitString(number: Int) =
        if (number > 9) number.toString() else "0$number"

    enum class Gender {
        Male,
        Female
    }
}
