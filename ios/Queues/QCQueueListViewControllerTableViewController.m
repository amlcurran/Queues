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
    
    
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
    
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
}

- (void)viewDidAppear:(BOOL)animated
{
    [self.presenter start];
}

- (void)viewDidDisappear:(BOOL)animated
{
    [self.presenter stop];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    // Return the number of rows in the section.
    return [self.queueList count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"queue" forIndexPath:indexPath];
    QCQueue *queue = [self.queueList objectAtIndex:indexPath.row];
    [cell textLabel].text = [queue getTitle];
    return cell;
}

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath {
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath {
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath {
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath {
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

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
    
}

- (void)queueRemovedWithQCQueue:(QCQueue *)queue
                        withInt:(jint)position
{
    
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
