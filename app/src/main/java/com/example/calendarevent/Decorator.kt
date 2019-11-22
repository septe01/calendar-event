package com.example.calendarevent

import android.text.style.LineBackgroundSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class Decorator(var color: Int, var spanType: Int) : DayViewDecorator {
    private val dates: HashSet<CalendarDay> = HashSet()

    fun addDate(day: CalendarDay): Boolean {
        return dates.add(day)
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        val span: LineBackgroundSpan = CustomSpan(color, xOffsets[spanType])
        view.addSpan(span)
    }

    companion object {
        private val xOffsets = intArrayOf(0, -10, 10, -20)
    }
}