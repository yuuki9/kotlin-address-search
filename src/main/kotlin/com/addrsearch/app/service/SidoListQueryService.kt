package com.addrsearch.app.service

import com.addrsearch.app.dto.SidoResponse
import com.addrsearch.app.repository.AddressRepository
import org.springframework.stereotype.Service

@Service
class SidoListQueryService (
    private val addressRepository: AddressRepository
) {
    //시도 리스트 가져오기
    fun getSidoList(): List<SidoResponse> {
        return addressRepository.sidoList()
    }
}