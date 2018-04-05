package com.iacovelli.moviesapp

class ReadFixture {
    fun execute(filePath: String): String {
        return javaClass.classLoader.getResource(filePath).readText()
    }
}