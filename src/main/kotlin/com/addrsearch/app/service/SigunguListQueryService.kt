package com.addrsearch.app.service

import com.addrsearch.app.dto.SigunguResponse
import com.addrsearch.app.repository.AddressRepository
import org.springframework.stereotype.Service

@Service
class SigunguListQueryService (
    private val addressRepository: AddressRepository
){
    fun execute(id : Long): List<SigunguResponse> {
        return addressRepository.sigunguList(id)
    }

}