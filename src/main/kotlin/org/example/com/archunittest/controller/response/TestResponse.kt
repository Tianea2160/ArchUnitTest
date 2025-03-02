package org.example.com.archunittest.controller.response

import io.swagger.v3.oas.annotations.media.Schema

class TestResponse(
    @field:Schema(description = "내용")
    val content: String,
)