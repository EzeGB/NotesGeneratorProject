fun main() {

    val generatedNotes = Generator("justOdd",
        440.0,
        emptyList()).generate()

    generatedNotes.forEach {
        println("Note : $it")
    }
}