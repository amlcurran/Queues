//
//  QueueItem+CoreDataProperties.swift
//  Queues
//
//  Created by Alex on 27/12/2015.
//  Copyright © 2015 Alex Curran. All rights reserved.
//
//  Choose "Create NSManagedObject Subclass…" from the Core Data editor menu
//  to delete and recreate this implementation file for your updated model.
//

import Foundation
import CoreData

extension QueueItem {

    @NSManaged var label: String?
    @NSManaged var host: Queue?

}
