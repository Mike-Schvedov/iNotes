package com.mikeschvedov.inotes.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import com.mikeschvedov.inotes.R
import com.mikeschvedov.inotes.data.database.Repository
import com.mikeschvedov.inotes.data.database.entities.Note
import com.mikeschvedov.inotes.ui.main.MainActivity
import com.mikeschvedov.inotes.utils.tbx_fromUnixToFormatted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NoteListWidget : AppWidgetProvider() {

    @Inject
    lateinit var repository: Repository

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, repository)
        }
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    repository: Repository
) {

    val views = RemoteViews(context.packageName, R.layout.note_list_widget)

    // -- Open App when we click on widget -- //

    val pendingIntent: PendingIntent = PendingIntent.getActivity(
        context,
        0,
        Intent(context, MainActivity::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    views.setOnClickPendingIntent(R.id.main_layout, pendingIntent)

    // -- Get all notes and display in xml -- //

    CoroutineScope(Dispatchers.IO).launch {
        val noteList = repository.getAllNotes()
        CoroutineScope(Dispatchers.Main).launch{
            setImprovisedAdapter(noteList, views, context)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

fun setImprovisedAdapter(noteList: List<Note>, views: RemoteViews, context: Context) {

    var viewCounter = 1

    val listOfViews = listOf(R.id.view1, R.id.view2,  R.id.view3,  R.id.view4,  R.id.view5,  R.id.view6,
        R.id.view7,  R.id.view8,  R.id.view9,  R.id.view10,)
    val listOfContents = listOf(R.id.content1, R.id.content2,R.id.content3,R.id.content4,R.id.content5,
        R.id.content6,R.id.content7,R.id.content8,R.id.content9,R.id.content10)
    val listOfDates = listOf(R.id.date1, R.id.date2, R.id.date3, R.id.date4, R.id.date5, R.id.date6,
        R.id.date7, R.id.date8, R.id.date9, R.id.date10)

    listOfViews.forEach {
        views.setViewVisibility(it, View.GONE)
    }

    noteList.forEach { note ->

        views.setTextViewText(listOfContents[viewCounter],  note.content)
        views.setTextViewText(listOfDates[viewCounter], note.date.tbx_fromUnixToFormatted())
        views.setViewVisibility(listOfViews[viewCounter], View.VISIBLE)

        viewCounter += 1
    }

}









