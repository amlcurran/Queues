//
//  DetailViewController.h
//  Queues
//
//  Created by Alex on 28/04/2015.
//  Copyright (c) 2015 Alex Curran. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface DetailViewController : UIViewController

@property (strong, nonatomic) id detailItem;
@property (weak, nonatomic) IBOutlet UILabel *detailDescriptionLabel;

@end

