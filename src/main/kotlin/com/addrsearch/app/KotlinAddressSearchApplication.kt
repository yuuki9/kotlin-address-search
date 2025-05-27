package com.addrsearch.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinAddressSearchApplication

fun main(args: Array<String>) {
	runApplication<KotlinAddressSearchApplication>(*args)
}
