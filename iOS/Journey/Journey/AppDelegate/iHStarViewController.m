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
                                 @"http://i0.sinaimg.cn/qc/572/2011/0406/U7055P33T572D4F24198DT20120326100856.jpg",
                                 @"http://i2.sinaimg.cn/qc/572/2011/0406/U7055P33T572D4F24202DT20120326100856.jpg",
                                 @"http://i1.sinaimg.cn/qc/572/2011/0406/U7055P33T572D4F24206DT20120326103335.jpg",
                                 @"http://i2.sinaimg.cn/qc/572/2011/0406/U7055P33T572D4F24210DT20120326181928.jpg",
                                 @"http://i3.sinaimg.cn/qc/572/2011/0406/U7055P33T572D4F24214DT20120326180737.jpg",
                                 nil];
    _slideImageView = [[iHImageSlideView alloc] initWithFrame:CGRectMake(0, 0, 320, 100)];
    _slideImageView.delegate = self;
    [self.view insertSubview:_slideImageView atIndex:0];
    [_slideImageView setImageUrls:imageUrls];
    
    
    _pageView = [[iHPageView alloc] initWithPageNum:[imageUrls count]];
    _pageView.top = _slideImageView.bottom - 8;
    _pageView.left = 130;
    [self.view addSubview:_pageView];
}

#pragma mark - ImageSliderViewDelegate

- (void)imageClickedWithIndex:(int)imageIndex{
    iHDINFO(@"image clicked at index:%d",imageIndex);
}

- (void)imageDidEndDeceleratingWithIndex:(int)imageIndex{
    [_pageView setCurrentPage:imageIndex];
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
