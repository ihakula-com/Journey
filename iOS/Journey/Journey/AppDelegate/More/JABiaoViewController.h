//
//  JABiaoViewController.h
//  Journey
//
//  Created by Wayde Sun on 7/2/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import "JBaseViewController.h"
#import "iHZoomableImageViewProtocol.h"

@class iHZoomableImageView;
@interface JABiaoViewController : JBaseViewController <iHZoomableImageViewProtocol> {
    iHZoomableImageView *_zoomableView;
}

@property (weak, nonatomic) IBOutlet UIScrollView *theScrollView;
- (IBAction)onLicenceBtnClicked:(id)sender;
@end
