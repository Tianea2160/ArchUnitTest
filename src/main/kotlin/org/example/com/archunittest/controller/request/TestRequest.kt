package org.example.com.archunittest.controller.request

import io.swagger.v3.oas.annotations.media.Schema

class TestRequest(
    @field:Schema val content: String
)