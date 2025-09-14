import kotlin.math.pow

class Generator (
    private val algorithm:String,
    private val baseFrequency:Double,
    private val parameters: List<String>
){
    fun generate(): List<Double>{
        return when (algorithm){
            "edo"-> edoAlgorithm(baseFrequency,12)
            "justPrime"-> justPrimeAlgorithm(baseFrequency,5,"normal")
            "justOdd"-> justOddAlgorithm(baseFrequency,15)
            "Pythagorean"-> pythagoreanAlgorithm(baseFrequency)
            else -> emptyList()
        }
    }

    //ALGORITHMS
    private fun edoAlgorithm(baseFrequency:Double, subdivisions:Int): List<Double>{
        println("EDO called")
        val notesInOctave = mutableListOf<Double>()
        for (i in 0 until subdivisions){
            //IMPORTANT PART LOL
            val newNote = baseFrequency * 2.0.pow(i/subdivisions.toDouble())

            notesInOctave.add(newNote)
        }
        return notesInOctave
    }

    private fun justPrimeAlgorithm(baseFrequency:Double, limit:Int, deepness: String): List<Double>{
        println("Just Prime called")
        val primes: List<Double> = eratosthenesSieve(limit).map { it.toDouble() }
        val exponents = primes.map { prime-> generateExponentsForPrime(prime,deepness) }

        val ratios = mutableListOf<Double>()
        val permutatedExponents = permutateExponents(exponents.toMutableList())

        permutatedExponents.forEach { exponents ->
            val ratio = primes
                .mapIndexed { index, prime -> prime.pow(exponents[index]) }
                .reduce { accumulated, next -> accumulated*next }
            if (ratio in 1.0..2.0){
                ratios.add(ratio)
            }
        }
        ratios.forEach {
            println("${approximateFraction(it)}")
        }

        println("Number of permutations: ${permutatedExponents.size}")
        return emptyList()
    }

    private fun justOddAlgorithm(baseFrequency:Double, limit:Int): List<Double> {
        println("Just Odd called")

        val notesInOctave = mutableListOf<Double>()
        notesInOctave.add(baseFrequency)

        //First, we generate the possible factors to be calculated
        val factors = mutableListOf<Double>()
        val lowEndOfFactors = (limit + 1) / 2
        for (i in lowEndOfFactors until 2 * limit) {
            val candidate = (i + 1)
            if (candidate <= limit)
                factors.add(candidate * 1.0)
            else if (candidate % 2 == 0)
                factors.add(candidate * 1.0)
        }

        //We then pick up the useful numerators, and divide them to get the fractions
        val numerators = factors.filter { it > limit }
        numerators.forEach { num ->
            val denominators = factors.filter { factor -> factor > num / 2 && factor < num }
            denominators.forEach { den ->
                val fraction = (num / den)
                val candidate = (baseFrequency*fraction)
                if (candidate !in notesInOctave)
                    notesInOctave.add(candidate)
            }
        }
        notesInOctave.sort()

        println("Added ratios: ")
        notesInOctave.forEach {
            val debugFrac = approximateFraction(it/baseFrequency)
            print("[${debugFrac[0]}/${debugFrac[1]}], ")
        }

        return notesInOctave
    }

    private fun pythagoreanAlgorithm(baseFrequency:Double): List<Double>{
        println("Pythagorean called")
        return emptyList()
    }

    //"DEPENDENCIES"
    private fun eratosthenesSieve(upperLimit: Int): List<Int>{
        //We generate a list with all Ints from 2 up to limit
        val initialIntegers = (2..upperLimit)

        //We create a copy of Initial, which will be filtered
        val primeIntegers = initialIntegers.toMutableList()

        //We implement the Sieve of Eratosthenes to remove unwanted elements of the Primes variable
        for (currentPrime in initialIntegers){
            if (currentPrime*currentPrime>upperLimit)
                break
            primeIntegers.removeIf { possibleComposite ->
                possibleComposite != currentPrime &&
                        possibleComposite%currentPrime == 0
                }
        }
        return primeIntegers
    }
    private fun generateExponentsForPrime(prime: Double, deepness: String): List<Int>{
        var exponents = emptyList<Int>()
        when (deepness){
            "normal" -> {
                exponents = when(prime){
                    2.0 -> (-6..6).toList()
                    3.0, 5.0 -> (-4..4).toList()
                    7.0, 11.0-> (-2..2).toList()
                    else -> (-1..1).toList()
                }
            }
            "deep" -> {
                exponents = when(prime){
                    2.0 -> (-11..11).toList()
                    3.0 -> (-7..7).toList()
                    5.0 -> (-4..4).toList()
                    7.0, 11.0, 13.0, 17.0-> (-2..2).toList()
                    else -> (-1..1).toList()
                }
            }
            else -> (-1..1).toList()
        }
        return exponents.toList()
    }
    private fun permutateExponents(exponents: MutableList<List<Int>>): List<List<Int>>{
        val permutations = mutableListOf<List<Int>>()
        if (exponents.size==1){
            val currentPrimeExponentList = exponents[0]
            currentPrimeExponentList.forEach { exp ->
                val currentExp = mutableListOf<Int>()
                currentExp.add(exp)
                permutations.add(currentExp)
            }
        } else {
            val head = exponents.first()
            val tail = exponents.drop(1).toMutableList()
            val permutatedTail = permutateExponents(tail)

            //do stuff
            head.forEach { headPrimeExponent ->
                permutatedTail.forEach { otherPrimesExponents ->
                    val newPermutation = mutableListOf<Int>()
                    newPermutation.add(headPrimeExponent)
                    otherPrimesExponents.forEach {
                        newPermutation.add(it)
                    }
                    permutations.add(newPermutation)
                }

            }
        }
        return permutations.toList()
    }

    //CODE GENERATED BY CHATGPT
    private fun approximateFraction(value: Double, maxDenominator: Int = 1000): List<Int> {
        var x = value
        var a = kotlin.math.floor(x).toInt()
        var h1 = 1
        var k1 = 0
        var h = a
        var k = 1

        while (x - a > 1e-12 && k < maxDenominator) {
            x = 1.0 / (x - a)
            a = kotlin.math.floor(x).toInt()
            val h2 = h1
            val k2 = k1
            h1 = h
            k1 = k
            h = a * h1 + h2
            k = a * k1 + k2
        }

        // reduce by greatest common divisor
        val gcd = gcd(h, k)
        return listOf(h / gcd, k / gcd)
    }

    private fun gcd(a: Int, b: Int): Int {
        var x = kotlin.math.abs(a)
        var y = kotlin.math.abs(b)
        while (y != 0) {
            val temp = x % y
            x = y
            y = temp
        }
        return x
    }
    //CODE BY CHATGPT ENDS HERE

}