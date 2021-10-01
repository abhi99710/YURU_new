package com.app.yuru.domain.usecase

import com.app.yuru.coreandroid.exception.Failure
import com.app.yuru.coreandroid.functional.Either
import com.app.yuru.coreandroid.usecase.UseCase
import com.app.yuru.data.repository.YuruRepository
import com.app.yuru.domain.entity.QuestionResponseSubmit
import com.app.yuru.domain.entity.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SubmitResponseUseCase @Inject constructor(private val repository: YuruRepository) :
    UseCase<QuestionResponseSubmit, SubmitResponseUseCase.SubmitResponse>() {

    override suspend fun run(params: SubmitResponse): Either<Failure, QuestionResponseSubmit> =
        withContext(Dispatchers.IO) {
            repository.submitrating(params.user_id, params.answer)
        }

    data class SubmitResponse(
        val user_id: String,
        val answer: String,
    )
}
