//
//  JServiceViewController.m
//  Journey
//
//  Created by Wayde Sun on 7/1/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import "JServiceViewController.h"

@interface JServiceViewController ()

@end

@implementation JServiceViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.title = NSLocalizedString(@"WarmServiceTitle", @"WarmServiceTitle");
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.theScrollView.contentSize = CGSizeMake(320, 880);
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
@end
