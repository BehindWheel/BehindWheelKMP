package com.egoriku.grodnoroads.setting.faq.data.repository

import com.egoriku.grodnoroads.extensions.await
import com.egoriku.grodnoroads.extensions.common.ResultOf.Failure
import com.egoriku.grodnoroads.extensions.common.ResultOf.Success
import com.egoriku.grodnoroads.setting.faq.data.dto.FaqDTO
import com.egoriku.grodnoroads.setting.faq.domain.model.FAQ
import com.egoriku.grodnoroads.setting.faq.domain.repository.FaqRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class FaqRepositoryImpl(
    private val firestore: FirebaseFirestore
) : FaqRepository {

    override suspend fun load() = withContext(Dispatchers.IO) {
        val faqResponse = firestore
            .collection("faq")
            .orderBy("id")
            .await<FaqDTO>()

        return@withContext when (faqResponse) {
            is Failure -> Failure(faqResponse.throwable)
            is Success -> Success(faqResponse.value.map {
                FAQ(
                    question = it.question,
                    answer = it.answer.replace("\\n", "\n")
                )
            })
        }
    }
}