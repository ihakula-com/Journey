//
//  iHSecondViewController.h
//  Journey
//
//  Created by Wayde Sun on 6/27/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface iHMoreViewController : UIViewController <UITableViewDelegate, UITableViewDataSource> {
    NSArray *_moreList;
}


@property (weak, nonatomic) IBOutlet UITableView *theTableView;

@end
