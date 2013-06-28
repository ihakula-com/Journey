//
//  iHFirstViewController.h
//  Journey
//
//  Created by Wayde Sun on 6/27/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "iHImageSlideView.h"

@class iHPageView;
@interface iHStarViewController : UIViewController <ImageSliderViewDelegate> {
    iHImageSlideView *_slideImageView;
    iHPageView *_pageView;
}

@end

// [[NSUserDefaults standardUserDefaults] objectForKey:@"SBFormattedPhoneNumber"];