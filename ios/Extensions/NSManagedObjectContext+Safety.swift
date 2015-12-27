//
//  NSManagedObjectContext+Safety.swift
//  Queues
//
//  Created by Alex on 27/12/2015.
//  Copyright © 2015 Alex Curran. All rights reserved.
//

import UIKit
import CoreData

extension NSManagedObjectContext {
    
    func safeSave() {
        do {
            try save()
        } catch let error as NSError {
            NSLog("\(error.localizedDescription)")
        }
    }

}
