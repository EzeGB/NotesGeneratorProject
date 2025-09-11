import kotlin.math.pow
import kotlin.math.round

class Generator (
    private val algorithm:String,
    private val baseFrequency:Double,
    private val parameters: List<String>
){
    fun generate(): List<Double>{
        return when (algorithm){
            "edo"-> edoAlgorithm(baseFrequency,12)
            "justPrime"-> justPrimeAlgorithm(baseFrequency,7,8)
            "justOdd"-> justOddAlgorithm(baseFrequency,9)
            "Pythagorean"-> pythagoreanAlgorithm(baseFrequency)
            else -> emptyList()
        }
    }

    private fun edoAlgorithm(baseFrequency:Double, subdivisions:Int): List<Double>{
        println("EDO called")
        val notesInOctave = mutableListOf<Double>()
        for (i in 0 until subdivisions){
            //IMPORTANT PART LOL
            val newNote = baseFrequency * 2.0.pow(i/subdivisions.toDouble())

            notesInOctave.add(newNote.roundTo(4))
        }
        return notesInOctave
    }

    private fun justPrimeAlgorithm(baseFrequency:Double, limit:Int, maxOctaveDiv: Int): List<Double>{
        println("Just Prime called")
        return emptyList()
    }

    private fun justOddAlgorithm(baseFrequency:Double, limit:Int): List<Double>{
        println("Just Odd called")

        //First, we generate the possible factors to be calculated
        val factors = mutableListOf<Double>()
        val lowEndOfFactors = (limit+1)/2
        for (i in lowEndOfFactors until 2*limit){
            val candidate = (i+1)
            if (candidate<=limit)
                factors.add(candidate*1.0)
            else if (candidate%2==0)
                factors.add(candidate*1.0)
        }

        //We then pick up the useful numerators, and divide them to get the fractions
        val numerators = factors.filter { it > limit }


        factors.forEach { print("$it, ") }
        println()
        numerators.forEach { print("$it, ") }
        return emptyList()
    }

    private fun pythagoreanAlgorithm(baseFrequency:Double): List<Double>{
        println("Pythagorean called")
        return emptyList()
    }

    fun Double.roundTo(n: Int): Double {
        val factor = 10.0.pow(n)
        return round(this * factor) / factor
    }
}