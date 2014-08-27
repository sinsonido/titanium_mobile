/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2014 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
#import "TiProxy.h"

#ifdef USE_TI_APPIOS

@interface TiAppiOSNotificationCategoryProxy : TiProxy {
@private
	UIMutableUserNotificationCategory *_notificationCategory;
}

@property(nonatomic,retain) UIMutableUserNotificationCategory *notificationCategory;

@end


#endif