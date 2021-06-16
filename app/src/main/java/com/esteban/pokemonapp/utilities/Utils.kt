package com.esteban.pokemonapp.utilities

import android.location.Location
import android.util.Log
import com.esteban.pokemonapp.data.pokemon.Move
import com.esteban.pokemonapp.data.token.TokenEntity
import org.osmdroid.util.GeoPoint
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun shouldFetchToken(tokenEntity: TokenEntity?): Boolean {
        return if (tokenEntity == null) {
            true
        } else System.currentTimeMillis() > tokenEntity.expiresAt
    }

    fun formatTimeAgo(date1: String): String {
        var conversionTime = ""
        try {
            val format = "yyyy-MM-dd'T'HH:mm:ssZ"

            val dateFormatter = SimpleDateFormat(format, Locale.getDefault())

            val datetime = Calendar.getInstance()
            var date2 = dateFormatter.format(datetime.time).toString()

            val dateObj1 = dateFormatter.parse(date1)
            val dateObj2 = dateFormatter.parse(date2)
            val diff = dateObj2.time - dateObj1.time

            val diffDays = diff / (24 * 60 * 60 * 1000)
            val diffHours = diff / (60 * 60 * 1000)
            val diffMin = diff / (60 * 1000)
            val diffSec = diff / 1000
            if (diffDays > 1) {
                conversionTime += "$diffDays days "
            } else if (diffHours > 1) {
                conversionTime += (diffHours - diffDays * 24).toString() + " hours "
            } else if (diffMin > 1) {
                conversionTime += (diffMin - diffHours * 60).toString() + " min "
            } else if (diffSec > 1) {
                conversionTime += (diffSec - diffMin * 60).toString() + " sec "
            }
        } catch (ex: java.lang.Exception) {
            Log.e("formatTimeAgo", ex.toString())
        }
        if (conversionTime != "") {
            conversionTime += "ago"
        }
        return conversionTime
    }

    fun formatToReadableDate(inputDate: String): String {
        var formattedDate = ""
        try {
            val formatInput = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
            val formatOutput = "MMM, d, ',' yyyy"

            val dateFormatterInput = SimpleDateFormat(formatInput, Locale.getDefault())
            val dateFormatterOutput = SimpleDateFormat(formatOutput, Locale.getDefault())
            var date = dateFormatterInput.format(inputDate)

            formattedDate = dateFormatterOutput.format(date).toString()


        } catch (ex: java.lang.Exception) {
            Log.e("formatToReadableDate", ex.toString())
            formattedDate = inputDate
        }
        return formattedDate
    }

    fun getRandomMoves(moves: List<Move>): List<Move> {
        var randomMoves = moves.shuffled().take(4)
        for (item in randomMoves){
            item.nestedMove.level = getRandomMovesLevel()
        }
        return randomMoves
    }

    fun getRandomMovesLevel(): String {
        val max = (1..20).random()
        val total = (1..max).random()
        return "$total/$max"
    }

    fun getLocationInLatLngRad(radiusInMeters: Double, geoPoint: GeoPoint): GeoPoint? {
        val x0: Double = geoPoint.getLongitude()
        val y0: Double = geoPoint.getLatitude()
        val random = Random()

        // Convert radius from meters to degrees.
        val radiusInDegrees = radiusInMeters / 111320f

        // Get a random distance and a random angle.
        val u = random.nextDouble()
        val v = random.nextDouble()
        val w = radiusInDegrees * Math.sqrt(u)
        val t = 2 * Math.PI * v
        // Get the x and y delta values.
        val x = w * Math.cos(t)
        val y = w * Math.sin(t)

        // Compensate the x value.
        val new_x = x / Math.cos(Math.toRadians(y0))
        val foundLatitude: Double
        val foundLongitude: Double
        foundLatitude = y0 + y
        foundLongitude = x0 + new_x
        val copy = GeoPoint(foundLatitude, foundLongitude)
//        copy.setLatitude(foundLatitude)
//        copy.setLongitude(foundLongitude)
        return copy
    }
}