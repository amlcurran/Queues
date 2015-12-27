//
//  Queue.swift
//  Queues
//
//  Created by Alex on 27/12/2015.
//  Copyright © 2015 Alex Curran. All rights reserved.
//

import Foundation
import CoreData


class Queue: NSManagedObject {

    static func insert(title: String, into context:NSManagedObjectContext) -> Queue {
        let queue = NSEntityDescription.insertNewObjectForEntityForName("Queue", inManagedObjectContext: context) as! Queue
        queue.title = title;
        return queue
    }

}
