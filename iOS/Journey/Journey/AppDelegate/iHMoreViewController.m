//
//  iHSecondViewController.m
//  Journey
//
//  Created by Wayde Sun on 6/27/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import "iHMoreViewController.h"
#import "JMoreCell.h"
#import "JPTViewController.h"
#import "JAboutUsViewController.h"

@interface iHMoreViewController ()
- (void)gotoPrivacyAndTerms;
- (void)gotoAboutUsAndTerms;
@end

@implementation iHMoreViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.title = NSLocalizedString(@"gengduo", @"gengduo");
        self.tabBarItem.image = [UIImage imageNamed:@"second"];
        
        NSString *dataPath = [[NSBundle mainBundle] pathForResource:@"More" ofType:@"plist"];
        NSDictionary *dataDic = [NSDictionary dictionaryWithContentsOfFile:dataPath];
        _moreList = [dataDic objectForKey:@"data"];
    }
    return self;
}
							
- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidUnload {
    [self setTheTableView:nil];
    [super viewDidUnload];
}

#pragma mark - UITableView delegate & datasource
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    [self gotoAboutUsAndTerms];
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 44;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [_moreList count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *CellIdentifier = @"SightSpotHomeCell";
    UITableViewCell *cell = nil;
    
    cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[JMoreCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
        [cell setAccessoryType:UITableViewCellAccessoryDisclosureIndicator];
    }
    
    NSDictionary *service = [_moreList objectAtIndex:indexPath.row];
    [(JMoreCell *)cell setValues:service];
    
    return cell;
}

#pragma mark - Private Methods
- (void)gotoPrivacyAndTerms {
    JPTViewController *vc = [[JPTViewController alloc] initWithNibName:@"JPTViewController" bundle:nil];
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)gotoAboutUsAndTerms {
    JAboutUsViewController *vc = [[JAboutUsViewController alloc] initWithNibName:@"JAboutUsViewController" bundle:nil];
    [self.navigationController pushViewController:vc animated:YES];
}
@end
