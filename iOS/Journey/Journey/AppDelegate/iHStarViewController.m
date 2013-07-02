//
//  iHFirstViewController.m
//  Journey
//
//  Created by Wayde Sun on 6/27/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import "iHStarViewController.h"
#import "iHPageView.h"
#import "JHomeCell.h"
#import "iHStarModel.h"
#import "JStarChoiceViewController.h"

@interface iHStarViewController ()
- (void)drawSlideImages;
@end

@implementation iHStarViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.title = NSLocalizedString(@"jingpin", @"jingpin");
        self.tabBarItem.image = [UIImage imageNamed:@"first"];
        _dm = [[iHStarModel alloc] init];
    }
    return self;
}
							
- (void)viewDidLoad
{
    [super viewDidLoad];
    [self setupRightCallItem];
	[self drawSlideImages];
    [self performSelector:@selector(test) withObject:nil afterDelay:5.0];
}

- (void)test {
    [appDelegate.user getAddress];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Private Methods
- (void)drawSlideImages {
    NSMutableArray *imageUrls = [NSMutableArray arrayWithObjects:
                                 @"10.png",
                                 @"1.png",
                                 @"2.png",
                                 @"3.png",
                                 @"4.png",
                                 @"5.png",
                                 @"6.png",
                                 @"7.png",
                                 @"8.png",
                                 @"9.png",
                                 nil];
    _slideImageView = [[iHImageSlideView alloc] initWithFrame:CGRectMake(0, 0, 320, 100)];
    _slideImageView.delegate = self;
    [self.view insertSubview:_slideImageView atIndex:0];
    [_slideImageView setImageUrls:imageUrls];
    
    
    _pageView = [[iHPageView alloc] initWithPageNum:[imageUrls count]];
    _pageView.top = _slideImageView.bottom - 8;
    _pageView.left = 102;
    [self.view addSubview:_pageView];
}

#pragma mark - ImageSliderViewDelegate

- (void)imageClickedWithIndex:(int)imageIndex{
    iHDINFO(@"image clicked at index:%d",imageIndex);
}

- (void)imageDidEndDeceleratingWithIndex:(int)imageIndex{
    [_pageView setCurrentPage:imageIndex];
    NSString *motto = [_dm.sightsMottoArr objectAtIndex:imageIndex];
    _sightSpotLabel.text = motto;
}

- (void)viewDidUnload {
    [self setTheTableView:nil];
    [self setSightSpotLabel:nil];
    [super viewDidUnload];
}

#pragma mark - UITableView delegate & datasource
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    JStarChoiceViewController *vc = [[JStarChoiceViewController alloc] initWithNibName:@"JStarChoiceViewController" bundle:nil];
    [self.navigationController pushViewController:vc animated:YES];
    
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 52;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [_dm.servicesArr count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *CellIdentifier = @"SightSpotHomeCell";
    UITableViewCell *cell = nil;
    
    cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[JHomeCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
        [cell setAccessoryType:UITableViewCellAccessoryDisclosureIndicator];
    }
    
    NSDictionary *service = [_dm.servicesArr objectAtIndex:indexPath.row];
    [(JHomeCell *)cell setValues:service];
        
    return cell;
}

@end
