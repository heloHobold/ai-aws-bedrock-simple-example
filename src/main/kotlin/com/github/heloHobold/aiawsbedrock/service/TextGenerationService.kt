package com.github.heloHobold.aiawsbedrock.service

import aws.sdk.kotlin.services.bedrockruntime.BedrockRuntimeClient
import com.github.heloHobold.aiawsbedrock.controller.dto.PromptInput
import com.github.heloHobold.aiawsbedrock.service.aimodels.Jurassic2Mid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TextGenerationService(private val bedrockRuntimeClient: BedrockRuntimeClient) {
    private val logger = LoggerFactory.getLogger(TextGenerationService::class.java)

     suspend fun invoke(promptInput: PromptInput): String {
        return try {
            logger.info("Invoking Jurassic2Mid model")
            Jurassic2Mid.invoke(bedrockRuntimeClient, promptInput.prompt, promptInput.temperature, promptInput.maxTokens)
        } catch (e: AccessDeniedException) {
            logger.error("Access denied when invoking Jurassic2Mid model: ${e.message}")
            throw ResponseStatusException(HttpStatus.FORBIDDEN)
        } catch (e: Exception) {
            logger.error("Error invoking Jurassic2Mid model: ${e.message}")
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}