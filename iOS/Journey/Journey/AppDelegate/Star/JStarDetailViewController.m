//
//  JStarDetailViewController.m
//  Journey
//
//  Created by Wayde Sun on 7/2/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import "JStarDetailViewController.h"

@interface JStarDetailViewController ()

@end

@implementation JStarDetailViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self setupRightCallItem];
    
    NSError *err = nil;
    NSString *dataPath = [[NSBundle mainBundle] pathForResource:@"11" ofType:@"txt"];
    NSString *content = [NSString stringWithContentsOfFile:dataPath encoding:NSUTF8StringEncoding error:&err];
    if (!err) {
        CGSize realsize = [content sizeWithFont:_contentLabel.font constrainedToSize:CGSizeMake(_contentLabel.width, 1000)];
        _contentLabel.height = realsize.height + 600;
        _contentLabel.text = content;
        
        self.theScrollView.contentSize = CGSizeMake(320, _contentLabel.height + 50);
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidUnload {
    [self setTheScrollView:nil];
    [self setContentLabel:nil];
    [super viewDidUnload];
}
@end
