package za.ac.iie.quizapp

data class Question (

    val id: Int,
    val question: String,
    val image: Int,
    val optionOne: String, //True
    val optionTwo: String, //False
    val correctAnswer: Int


)
