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

@interface QCDummyQueuePersister ()

@property (nonatomic, strong) NSMutableArray *queues;
@property (nonatomic, assign) NSInteger queueIndex;

@end

@implementation QCDummyQueuePersister

- (instancetype)init
{
    self = [super init];
    if (self) {
        _queues = [[NSMutableArray alloc] init];
        QCQueue *queue = [[QCQueue alloc] initWithNSString:@"Hard-coded queue" withNSString:@"something" withQCQueuePersister:self withJavaUtilList:nil];
        [_queues addObject:queue];
    }
    return self;
}

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
    JavaUtilArrayList *arrayList = [[JavaUtilArrayList alloc] init];
    [self.queues enumerateObjectsWithOptions:NSEnumerationConcurrent usingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
        [arrayList addWithId:obj];
    }];
    
    [callbacks loadedWithJavaUtilList:arrayList];
}

- (void)saveQueueWithQCQueue:(QCQueue *)queue
withQCQueuePersister_Callbacks:(id<QCQueuePersister_Callbacks>)callbacks
{
    [self.queues addObject:queue];
}

- (NSString *)uniqueId
{
    NSInteger nextIndex = self.queueIndex++;
    self.queueIndex = nextIndex;
    return [NSString stringWithFormat:@"%ld", (long)nextIndex];
}

- (NSString *)uniqueItemId
{
    return @"1001";
}

- (void)deleteQueueWithQCQueue:(QCQueue *)queue
withQCQueuePersister_Callbacks:(id<QCQueuePersister_Callbacks>)callbacks;
{
    [self.queues removeObject:queue];
}

- (jboolean)requiresUserIntervention
{
    return false;
}

@end
