fun main() {

    val generatedNotes = Generator("edo",
        440.0,
        emptyList()).generate()

    generatedNotes.forEach {
        println("Note : $it")
    }
}