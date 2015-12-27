//
//  QCDummyQueuePersister.m
//  Queues
//
//  Created by Alex on 16/05/2015.
//  Copyright (c) 2015 Alex Curran. All rights reserved.
//

#import "QCDummyQueuePersister.h"
#import "Queue.h"
#import "QueueItem.h"
#import "java/util/List.h"
#import "java/util/ArrayList.h"
#import "IOSClass.h"
#import <CoreData/CoreData.h>

@interface QCDummyQueuePersister ()

@property (nonatomic, assign) NSInteger queueIndex;
@property (nonatomic, strong) NSManagedObjectContext *coreDataContext;

@end

@implementation QCDummyQueuePersister

- (instancetype)init
{
    self = [super init];
    if (self) {
        NSURL *modelUrl = [[NSBundle mainBundle] URLForResource:@"QueueModel" withExtension:@"momd"];
        NSManagedObjectModel *mom = [[NSManagedObjectModel alloc] initWithContentsOfURL:modelUrl];
        NSPersistentStoreCoordinator *coordinator = [[NSPersistentStoreCoordinator alloc] initWithManagedObjectModel:mom];
        NSURL *documentsURL = [[[NSFileManager defaultManager] URLsForDirectory:NSDocumentDirectory inDomains:NSUserDomainMask] lastObject];
        NSURL *storeURL = [documentsURL URLByAppendingPathComponent:@"DataModel.sqlite"];
        NSError *error;
        [coordinator addPersistentStoreWithType:NSSQLiteStoreType configuration:nil URL:storeURL options:nil error:&error];
        
        if (error) {
            NSLog(error);
        }
        
        self.coreDataContext = [[NSManagedObjectContext alloc] initWithConcurrencyType:NSPrivateQueueConcurrencyType];
        [self.coreDataContext setPersistentStoreCoordinator:coordinator];
        QCQueueItem *firstItem = [[QCQueueItem alloc] initWithNSString:@"an id" withNSString:@"label"];
        JavaUtilArrayList *queueItemList = [[JavaUtilArrayList alloc] init];
        [queueItemList addWithId:firstItem];
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
    NSFetchRequest *request = [[NSFetchRequest alloc] init];
    NSEntityDescription *entity = [NSEntityDescription entityForName:@"Queue" inManagedObjectContext:self.coreDataContext];
    NSError *error;
    [request setEntity:entity];
    NSArray *response = [self.coreDataContext executeFetchRequest:request error:&error];
    NSArray *sorted = [response sortedArrayWithOptions:NSSortConcurrent usingComparator:^NSComparisonResult(id  _Nonnull obj1, id  _Nonnull obj2) {
        NSString *title1 = [((NSManagedObject *) obj1) valueForKey:@"title"];
        NSString *title2 = [((NSManagedObject *) obj2) valueForKey:@"title"];
        return [title1 compare:title2];
    }];
    JavaUtilArrayList *arrayList = [[JavaUtilArrayList alloc] init];
    if (!error) {
        [sorted enumerateObjectsWithOptions:NSEnumerationConcurrent usingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
            NSManagedObject *object = (NSManagedObject *)obj;
            NSString *idString = [NSString stringWithFormat:@"%@", [[object objectID] URIRepresentation]];
            QCQueue *queue = [[QCQueue alloc] initWithNSString:[object valueForKey:@"title"] withNSString:idString withQCQueuePersister:self withJavaUtilList:[[JavaUtilArrayList alloc] init]];
            [arrayList addWithId:queue];
        }];
    }
    [callbacks loadedWithJavaUtilList:arrayList];
}

- (QCQueue *)saveQueueWithQCQueue:(QCQueue *)queue
withQCQueuePersister_Callbacks:(id<QCQueuePersister_Callbacks>)callbacks
{
    NSManagedObject *newQueue = [NSEntityDescription insertNewObjectForEntityForName:@"Queue" inManagedObjectContext:self.coreDataContext];
    [newQueue setValue:[queue getTitle] forKey:@"title"];
    NSError *error;
    if ([self.coreDataContext save:&error]) {
        [callbacks failedToSaveWithQCQueue:queue];
    }
    return queue;
}

- (NSString *)uniqueId
{
    NSInteger nextIndex = self.queueIndex++;
    self.queueIndex = nextIndex;
    return nil;
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
