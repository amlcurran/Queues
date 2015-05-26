//
//  QCQueueListViewControllerTableViewController.m
//  Queues
//
//  Created by Alex on 15/05/2015.
//  Copyright (c) 2015 Alex Curran. All rights reserved.
//

#import "QCQueueListViewControllerTableViewController.h"
#import "J2ObjC_header.h"
#import "QCDummyQueuePersister.h"
#import "QueueList.h"
#import "IOSObjectArray.h"
#import "java/util/List.h"
#import "Queue.h"

@interface QCQueueListViewControllerTableViewController ()

@property (nonatomic, strong) QCQueueListPresenter *presenter;
@property (nonatomic, strong) QCQueueList *list;
@property (nonatomic, strong) NSMutableArray *queueList;

@end

@implementation QCQueueListViewControllerTableViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    QCDummyQueuePersister *persister = [[QCDummyQueuePersister alloc] init];
    _list = [[QCQueueList alloc] initWithQCQueuePersister:persister];
    _queueList = [[NSMutableArray alloc] init];
    _presenter = [[QCQueueListPresenter alloc] initWithQCQueueListView:self withQCNavigationController:nil withQCQueueList:self.list];
    
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"Add" style:UIBarButtonItemStylePlain target:self action:@selector(addItem)];
}

- (void)addItem
{
    [self.list addNewQueueWithNSString:@"other"];
}

- (void)viewDidAppear:(BOOL)animated
{
    [self.presenter start];
}

- (void)viewDidDisappear:(BOOL)animated
{
    [self.presenter stop];
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [self.queueList count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"queue" forIndexPath:indexPath];
    QCQueue *queue = [self.queueList objectAtIndex:indexPath.row];
    [cell textLabel].text = [queue getTitle];
    return cell;
}

- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath {
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [self.presenter deleteQueueWithInt:indexPath.row];
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

#pragma mark QCListView


- (void)queueAddedWithQCQueue:(QCQueue *)queue
                      withInt:(jint)position
{
    [self.queueList insertObject:queue atIndex:position];
    NSIndexPath *path = [NSIndexPath indexPathForRow:position inSection:0];
    [self.tableView insertRowsAtIndexPaths:[NSArray arrayWithObject:path] withRowAnimation:UITableViewRowAnimationAutomatic];
}

- (void)queueRemovedWithQCQueue:(QCQueue *)queue
                        withInt:(jint)position
{
    [self.queueList removeObject:queue];
}

- (void)showWithJavaUtilList:(id<JavaUtilList>)queues
{
    IOSObjectArray *array = [queues toArray];
    for (id queue in array) {
        [self.queueList addObject:queue];
    }
    [self.tableView reloadData];
}

@end
