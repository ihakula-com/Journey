//
//  iHFirstViewController.m
//  Journey
//
//  Created by Wayde Sun on 6/27/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import "iHStarViewController.h"
#import "iHPageView.h"

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
    }
    return self;
}
							
- (void)viewDidLoad
{
    [super viewDidLoad];
	[self drawSlideImages];
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

@end
