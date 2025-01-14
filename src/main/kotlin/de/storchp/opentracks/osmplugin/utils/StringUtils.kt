package de.storchp.opentracks.osmplugin.utils

import android.content.Context
import android.text.format.DateUtils
import de.storchp.opentracks.osmplugin.R
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.time.Duration

/**
 * Various string manipulation methods.
 */
object StringUtils {
    /**
     * Formats the elapsed time in the form "H:MM".
     */
    fun formatElapsedTimeHoursMinutes(context: Context, duration: Duration): String {
        return context.getString(
            R.string.stat_time,
            DateUtils.formatElapsedTime(duration.inWholeSeconds)
        )
    }

    fun formatDistanceInKm(context: Context, distanceMeter: Double): String {
        return context.getString(
            R.string.stat_distance_with_unit,
            formatDecimal(distanceMeter * UnitConversions.M_TO_KM),
            context.getString(R.string.unit_kilometer)
        )
    }

    fun formatDistanceInMi(context: Context, distanceMeter: Double): String {
        return context.getString(
            R.string.stat_distance_with_unit,
            formatDecimal(distanceMeter * UnitConversions.M_TO_MI),
            context.getString(R.string.unit_mile)
        )
    }

    fun formatSpeedInKmPerHour(context: Context, meterPerSeconds: Double): String {
        return context.getString(
            R.string.stat_distance_with_unit,
            formatDecimal(meterPerSeconds * UnitConversions.MS_TO_KMH),
            context.getString(R.string.unit_kilometer_per_hour)
        )
    }

    fun formatSpeedInMiPerHour(context: Context, meterPerSeconds: Double): String {
        return context.getString(
            R.string.stat_distance_with_unit,
            formatDecimal(meterPerSeconds * UnitConversions.MS_TO_KMH * UnitConversions.KM_TO_MI),
            context.getString(R.string.unit_mile_per_hour)
        )
    }

    fun formatPaceMinPerKm(context: Context, meterPerSeconds: Double): String {
        if (meterPerSeconds == 0.0) {
            return "0:00"
        }
        val kmPerSecond = meterPerSeconds / UnitConversions.KM_TO_M.toFloat()
        val secondsPerKm = (1 / kmPerSecond).toInt()
        val minutes = (secondsPerKm / UnitConversions.MIN_TO_S).toInt()
        val seconds = secondsPerKm % UnitConversions.MIN_TO_S.toInt()

        return context.getString(
            R.string.stat_distance_with_unit,
            context.getString(R.string.stat_minute_seconds, minutes, seconds),
            context.getString(R.string.unit_minute_per_kilometer)
        )
    }

    fun formatPaceMinPerMi(context: Context, meterPerSeconds: Double): String {
        if (meterPerSeconds == 0.0) {
            return "0:00"
        }
        val kmPerSecond = (meterPerSeconds / UnitConversions.KM_TO_M) * UnitConversions.KM_TO_MI
        val secondsPerKm = (1 / kmPerSecond).toInt()
        val minutes = (secondsPerKm / UnitConversions.MIN_TO_S).toInt()
        val seconds = secondsPerKm % UnitConversions.MIN_TO_S.toInt()

        return context.getString(
            R.string.stat_distance_with_unit,
            context.getString(R.string.stat_minute_seconds, minutes, seconds),
            context.getString(R.string.unit_minute_per_mile)
        )
    }

    private fun formatDecimal(value: Double): String {
        return formatDecimal(value, 2)
    }

    /**
     * Format a decimal number while removing trailing zeros of the decimal part (if present).
     */
    fun formatDecimal(value: Double, decimalPlaces: Int): String {
        val df = DecimalFormat()
        df.setMinimumFractionDigits(decimalPlaces)
        df.setMaximumFractionDigits(decimalPlaces)
        df.setRoundingMode(RoundingMode.HALF_EVEN)
        return df.format(value)
    }

    fun formatAltitudeChangeInMeter(context: Context, altitudeInMeter: Double): String {
        return context.getString(
            R.string.stat_altitude_with_unit,
            altitudeInMeter.toInt().toString(),
            context.getString(R.string.unit_meter)
        )
    }

    fun formatAltitudeChangeInFeet(context: Context, altitudeInMeter: Double): String {
        return context.getString(
            R.string.stat_altitude_with_unit,
            (altitudeInMeter * UnitConversions.M_TO_FT).toInt().toString(),
            context.getString(R.string.unit_feet)
        )
    }
}
