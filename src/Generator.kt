class Generator (
    private val algorithm:String,
    private val baseFrequency:Double,
    private val parameters: List<String>
){
    fun generate(): List<Double>{
        return when (algorithm){
            "edo"-> edoAlgorithm(baseFrequency,12)
            "justPrime"-> justPrimeAlgorithm(baseFrequency,7,8)
            "justOdd"-> justOddAlgorithm(baseFrequency,15)
            "Pythagorean"-> pythagoreanAlgorithm(baseFrequency)
            else -> emptyList()
        }
    }

    private fun edoAlgorithm(baseFrequency:Double, subdivisions:Int): List<Double>{
        println("EDO called")
        return emptyList()
    }

    private fun justPrimeAlgorithm(baseFrequency:Double, limit:Int, maxOctaveDiv: Int): List<Double>{
        println("Just Prime called")
        return emptyList()
    }

    private fun justOddAlgorithm(baseFrequency:Double, limit:Int): List<Double>{
        println("Just Odd called")
        return emptyList()
    }

    private fun pythagoreanAlgorithm(baseFrequency:Double): List<Double>{
        println("Pythagorean called")
        return emptyList()
    }
}