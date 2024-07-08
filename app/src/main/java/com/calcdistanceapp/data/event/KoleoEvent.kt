package com.calcdistanceapp.data.event

import com.calcdistanceapp.domain.model.Station

sealed class KoleoEvent {

    sealed class UpdateStation : KoleoEvent() {
        data class Starting(val station: Station) : UpdateStation()
        data class Ending(val station: Station) : UpdateStation()
    }

    data class SearchStationByKeyword(val keyword: String) : KoleoEvent()
    data object FetchEvent : KoleoEvent()
}