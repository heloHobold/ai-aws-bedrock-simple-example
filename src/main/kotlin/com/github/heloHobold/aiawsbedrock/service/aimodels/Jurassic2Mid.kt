package com.github.heloHobold.aiawsbedrock.service.aimodels

import aws.sdk.kotlin.services.bedrockruntime.BedrockRuntimeClient
import aws.sdk.kotlin.services.bedrockruntime.model.InvokeModelRequest
import org.json.JSONObject
import org.slf4j.LoggerFactory

object Jurassic2Mid {
    private const val MODEL_ID: String = "ai21.j2-mid-v1"

    private val logger = LoggerFactory.getLogger(Jurassic2Mid::class.java)

     suspend fun invoke(
        client: BedrockRuntimeClient,
        prompt: String,
        temperature: Double,
        maxTokens: Int
    ): String {
         logger.info("Invoking Jurassic2Mid model with prompt: $prompt")
         val jsonBody = JSONObject()
            .put("prompt", prompt)
            .put("temperature", temperature)
            .put("maxTokens", maxTokens)

         logger.info("Creating request to invoke Jurassic2Mid model")
         val request = InvokeModelRequest.invoke {
            modelId = MODEL_ID
            body = jsonBody.toString().toByteArray()
         }

         logger.info("Invoking Jurassic2Mid model")
         val response = client.invokeModel(request)

         logger.info("Parsing response from Jurassic2Mid model")
         var completion = JSONObject(String(response.body, Charsets.UTF_8))
            .getJSONArray("completions")
            .getJSONObject(0)
            .getJSONObject("data")
            .getString("text")

         if (completion.startsWith("\n")) {
            completion = completion.substring(1)
         }

         return completion
    }
}