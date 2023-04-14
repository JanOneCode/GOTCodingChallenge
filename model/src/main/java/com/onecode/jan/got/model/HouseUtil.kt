package com.onecode.jan.got.model

import com.onecode.jan.got.model.api.ApiHouse
import com.onecode.jan.got.model.domain.DomainHouse

fun ApiHouse.toDomain(): DomainHouse = DomainHouse(
    url = this.url,
    name = this.name,
    region = this.region.returnStringOrNull(),
    coatOfArms = this.coatOfArms.returnStringOrNull(),
    words = this.words.returnStringOrNull(),
    titles = this.titles.returnListOrNull(),
    seats = this.seats.returnListOrNull(),
    currentLord = this.region.returnStringOrNull(),
    heir = this.region.returnStringOrNull(),
    overlord = this.region.returnStringOrNull(),
    founded = this.region.returnStringOrNull(),
    founder = this.region.returnStringOrNull(),
    diedOut = this.region.returnStringOrNull(),
    ancestralWeapons = this.ancestralWeapons.returnListOrNull(),
    cadetBranches = this.cadetBranches.returnListOrNull(),
    swornMembers = this.swornMembers.returnListOrNull()
)