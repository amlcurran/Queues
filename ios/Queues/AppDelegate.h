//
//  AppDelegate.h
//  Queues
//
//  Created by Alex on 28/04/2015.
//  Copyright (c) 2015 Alex Curran. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreData/CoreData.h>
#import "QueueList.h"

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;
@property (strong, nonatomic) QCQueueList *queueList;

@end

