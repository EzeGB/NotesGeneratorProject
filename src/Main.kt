import kotlin.math.pow
import kotlin.math.round

fun main() {

    val generatedNotes = Generator("justOdd",
        440.0,
        emptyList()).generate()

    println()
    var noteNumber = 1
    generatedNotes.forEach { gen ->
        println("$noteNumber Note : ${gen.roundTo(8)}")
        noteNumber++
    }
}

fun Double.roundTo(n: Int): Double {
    val factor = 10.0.pow(n)
    return round(this * factor) / factor
}