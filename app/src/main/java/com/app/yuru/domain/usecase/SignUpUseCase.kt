package com.app.yuru.domain.usecase

import com.app.yuru.coreandroid.exception.Failure
import com.app.yuru.coreandroid.functional.Either
import com.app.yuru.coreandroid.usecase.UseCase
import com.app.yuru.data.repository.YuruRepository
import com.app.yuru.domain.entity.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: YuruRepository) :
    UseCase<SignUpResponse, SignUpUseCase.SignUpParams>() {

    override suspend fun run(params: SignUpParams): Either<Failure, SignUpResponse> =
        withContext(Dispatchers.IO) {
            repository.register(params.fullName, params.email, params.password)
        }

    data class SignUpParams(
        val fullName: String,
        val email: String,
        val password: String,
    )
}