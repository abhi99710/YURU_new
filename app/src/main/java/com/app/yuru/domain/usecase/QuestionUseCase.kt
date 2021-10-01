package com.app.yuru.domain.usecase


import com.app.yuru.domain.entity.Json4Kotlin_Base
import com.app.yuru.coreandroid.exception.Failure
import com.app.yuru.coreandroid.functional.Either

import com.app.yuru.coreandroid.usecase.UseCase
import com.app.yuru.data.repository.YuruRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuestionUseCase @Inject constructor(private val repository: YuruRepository) :
    UseCase<Json4Kotlin_Base, QuestionUseCase.QuestionSParams>() {

    override suspend fun run(params: QuestionSParams): Either<Failure, Json4Kotlin_Base> =
        withContext(Dispatchers.IO) {
            repository.getQuestions(params.category_id)
        }

    data class QuestionSParams(
        val category_id : String
    )
}