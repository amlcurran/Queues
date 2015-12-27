//
//  CoreDataPersister.swift
//  Queues
//
//  Created by Alex on 27/12/2015.
//  Copyright © 2015 Alex Curran. All rights reserved.
//

import UIKit

class CoreDataPersister: NSObject, QCQueuePersister {
    
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
        let list = JavaUtilArrayList()
        let fakeQueue = QCQueue(NSString: "title", withNSString: "id", withQCQueuePersister: self, withJavaUtilList: JavaUtilArrayList())
        list.addWithId(fakeQueue)
        callbacks.loadedWithJavaUtilList(list)
    }
    
    func saveQueueWithQCQueue(queue: QCQueue!, withQCQueuePersister_Callbacks callbacks: QCQueuePersister_Callbacks!) {
        // Do nothing
    }
    
    func deleteQueueWithQCQueue(queue: QCQueue!, withQCQueuePersister_Callbacks callbacks: QCQueuePersister_Callbacks!) {
        // Do nothing
    }
    
    func addItemToQueueWithNSString(queueId: String!, withQCQueueItem queueItem: QCQueueItem!) {
        // Do nothing
    }
    
    func removeItemFromQueueWithNSString(queueId: String!, withQCQueueItem queueItem: QCQueueItem!) {
        // Do nothing
    }

}
