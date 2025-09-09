class Generator (
    private val algorithm:String,
    private val baseFrequency:Double,
    private val parameters: List<String>
){
    fun generate(): List<Double>{
        return when (algorithm){
            "a"-> callA()
            "b"-> callB()
            else -> emptyList()
        }
    }

    private fun callA(): List<Double>{
        println("A called")
        return emptyList()
    }

    private fun callB(): List<Double>{
        println("B called")
        return emptyList()
    }
}