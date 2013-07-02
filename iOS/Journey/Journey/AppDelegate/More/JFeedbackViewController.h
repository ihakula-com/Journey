//
//  JFeedbackViewController.h
//  Journey
//
//  Created by Wayde Sun on 7/2/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import "JBaseViewController.h"

@interface JFeedbackViewController : JBaseViewController

@property (weak, nonatomic) IBOutlet UITextView *theTextView;
@property (weak, nonatomic) IBOutlet UILabel *lengthLabel;

- (IBAction)onBgClicked:(id)sender;
@end
