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

	@Test
	fun `Elasticsearch 실제 연동 테스트`() {
		// given
		val keyword = "서초대로"

		// when
		val results = keywordAddressSearchService.execute(keyword)

		// then
        assertTrue(results.any { it.fullAddress.contains("서초대로") })
	}


}