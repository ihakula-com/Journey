//
//  JStarChoiceViewController.h
//  Journey
//
//  Created by Wayde Sun on 7/1/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import "JBaseViewController.h"

@class JStarChoiceModel;
@interface JStarChoiceViewController : JBaseViewController <UITableViewDataSource, UITableViewDelegate> {
    JStarChoiceModel *_dm;
}


@property (weak, nonatomic) IBOutlet UITableView *theTableView;
@end
