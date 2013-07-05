//
//  JBaseViewController.h
//  Journey
//
//  Created by Wayde Sun on 6/30/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import "iHBaseViewController.h"
#import "iHAppDelegate.h"

@interface JBaseViewController : iHBaseViewController <UIActionSheetDelegate> {
    iHAppDelegate *appDelegate;
}

- (void)setupRightCallItem;
- (void)showAlertMessage:(NSString *)msg;

@end
