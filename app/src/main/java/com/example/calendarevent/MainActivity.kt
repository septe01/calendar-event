package com.example.calendarevent

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import org.threeten.bp.LocalDate
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var eventAdapter: EventAdapter

    private lateinit var thisDay: ArrayList<EventData>

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onData()
        onView()
        onEvent()
    }

    private fun onEvent() {
        ccvSchedule.setListener(object: CompactCalendarView.CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date?) {
                onSelected(dateClicked!!)
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date?) {
                Log.e("TAG", firstDayOfNewMonth?.toString()!!)
            }
        })
    }

    private fun onData() {
        var base = ""
        Utils.getEvents().forEach {
            val arrayList = ArrayList<Decorator>()

            val date: LocalDate = LocalDate.parse(it.date)

            val localDate: CalendarDay = CalendarDay.from(LocalDate.of(date.year, date.month.value, date.dayOfMonth))

            when (it.date != base) {
                true -> {
                    if (arrayList.isNotEmpty()) mcvSchedule.addDecorators(arrayList)

                    arrayList.clear()
                }

                else -> {
                    val decorator = Decorator(R.color.colorPrimary, (arrayList.size - 1))
                    decorator.addDate(localDate)

                    when (it.type) {
                        Utils.leave -> {
                            decorator.color = android.R.color.holo_red_dark
                            arrayList.add(decorator)
                        }
                        Utils.event -> {
                            decorator.color = R.color.colorAccent
                            arrayList.add(decorator)
                        }
                        else -> arrayList.add(decorator)
                    }
                }
            }

            base = it.date
        }

        ccvSchedule.setFirstDayOfWeek(Calendar.SUNDAY)

        Utils.getEvents().forEach {
            val date: Date = dateFormat.parse(it.date)!!

            ccvSchedule.addEvent(Event(ContextCompat.getColor(this, it.color), date.time))
        }

        eventAdapter = EventAdapter(this)
    }

    private fun onView() {
        rvEvents.adapter = eventAdapter

        onSelected(Date())

        val bottomSheet: BottomSheetBehavior<CoordinatorLayout> = BottomSheetBehavior.from(clBottomSheet)
        bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheet.isHideable = false

        clBottomSheet.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                clBottomSheet.viewTreeObserver.removeOnGlobalLayoutListener(this)

                bottomSheet.peekHeight = clBottomSheet.height / 2
            }
        })
    }

    private fun onSelected(date: Date) {
        val dateTime: String = dateFormat.format(date)

        thisDay = Utils.getEvents().filter { return@filter it.date == dateTime } as ArrayList<EventData>

        eventAdapter.data = thisDay
        eventAdapter.notifyDataSetChanged()

        when {
            thisDay.isEmpty() -> {
                tvEmpty.visibility = View.VISIBLE
                rvEvents.visibility = View.GONE
            }

            else -> {
                tvEmpty.visibility = View.GONE
                rvEvents.visibility = View.VISIBLE
            }
        }
    }
}
