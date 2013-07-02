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
#import "JFeedbackViewController.h"
#import "JServiceViewController.h"
#import "JABiaoViewController.h"

@interface iHMoreViewController ()
- (void)gotoABiaoDescription;
- (void)gotoService;
- (void)gotoPrivacyAndTerms;
- (void)gotoAboutUs;
- (void)gotoFeedback;
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
    
    switch (indexPath.row) {
        case 0:
            [self gotoABiaoDescription];
            break;
        case 1:
            [self gotoService];
            break;
        case 2:
            [self gotoFeedback];
            break;
        case 3:
            [self gotoAboutUs];
            break;
            
        default:
            break;
    }    
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
- (void)gotoABiaoDescription {
    JABiaoViewController *vc = [[JABiaoViewController alloc] initWithNibName:@"JABiaoViewController" bundle:nil];
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)gotoPrivacyAndTerms {
    JPTViewController *vc = [[JPTViewController alloc] initWithNibName:@"JPTViewController" bundle:nil];
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)gotoAboutUs {
    JAboutUsViewController *vc = [[JAboutUsViewController alloc] initWithNibName:@"JAboutUsViewController" bundle:nil];
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)gotoFeedback {
    JFeedbackViewController *vc = [[JFeedbackViewController alloc] initWithNibName:@"JFeedbackViewController" bundle:nil];
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)gotoService {
    JServiceViewController *vc = [[JServiceViewController alloc] initWithNibName:@"JServiceViewController" bundle:nil];
    [self.navigationController pushViewController:vc animated:YES];
}
@end
