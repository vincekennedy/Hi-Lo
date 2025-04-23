package com.hi_lo.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.hi_lo.viewmodel.Golfer
import com.hi_lo.viewmodel.MatchData
import com.hi_lo.viewmodel.Team
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object MatchDataSerializer : Serializer<MatchData> {
    override val defaultValue: MatchData = MatchData(
        team1 = Team(Golfer("", 0), Golfer("", 0)),
        team2 = Team(Golfer("", 0), Golfer("", 0)),
        pricePerPoint = 0,
    )

    override suspend fun readFrom(input: InputStream): MatchData {
        return try {
            Json.decodeFromString(MatchData.serializer(), input.readBytes().decodeToString())
        } catch (e: SerializationException) {
            throw CorruptionException("Error reading MatchData", e)
        }
    }

    override suspend fun writeTo(t: MatchData, output: OutputStream) {
        output.write(Json.encodeToString(MatchData.serializer(), t).encodeToByteArray())
    }
}
