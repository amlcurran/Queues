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
                let fakeQueue = QCQueue(NSString: item.title, withNSString: "\(item.objectID)", withQCQueuePersister: self, withJavaUtilList: JavaUtilArrayList())
                list.addWithId(fakeQueue)
            }
            callbacks.loadedWithJavaUtilList(list)
        } catch let error as NSError {
            NSLog("%@", error)
        }
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
        let fetchRequest = NSFetchRequest(entityName: "Queue")
        do {
            let results = try managedObjectContext.executeFetchRequest(fetchRequest) as! [NSManagedObject]
            let filteredById = results.filter({ (object: NSManagedObject) -> Bool in
                return "\(object.objectID)" == queue.getId()
            })
            for managedObject in filteredById {
                managedObjectContext.deleteObject(managedObject)
            }
            try managedObjectContext.save()
        } catch let error as NSError {
            NSLog("\(error.localizedDescription)")
        }
    }
    
    func addItemToQueueWithNSString(queueId: String!, withQCQueueItem queueItem: QCQueueItem!) {
        
    }
    
    func removeItemFromQueueWithNSString(queueId: String!, withQCQueueItem queueItem: QCQueueItem!) {
        // Do nothing
    }

}
