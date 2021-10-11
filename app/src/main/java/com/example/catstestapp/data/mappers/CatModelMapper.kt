package com.example.catstestapp.data.mappers

import com.example.catstestapp.domain.models.Cat
import com.example.catstestapp.data.responses.CatResponse

class CatModelMapper {
    operator fun invoke(catResponse: CatResponse): Cat =
        with(catResponse){
            Cat(url = url)
        }
}

