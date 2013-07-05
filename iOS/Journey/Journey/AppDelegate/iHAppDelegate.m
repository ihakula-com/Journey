//
//  iHAppDelegate.m
//  Journey
//
//  Created by Wayde Sun on 6/27/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import "iHAppDelegate.h"
#import "iHEngine.h"
#import "iHSingletonCloud.h"
#import "iHCacheCenter.h"
#import "iHLog.h"
#import "iHLogCenter.h"

#import "iHStarViewController.h"
#import "iHMoreViewController.h"

@interface iHAppDelegate ()
- (void)locateMe;
- (void)stopUpdatingLocation:(NSString *)state;
@end

@implementation iHAppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleBlackOpaque];
    
    // Engine Start
    if (![iHEngine start]) {
        return NO;
    }
    self.user = [[JUser alloc] init];
    self.customerMessageStatusBar = [[iHStatusBarWindow alloc] initWithColor:iHStatusBarColorBlack];
    
    UIViewController *viewController1 = [[iHStarViewController alloc] initWithNibName:@"iHStarViewController" bundle:nil];
    UIViewController *viewController2 = [[iHMoreViewController alloc] initWithNibName:@"iHMoreViewController" bundle:nil];
  
    UINavigationController *nav1 = [[UINavigationController alloc] initWithRootViewController:viewController1];
    UINavigationController *nav2 = [[UINavigationController alloc] initWithRootViewController:viewController2];
      
    self.tabBarController = [[UITabBarController alloc] init];
    self.tabBarController.viewControllers = @[nav1, nav2];
    self.window.rootViewController = self.tabBarController;
    [self.window makeKeyAndVisible];
    
    [self locateMe];
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
  // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
  // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
  // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
  // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
  // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
  // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
  // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

/*
// Optional UITabBarControllerDelegate method.
- (void)tabBarController:(UITabBarController *)tabBarController didSelectViewController:(UIViewController *)viewController
{
}
*/

/*
// Optional UITabBarControllerDelegate method.
- (void)tabBarController:(UITabBarController *)tabBarController didEndCustomizingViewControllers:(NSArray *)viewControllers changed:(BOOL)changed
{
}
*/

#pragma mark - Private Methods
- (void)locateMe {
    _locateManager = [[CLLocationManager alloc] init];
    _locateManager.delegate  = self;
    _locateManager.desiredAccuracy = kCLLocationAccuracyKilometer;
    [_locateManager startUpdatingLocation];
    [self performSelector:@selector(stopUpdatingLocation:) withObject:@"Timed Out" afterDelay:5.0];
}

- (void)stopUpdatingLocation:(NSString *)state {
    [_locateManager stopUpdatingLocation];
    _locateManager.delegate = nil;
}

#pragma mark - CLLocationManagerDelegate
- (void)locationManager:(CLLocationManager *)manager didUpdateToLocation:(CLLocation *)newLocation fromLocation:(CLLocation *)oldLocation {
    self.user.myLocation = newLocation;
    [self.user getAddress];
}

- (void)locationManager:(CLLocationManager *)manager didFailWithError:(NSError *)error {
    if ([error code] != kCLErrorLocationUnknown) {
        [self stopUpdatingLocation:NSLocalizedString(@"Error", @"Error")];
    }
}


#pragma mark - Class Methods
+ (iHAppDelegate *)getSharedAppDelegate {
    return (iHAppDelegate *)[[UIApplication sharedApplication] delegate];
}

@end
