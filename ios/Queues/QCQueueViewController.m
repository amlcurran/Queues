//
//  QCQueueViewControllerTableViewController.m
//  Queues
//
//  Created by Alex on 28/05/2015.
//  Copyright (c) 2015 Alex Curran. All rights reserved.
//

#import "QCQueueViewController.h"
#import "QueueItem.h"
#import "java/util/List.h"
#import "QueuePresenter.h"
#import "AppDelegate.h"
#import "QueueView.h"

@interface QCQueueViewController () <QCQueueView>

@property (nonatomic, strong) QCQueuePresenter *presenter;
@property (nonatomic, strong) NSMutableArray *queueItems;

@end

@interface QCQueueViewController ()

@end

@implementation QCQueueViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    if (!self.queue) {
        [NSException raise:@"Attempting to view a queue without supplying one" format:nil];
    }
    
    _presenter = [[QCQueuePresenter alloc] initWithNSString:[self.queue getId] withQCQueueView:self withQCQueueList:[AppDelegate sharedList]];
    _queueItems = [[NSMutableArray alloc] init];
    
    self.title = [self.queue getTitle];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"Add" style:UIBarButtonItemStylePlain target:self action:@selector(addItem)];
}

- (void)addItem
{
    UIAlertController *alertController = [UIAlertController alertControllerWithTitle:@"Add new queue" message:nil preferredStyle:UIAlertControllerStyleAlert];
    [alertController addAction:[UIAlertAction actionWithTitle:@"Add" style:UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
        UITextField *textField = [alertController.textFields objectAtIndex:0];
        [self.presenter addItemWithNSString:textField.text];
    }]];
    [alertController addAction:[UIAlertAction actionWithTitle:@"Cancel" style:UIAlertActionStyleCancel handler:nil]];
    [alertController addTextFieldWithConfigurationHandler:nil];
    [self presentViewController:alertController animated:true completion:nil];
}

- (void)viewDidAppear:(BOOL)animated
{
    [self.presenter load__];
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [self.queueItems count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"queueItem" forIndexPath:indexPath];
    QCQueueItem *item = [self.queueItems objectAtIndex:indexPath.row];
    cell.textLabel.text = [item getLabel];
    return cell;
}

// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath {
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        [_presenter removeItemWithInt:indexPath.row];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}

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

- (void)showWithQCQueue:(QCQueue *)queue
{
    [self.queueItems removeAllObjects];
    IOSObjectArray *array = [[queue all] toArray];
    for (id queue in array) {
        [self.queueItems addObject:queue];
    }
    [self.tableView reloadData];
}

- (void)itemAddedWithQCQueueItem:(QCQueueItem *)queueItem
{
    [self.queueItems addObject:queueItem];
    NSInteger addedIndex = [self.queueItems indexOfObject:queueItem];
    NSIndexPath *indexPath = [NSIndexPath indexPathForRow:addedIndex inSection:0];
    [self.tableView insertRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationAutomatic];
}

- (void)itemRemovedWithQCQueueItem:(QCQueueItem *)item
{
    NSInteger removedIndex = [self.queueItems indexOfObject:item];
    [self.queueItems removeObject:item];
    NSIndexPath *indexPath = [NSIndexPath indexPathForRow:removedIndex inSection:0];
    [self.tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationAutomatic];
}

- (void)notEmpty
{
    
}

- (void)empty
{
    UIAlertController *controller = [UIAlertController alertControllerWithTitle:@"No items" message:nil preferredStyle:UIAlertControllerStyleAlert];
    UIAlertAction *okAction = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:nil];
    
    [controller addAction:okAction];
    [self presentViewController:controller animated:YES completion:nil];
}

@end
