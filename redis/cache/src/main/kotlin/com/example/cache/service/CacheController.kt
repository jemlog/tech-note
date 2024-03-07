package com.example.cache.service

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CacheController(
    val cacheService: CacheService
) {

    @GetMapping("/api/v1/test")
    fun test(): Test{
        return cacheService.get(2L)
    }
}