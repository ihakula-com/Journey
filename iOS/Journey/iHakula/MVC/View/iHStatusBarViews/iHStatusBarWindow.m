//
//  iHStatusBarWindow.m
//  iHakula
//
//  Created by Wayde Sun on 7/3/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#define IH_STATUS_BAR_GRAY      @"grayStatusBar.png"

#import "iHStatusBarWindow.h"
#import "iHStatusBarActivitiesView.h"
#import "iHAppDelegate.h"

@interface iHStatusBarWindow ()
- (NSString *)getStatusBarImage:(iHStatusBarColor)barColor;
@end

@implementation iHStatusBarWindow

- (id)initWithColor:(iHStatusBarColor)barColor {
    self = [super initWithFrame:CGRectMake(0, 0, 320, 20)];
    if (self) {
        self.windowLevel = UIWindowLevelStatusBar + 0.1f;
        self.frame = [[UIApplication sharedApplication] statusBarFrame];
        self.backgroundColor = [UIColor clearColor];
        self.alpha = 0.0;
        
        _activitiesView = [iHStatusBarActivitiesView viewFromNib];
        if (barColor == iHStatusBarColorBlack) {
            _activitiesView.messageLabel.backgroundColor = [UIColor blackColor];
        } else {
            NSString *barImage = [self getStatusBarImage:barColor];
            _activitiesView.colorImageView.image = ImageNamed(barImage);
        }
        
        [self addSubview:_activitiesView];
    }
    return self;
}

- (void)dealloc {
    [_activitiesView release];
    [super dealloc];
}

#pragma mark - Public Methods
- (void)showMessage:(NSString *)msg {
    [UIView animateWithDuration:.3 animations:^{
        self.alpha = 1.0;
        _activitiesView.messageLabel.text = msg;
        [self makeKeyAndVisible];
    }];
    
    // restore default window
    [[iHAppDelegate getSharedAppDelegate].window makeKeyWindow];
}

- (void)hide {
    [UIView animateWithDuration:.3 animations:^{
        self.alpha = 0.0;
    } completion:^(BOOL finished){
        _activitiesView.messageLabel.text = @"";
    }];
}

#pragma mark - Private Methods
- (NSString *)getStatusBarImage:(iHStatusBarColor)barColor {
    NSString *name = @"";
    switch (barColor) {
        case iHStatusBarColorGray:
            name = IH_STATUS_BAR_GRAY;
            break;
            
        default:
            break;
    }
    
    return name;
}

@end
