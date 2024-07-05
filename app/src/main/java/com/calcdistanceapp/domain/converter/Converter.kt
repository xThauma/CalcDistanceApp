package com.calcdistanceapp.domain.converter

interface Converter<in FROM, out TO> {
    fun convert(from: FROM): TO
}