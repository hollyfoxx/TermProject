package com.isitchristmas.android;

import java.util.Locale;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class ChristmasWidgetReceiver extends BroadcastReceiver {

	/**
	 * This receiver runs only on Christmas, at midnight.
	 *  
	 * It is meant to handle the case where a widget has already been added prior to Christmas,
	 * and to make it so that the widget updates precisely at midnight for the avid watcher.
	 * 
	 * If a widget is added on Christmas day, the correct answer will be set in the 
	 * ChristmasWidgetProvider's onUpdate method. 
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		/* Essentially gambling here that the widgets can all be updated within the few seconds
		 * that a BroadcastReceiver has to work with. If someone has like 1000 widgets somehow, then they 
		 * might conceivably see an ANR, and maybe I should have made a Service. I'm okay with that.
		 */
		int answerId = Christmas.answer(Christmas.isIt(), Locale.getDefault());
		String answer = context.getResources().getString(answerId);
        
        RemoteViews views = ChristmasWidgetProvider.buildView(context, answer);
        
        ComponentName widget = new ComponentName(context, ChristmasWidgetProvider.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		manager.updateAppWidget(widget, views);
	}
}
