package org.example.com.archunittest.controller

import io.swagger.v3.oas.annotations.Operation
import org.example.com.archunittest.controller.request.TestRequest
import org.example.com.archunittest.controller.response.TestResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @Operation(summary = "Test Content")
    @PostMapping("/test")
    fun test(@RequestBody request: TestRequest) = TestResponse(request.content)
}