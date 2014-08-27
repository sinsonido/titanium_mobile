/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2014 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiAppiOSNotificationActionProxy.h"
#import "TiUtils.h"

#ifdef USE_TI_APPIOS

@implementation TiAppiOSNotificationActionProxy

@synthesize notificationAction = _notificationAction;

-(void)dealloc
{
	RELEASE_TO_NIL(_notificationAction);
	[super dealloc];
}

-(NSString*)apiName
{
	return @"Ti.App.iOS.Notification";
}

@end

#endif
