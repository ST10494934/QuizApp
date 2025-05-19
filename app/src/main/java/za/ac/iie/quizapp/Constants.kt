package za.ac.iie.quizapp

object Constants {

    // TODO (STEP 1: Create constant variables which we require in the result screen.)
    // START
    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"
    // END

    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        // 1
        val que1 = Question(
            1, "True/False: South Africa was settled by Europeans in the 17th century.",
            R.drawable.europeans,
            "True", "False", 1
        )
        questionsList.add(que1)

        // 2
        val que2 = Question(
            2, "True/False: Nelson Mandela became president in 1994.",
            R.drawable.nelson_mandela_president,
            "True", "False", 1
        )
        questionsList.add(que2)

        // 3
        val que3 = Question(
            3, "True/False: The first European settlers in South Africa were British.",
            R.drawable.first_settlers,
            "True", "False", 2
        )
        questionsList.add(que3)

        // 4
        val que4 = Question(
            4, "True/False: Nelson Mandela was the first democratically elected president of South Africa.",
            R.drawable.democratic,
            "True", "False", 1
        )
        questionsList.add(que4)

        // 5
        val que5 = Question(
            5, "True/False: The gold rush in South Africa occurred primarily in Durban.",
            R.drawable.kimberley,
            "True", "False", 2
        )
        questionsList.add(que5)

        return questionsList
    }
}