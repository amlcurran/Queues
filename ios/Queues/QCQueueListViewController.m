//
//  QCQueueListViewControllerTableViewController.m
//  Queues
//
//  Created by Alex on 15/05/2015.
//  Copyright (c) 2015 Alex Curran. All rights reserved.
//

#import "QCQueueListViewController.h"
#import "J2ObjC_header.h"
#import "QCDummyQueuePersister.h"
#import "QueueList.h"
#import "IOSObjectArray.h"
#import "java/util/List.h"
#import "Queue.h"
#import "QCQueueViewController.h"
#import "AppDelegate.h"

@interface QCQueueListViewController ()

@property (nonatomic, strong) QCQueueListPresenter *presenter;
@property (nonatomic, strong) NSMutableArray *queueList;

@end

@implementation QCQueueListViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    _queueList = [[NSMutableArray alloc] init];
    _presenter = [[QCQueueListPresenter alloc] initWithQCQueueListView:self withQCNavigationController:nil withQCQueueList:[AppDelegate sharedList]];
    
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"Add" style:UIBarButtonItemStylePlain target:self action:@selector(addItem)];
//    self.navigationController.navigationBar.translucent = NO;
    self.navigationController.navigationBar.barTintColor = [UIColor colorWithRed:0.255 green:0.416 blue:0.616 alpha:1];
    self.navigationController.navigationBar.tintColor = [UIColor colorWithWhite:1 alpha:0.8];
}

- (void)addItem
{
    UIAlertController *alertController = [UIAlertController alertControllerWithTitle:@"Add new queue" message:nil preferredStyle:UIAlertControllerStyleAlert];
    [alertController addAction:[UIAlertAction actionWithTitle:@"Add" style:UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
        UITextField *textField = [alertController.textFields objectAtIndex:0];
        [self addQueue:textField.text];
    }]];
    [alertController addAction:[UIAlertAction actionWithTitle:@"Cancel" style:UIAlertActionStyleCancel handler:nil]];
    [alertController addTextFieldWithConfigurationHandler:nil];
    [self presentViewController:alertController animated:true completion:nil];
}

- (void)addQueue:(NSString *)queueTitle
{
    [[AppDelegate sharedList] addNewQueueWithNSString:queueTitle];
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
        [self.presenter deleteQueueWithInt:indexPath.row];
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}

#pragma mark - Navigation

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    QCQueueViewController *queueView = [segue destinationViewController];
    NSIndexPath *selected = [self.tableView indexPathForSelectedRow];
    QCQueue *queue = [self.queueList objectAtIndex:selected.row];
    queueView.queue = queue;
}

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
    [self.queueList removeAllObjects];
    IOSObjectArray *array = [queues toArray];
    for (id queue in array) {
        [self.queueList addObject:queue];
    }
    [self.tableView reloadData];
}

@end
