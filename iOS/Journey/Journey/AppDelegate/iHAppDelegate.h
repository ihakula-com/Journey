//
//  iHAppDelegate.h
//  Journey
//
//  Created by Wayde Sun on 6/27/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "JUser.h"
#import "iHStatusBarWindow.h"

@interface iHAppDelegate : UIResponder <UIApplicationDelegate, UITabBarControllerDelegate, CLLocationManagerDelegate> {
    CLLocationManager *_locateManager;
}

@property (strong, nonatomic) UIWindow *window;
@property (strong, nonatomic) UITabBarController *tabBarController;

@property (strong, nonatomic) JUser *user;
@property (strong, nonatomic) iHStatusBarWindow *customerMessageStatusBar;

+ (iHAppDelegate *)getSharedAppDelegate;

@end
