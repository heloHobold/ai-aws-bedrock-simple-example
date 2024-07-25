package com.github.heloHobold.aiawsbedrock.service

import aws.sdk.kotlin.services.bedrock.BedrockClient
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class FoundationModelsService(private val bedrockClient: BedrockClient) {
    private val logger = LoggerFactory.getLogger(FoundationModelsService::class.java)

    suspend fun findAll(): List<String>? {
        return try {
            logger.info("Finding all foundation models")
            bedrockClient.listFoundationModels().modelSummaries?.map { it.modelId }
        } catch (e: AccessDeniedException) {
            logger.error("Access denied: ${e.message}")
            throw ResponseStatusException(HttpStatus.FORBIDDEN)
        } catch (e: Exception) {
            logger.error("Error: ${e.message}")
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}