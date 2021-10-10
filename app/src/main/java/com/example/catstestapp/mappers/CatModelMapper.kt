package com.example.catstestapp.mappers

import com.example.catstestapp.models.Cat
import com.example.catstestapp.models.CatResponse

class CatModelMapper {
    operator fun invoke(catResponse: CatResponse): Cat =
        with(catResponse){
            Cat(url = url)
        }
}

