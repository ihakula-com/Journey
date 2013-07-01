//
//  iHFirstViewController.h
//  Journey
//
//  Created by Wayde Sun on 6/27/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "JBaseViewController.h"
#import "iHImageSlideView.h"

@class iHPageView, iHStarModel;
@interface iHStarViewController : JBaseViewController <ImageSliderViewDelegate, UITableViewDataSource, UITableViewDelegate> {
    iHImageSlideView *_slideImageView;
    iHPageView *_pageView;
    
    iHStarModel *_dm;
}

@property (weak, nonatomic) IBOutlet UILabel *sightSpotLabel;
@property (weak, nonatomic) IBOutlet UITableView *theTableView;


@end