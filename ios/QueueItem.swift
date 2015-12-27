//
//  QueueItem.swift
//  Queues
//
//  Created by Alex on 27/12/2015.
//  Copyright © 2015 Alex Curran. All rights reserved.
//

import Foundation
import CoreData


class QueueItem: NSManagedObject {

    static func insert(label: String, host: Queue, into context: NSManagedObjectContext) -> QueueItem {
        let queueItem = NSEntityDescription.insertNewObjectForEntityForName("QueueItem", inManagedObjectContext: context) as! QueueItem
        queueItem.label = label
        queueItem.host = host
        return queueItem
    }

}
