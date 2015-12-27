//
//  CoreDataPersister.swift
//  Queues
//
//  Created by Alex on 27/12/2015.
//  Copyright © 2015 Alex Curran. All rights reserved.
//

import UIKit
import CoreData
import Foundation

class CoreDataPersister: NSObject, QCQueuePersister {
    
    let managedObjectContext : NSManagedObjectContext
    
    override init() {
        let modelUrl = NSBundle.mainBundle().URLForResource("QueueModel", withExtension: "momd")
        let model = NSManagedObjectModel.init(contentsOfURL: modelUrl!)
        self.managedObjectContext = NSManagedObjectContext(concurrencyType: .MainQueueConcurrencyType)
        self.managedObjectContext.persistentStoreCoordinator = NSPersistentStoreCoordinator(managedObjectModel: model!)
        do {
            let documentsUrl = NSFileManager.defaultManager().URLsForDirectory(.DocumentDirectory, inDomains: .UserDomainMask)[0]
            let storeUrl = documentsUrl .URLByAppendingPathComponent("DataModel2.sqlite")
            try self.managedObjectContext.persistentStoreCoordinator?.addPersistentStoreWithType(NSSQLiteStoreType, configuration: nil, URL: storeUrl, options: nil)
        } catch let error as NSError {
            NSLog("%@", error)
        }
        
    }
    
    func uniqueId() -> String! {
        return ""
    }
    
    func uniqueItemId() -> String! {
        return ""
    }
    
    func requiresUserIntervention() -> jboolean {
        return false
    }

    func queuesWithQCQueuePersister_LoadCallbacks(callbacks: QCQueuePersister_LoadCallbacks!) {
        let fetchRequest = NSFetchRequest(entityName: "Queue")
        do {
            let list = JavaUtilArrayList()
            let results = try managedObjectContext.executeFetchRequest(fetchRequest)
            for item in results as! [Queue] {
                let queueItems = translateItems(item.items ?? NSSet())
                let fakeQueue = QCQueue(NSString: item.title, withNSString: "\(item.objectID)", withQCQueuePersister: self, withJavaUtilList: queueItems)
                list.addWithId(fakeQueue)
            }
            callbacks.loadedWithJavaUtilList(list)
        } catch let error as NSError {
            NSLog("%@", error)
        }
    }
    
    private func translateItems(set: NSSet) -> JavaUtilList {
        let list = JavaUtilArrayList()
        for item in set {
            let convertedItem = item as! QueueItem
            let translatedItem = QCQueueItem(NSString: "\(convertedItem.objectID)", withNSString: convertedItem.label)
            list.addWithId(translatedItem)
        }
        return list
    }
    
    func saveQueueWithQCQueue(queue: QCQueue!, withQCQueuePersister_Callbacks callbacks: QCQueuePersister_Callbacks!) -> QCQueue {
        let queue = Queue.insert(queue.getTitle(), into: managedObjectContext)
        do {
            try managedObjectContext.save()
        } catch let error as NSError {
            NSLog("%@", error)
        }
        return QCQueue(NSString: queue.title, withNSString: "\(queue.objectID)", withQCQueuePersister: self, withJavaUtilList: JavaUtilArrayList())
    }
    
    func deleteQueueWithQCQueue(queue: QCQueue!, withQCQueuePersister_Callbacks callbacks: QCQueuePersister_Callbacks!) {
        guard let managedQueue = fetchQueue(queue.getId()) else {
            callbacks.failedToSaveWithQCQueue(queue)
            return
        }
        managedObjectContext.deleteObject(managedQueue)
    }
    
    private func fetchQueue(queueId: String) -> Queue? {
        let fetchRequest = NSFetchRequest(entityName: "Queue")
        do {
            let results = try managedObjectContext.executeFetchRequest(fetchRequest) as! [NSManagedObject]
            let filteredById = results.filter({ (object: NSManagedObject) -> Bool in
                return "\(object.objectID)" == queueId
            })
            return filteredById.last as! Queue?
        } catch let error as NSError {
            NSLog("\(error.localizedDescription)")
        }
        return nil
    }
    
    func addItemToQueueWithNSString(queueId: String!, withQCQueueItem queueItem: QCQueueItem!) {
        guard let host = fetchQueue(queueId) else {
            return
        }
        QueueItem.insert(queueItem.getLabel(), host: host, into: managedObjectContext)
        do {
            try managedObjectContext.save()
        } catch let error as NSError {
            NSLog("\(error.localizedDescription)")
        }
    }
    
    func removeItemFromQueueWithNSString(queueId: String!, withQCQueueItem queueItem: QCQueueItem!) {
        // Do nothing
    }

}
