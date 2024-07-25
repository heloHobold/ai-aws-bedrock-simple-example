package com.github.heloHobold.aiawsbedrock.controller.dto

data class PromptInput (
    val prompt: String,
    val temperature: Double,
    val maxTokens: Int
)