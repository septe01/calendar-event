package com.example.calendarevent

class Utils {

    companion object {

        const val event = "Event"
        const val shift = "Shift"
        const val leave = "Leave"

        fun getEvents(): ArrayList<EventData> {
            val arrayList = ArrayList<EventData>()
            arrayList.add(EventData("2019-11-20", "Meeting", event, "06:00", "11:00", R.color.colorAccent))
            arrayList.add(EventData("2019-11-20", "Sakit", leave, "06:00", "11:00", android.R.color.holo_red_dark))
            arrayList.add(EventData("2019-11-21", "Shift - Pagi", shift, "06:00", "11:00", R.color.colorPrimary))
            arrayList.add(EventData("2019-11-21", "Shift - Siang", shift, "06:00", "11:00", R.color.colorPrimary))
            arrayList.add(EventData("2019-11-22", "Sakit", leave, "06:00", "11:00", android.R.color.holo_green_dark))
            arrayList.sortBy { it.date.reversed() }
            return arrayList
        }
    }
}