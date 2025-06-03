package com.addrsearch.app.integration

import com.addrsearch.app.service.KeywordAddressSearchServiceImpl
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertTrue

@SpringBootTest
class KotlinAddressSearchApplicationTests {
	@Autowired
	lateinit var keywordAddressSearchService: KeywordAddressSearchServiceImpl
	
}