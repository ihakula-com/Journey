//
//  JABiaoViewController.m
//  Journey
//
//  Created by Wayde Sun on 7/2/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import "JABiaoViewController.h"
#import "iHZoomableImageView.h"

@interface JABiaoViewController ()

@end

@implementation JABiaoViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.title = NSLocalizedString(@"ABiaoSelfDescription", @"ABiaoSelfDescription");
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.theScrollView.contentSize = CGSizeMake(320, 600);
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidUnload {
    [self setTheScrollView:nil];
    [super viewDidUnload];
}
- (IBAction)onLicenceBtnClicked:(id)sender {
    if (!_zoomableView) {
        _zoomableView = [[iHZoomableImageView alloc] initWithFrame:self.view.bounds];
        _zoomableView.tapDelegate = self;
    }
    [_zoomableView setupImage:ImageNamed(@"licence.jpeg")];
    [self.view addSubview:_zoomableView];
}

#pragma mark - IHZoomableImgView
- (void)ihimageZoomableViewSingleTapped:(iHZoomableImageView*)imageZoomableView {
    [_zoomableView removeFromSuperview];
}

@end
