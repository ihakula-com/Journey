//
//  JFeedbackViewController.m
//  Journey
//
//  Created by Wayde Sun on 7/2/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import "JFeedbackViewController.h"
#import "iHArithmeticKit.h"

#define TEXT_VIEW_DEFAULT_COLOR     RGBACOLOR(187, 187, 187, 1)

@interface JFeedbackViewController ()
- (void)setupRightFeedbackItem;
- (void)sureBtnClicked;
- (void)restoreTextView;
@end

@implementation JFeedbackViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.title = NSLocalizedString(@"feedback", @"feedback");
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self setupRightFeedbackItem];
    [self restoreTextView];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidUnload {
    [self setTheTextView:nil];
    [self setLengthLabel:nil];
    [super viewDidUnload];
}

#pragma mark UItextViewDelegate
- (void)textViewDidChange:(UITextView *)textView{
    NSUInteger strLength = [iHArithmeticKit lengthOfComplexStr:textView.text];
    NSUInteger numOfHanzi = (strLength - textView.text.length)/2;
    self.lengthLabel.text = [NSString stringWithFormat:@"%d/500",(textView.text.length + numOfHanzi)];
    if ((textView.text.length + numOfHanzi) > 500) {
//        textView.text = [textView.text substringToIndex:[textView.text length] - 1];
        self.lengthLabel.textColor = [UIColor redColor];
    }else{
        self.lengthLabel.textColor = RGBACOLOR(68, 30, 12, 1);
    }
}

- (void)textViewDidBeginEditing:(UITextView *)textView{
    textView.text = @"";
    textView.textColor = [UIColor blackColor];
}

- (void)textViewDidEndEditing:(UITextView *)textView {
    if (0 == textView.text.length) {
        [self restoreTextView];
    }
}

#pragma mark - Private Methods
- (void)setupRightFeedbackItem {
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:NSLocalizedString(@"ok", @"ok") style:UIBarButtonItemStylePlain target:self action:@selector(sureBtnClicked)];
}

- (void)sureBtnClicked {
    if ([_lengthLabel.text isEqualToString:@"0/500"]) {
        [self showAlertMessage:LOCALIZED_STRING(@"iHServiceErrorFeedbackEmpty")];
        return;
    }
    [appDelegate.user doCallFeedbackService:_theTextView.text];
}

- (IBAction)onBgClicked:(id)sender {
    [_theTextView resignFirstResponder];
}

- (void)restoreTextView {
    self.theTextView.text = NSLocalizedString(@"feedbackDefault", @"feedbackDefault");
    self.theTextView.textColor = TEXT_VIEW_DEFAULT_COLOR;
}
@end
