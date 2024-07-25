package com.github.heloHobold.aiawsbedrock.config

import aws.sdk.kotlin.services.bedrock.BedrockClient
import aws.sdk.kotlin.services.bedrockruntime.BedrockRuntimeClient
import aws.smithy.kotlin.runtime.auth.awscredentials.Credentials
import aws.smithy.kotlin.runtime.auth.awscredentials.CredentialsProvider
import aws.smithy.kotlin.runtime.collections.Attributes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ConfigAWS {
    private val userRegion = System.getenv("AWS_REGION") ?: ""
    private val userCredentials = object : CredentialsProvider {
        override suspend fun resolve(attributes: Attributes): Credentials {
            return Credentials(
                accessKeyId = System.getenv("AWS_ACCESS_KEY_ID") ?: "",
                secretAccessKey = System.getenv("AWS_SECRET_ACCESS_KEY") ?: ""
            )
        }
    }

    @Bean
    fun bedrockClient(): BedrockClient {
        return BedrockClient {
            region = userRegion
            credentialsProvider = userCredentials
        }
    }

    @Bean
    fun bedrockRuntimeClient(): BedrockRuntimeClient {
        return BedrockRuntimeClient {
            region = userRegion
            credentialsProvider = userCredentials
        }
    }

    @Bean
    fun corsConfig(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                // Enable CORS for all domains, remember to change this for production purposes
                registry.addMapping("/**").allowedOrigins("*")
            }
        }
    }
}