package com.github.heloHobold.aiawsbedrock.controller

import com.github.heloHobold.aiawsbedrock.controller.dto.PromptInput
import com.github.heloHobold.aiawsbedrock.controller.dto.PromptOutput
import com.github.heloHobold.aiawsbedrock.service.TextGenerationService
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ai-aws-bedrock/text-generation")
class TextGenerationController(private val textGenerationService: TextGenerationService){
    private val logger = LoggerFactory.getLogger(TextGenerationController::class.java)

    @PostMapping("/jurassic2-mid-model")
    suspend fun invokeJurassic2MidModel(@RequestBody promptInput: PromptInput): ResponseEntity<PromptOutput> {
        return runBlocking {
            logger.info("Received request to generate text with Jurassic2Mid model")
            val generatedText = textGenerationService.invoke(promptInput)
            ResponseEntity(PromptOutput(generatedText), HttpStatus.OK)
        }
    }
}