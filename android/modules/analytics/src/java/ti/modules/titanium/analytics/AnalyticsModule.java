/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2014 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.modules.titanium.analytics;

//import org.appcelerator.kroll.KrollDate;
import java.util.Date;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.TiContext;
import org.appcelerator.titanium.analytics.TiAnalyticsEventFactory;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.util.TiPlatformHelper;
import org.json.JSONException;
import org.json.JSONObject;

import com.appcelerator.analytics.APSAnalytics;
import com.appcelerator.analytics.APSAnalyticsEvent;

@Kroll.module
public class AnalyticsModule extends KrollModule
{
	protected static final String PROPERTY_APP_NAV = "app.nav";
	protected static final String PROPERTY_APP_TIMED = "app.timed";
	protected static final String PROPERTY_APP_FEATURE = "app.feature";
	protected static final String PROPERTY_APP_SETTINGS = "app.settings";
	protected static final String PROPERTY_APP_USER = "app.user";

	public AnalyticsModule()
	{
		super();
	}

	public AnalyticsModule(TiContext tiContext)
	{
		this();
	}

	@Kroll.method
	public void addEvent(String type, String event, @Kroll.argument(optional=true) KrollDict data)
	{
		localAddEvent(type, event, data);
	}

	protected void localAddEvent(String type, String event, KrollDict data) {
		String dataJSON = "";
		if (data != null) {
			dataJSON = TiConvert.toJSONString(data).toString();
		}
		
		if (TiApplication.getInstance().isAnalyticsEnabled()) {
			TiPlatformHelper.getInstance().postAnalyticsEvent(TiAnalyticsEventFactory.createEvent(type, event, dataJSON));
		}
	}

	@Kroll.method
	public void navEvent(String from, String to,
		@Kroll.argument(optional=true) String event,
		@Kroll.argument(optional=true) KrollDict data)
	{
		KrollDict payload = new KrollDict();
		payload.put(TiC.PROPERTY_FROM, from);
		payload.put(TiC.PROPERTY_TO, to);
		payload.put(TiC.PROPERTY_EVENT, event);
		payload.put(TiC.PROPERTY_DATA, data);

		if (TiApplication.getInstance().isAnalyticsEnabled()) {
			APSAnalytics.sendAppNavEvent(from, to, event, TiConvert.toJSON(data));
		}
	}

	@Kroll.method
	public void timedEvent(String event, Object start, Object stop, int duration,
		@Kroll.argument(optional=true) KrollDict data)
	{
		KrollDict payload = new KrollDict();
		payload.put(TiC.PROPERTY_EVENT, event);
		if (start instanceof Number) {
			payload.put(TiC.PROPERTY_START, ((Number) start).longValue());
//		} else if (start instanceof KrollDate) {
		} else if (start instanceof Date) {
			//payload.put(TiC.PROPERTY_START, ((KrollDate) start).getTime());
			payload.put(TiC.PROPERTY_START, ((Date) start).getTime());
		} else {
			throw new IllegalArgumentException("start must be a long or Date.");
		}
		
		if (stop instanceof Number) {
			payload.put(TiC.PROPERTY_STOP, ((Number) stop).longValue());			
//		} else if (stop instanceof KrollDate) {
		} else if (stop instanceof Date) {
			//payload.put(TiC.PROPERTY_STOP, ((KrollDate) start).getTime());
			payload.put(TiC.PROPERTY_STOP, ((Date) start).getTime());
		} else {
			throw new IllegalArgumentException("stop must be a long or Date.");
		}
		
		payload.put(TiC.PROPERTY_DURATION, duration);
		payload.put(TiC.PROPERTY_DATA, data);

		localAddEvent(PROPERTY_APP_TIMED, payload.getString(TiC.PROPERTY_EVENT), payload);
	}

	@Kroll.method
	public void featureEvent(String event, @Kroll.argument(optional=true) KrollDict data) 
	{
		localAddEvent(PROPERTY_APP_FEATURE, event, data);
	}

	@Kroll.method
	public void settingsEvent(String event, @Kroll.argument(optional=true) KrollDict data) 
	{
		localAddEvent(PROPERTY_APP_SETTINGS, event, data);
	}

	@Kroll.method
	public void userEvent(String event, @Kroll.argument(optional=true) KrollDict data) 
	{
		localAddEvent(PROPERTY_APP_USER, event, data);
	}
	
	@Kroll.getProperty @Kroll.method
	public String getLastEvent()
	{
		try {
			APSAnalyticsEvent event = APSAnalytics.getLastAnalyticsEvent();
			if (event == null)
			{
				return null;
			}
			JSONObject json = new JSONObject();
			json.put("ver", "2");
			json.put("id", TiPlatformHelper.getInstance().getLastEventID());
			json.put("event", event.getEventType());
			json.put("ts", event.getEventTimestamp());
			json.put("mid", event.getEventMid());
			json.put("sid", event.getEventSid());
			json.put("aguid", event.getEventAppGuid());
			if ( event.mustExpandPayload() ) {
				json.put("data", new JSONObject(event.getEventPayload()));
			} else {
				json.put("data", event.getEventPayload());
			}
			return json.toString();
		} catch (JSONException e) {
			return null;
		}
	}

	@Override
	public String getApiName()
	{
		return "Ti.Analytics";
	}
}
