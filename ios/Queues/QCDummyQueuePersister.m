//
//  QCDummyQueuePersister.m
//  Queues
//
//  Created by Alex on 16/05/2015.
//  Copyright (c) 2015 Alex Curran. All rights reserved.
//

#import "QCDummyQueuePersister.h"
#import "Queue.h"
#import "java/util/List.h"
#import "java/util/ArrayList.h"
#import "IOSClass.h"

@implementation QCDummyQueuePersister

- (void)addItemToQueueWithNSString:(NSString *)queueId
                   withQCQueueItem:(QCQueueItem *)queueItem
{
    
}

- (void)removeItemFromQueueWithNSString:(NSString *)queueId
                        withQCQueueItem:(QCQueueItem *)queueItem
{
    
}

- (void)queuesWithQCQueuePersister_LoadCallbacks:(id<QCQueuePersister_LoadCallbacks>)callbacks
{
    QCQueue *queue = [[QCQueue alloc] initWithNSString:@"Hard-coded queue" withNSString:@"something" withQCQueuePersister:self withJavaUtilList:nil];
    
    JavaUtilArrayList *arrayList = [[JavaUtilArrayList alloc] init];
    [arrayList addWithId:queue];
    
    [callbacks loadedWithJavaUtilList:arrayList];
}

- (void)saveQueueWithQCQueue:(QCQueue *)queue
withQCQueuePersister_Callbacks:(id<QCQueuePersister_Callbacks>)callbacks
{
    
}

- (NSString *)uniqueId
{
    return @"1";
}

- (NSString *)uniqueItemId
{
    return @"1001";
}

- (void)deleteQueueWithQCQueue:(QCQueue *)queue
withQCQueuePersister_Callbacks:(id<QCQueuePersister_Callbacks>)callbacks;
{
    
}

- (jboolean)requiresUserIntervention
{
    return false;
}

@end
