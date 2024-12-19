package com.example.vivemurcia.model.fechas

import java.util.Calendar
import java.util.Date

class Calendario {

    companion object {
        fun getDayOfMonth(date: Date?): Int {
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar.get(Calendar.DAY_OF_MONTH)
        }
    }

}