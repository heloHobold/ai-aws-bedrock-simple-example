package com.github.heloHobold.aiawsbedrock.controller

import com.github.heloHobold.aiawsbedrock.service.FoundationModelsService
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ai-aws-bedrock/foundation-models")
class FoundationModels(private val foundationModelsService: FoundationModelsService){
    private val logger = LoggerFactory.getLogger(FoundationModels::class.java)

    @GetMapping
    suspend fun findAll(): ResponseEntity<List<String>> {
        return runBlocking {
            logger.info("Received request to find all foundation models")
            val models = foundationModelsService.findAll()
            ResponseEntity(models, HttpStatus.OK)
        }
    }

}