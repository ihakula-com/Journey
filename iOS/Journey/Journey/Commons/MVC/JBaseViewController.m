//
//  JBaseViewController.m
//  Journey
//
//  Created by Wayde Sun on 6/30/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import "JBaseViewController.h"

@interface JBaseViewController ()
- (void)callBtnClicked;
@end

@implementation JBaseViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        appDelegate = [iHAppDelegate getSharedAppDelegate];
    }
    return self;
}

- (id)init {
    self = [super init];
    if (self) {
        appDelegate = [iHAppDelegate getSharedAppDelegate];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Public Methods
- (void)showAlertMessage:(NSString *)msg {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:NSLocalizedString(@"WarmServiceTitle", @"WarmServiceTitle") message:msg delegate:nil cancelButtonTitle:NSLocalizedString(@"ok", @"ok") otherButtonTitles:nil, nil];
    [alert show];
}

#pragma mark - Private Methods
- (void)setupRightCallItem {
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:NSLocalizedString(@"callme", @"CallMe") style:UIBarButtonItemStylePlain target:self action:@selector(callBtnClicked)];
}

- (void)callBtnClicked {
    // MKReverseGeocoder
    // CLGeocoder
    
    UIActionSheet *actionSheet = [[UIActionSheet alloc] initWithTitle:NSLocalizedString(@"callmetitle", @"CallMeTitle")
                                                             delegate:self
                                                    cancelButtonTitle:NSLocalizedString(@"cancel", @"Cancel")
                                               destructiveButtonTitle:nil
                                                    otherButtonTitles:NSLocalizedString(@"abiao", @"ABiao"), NSLocalizedString(@"ahui", @"AHui"), nil];
    
    self.view.clipsToBounds = YES;
    [actionSheet showInView:appDelegate.window];
}

#pragma mark - UIActionSheetDelegate
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (2 != buttonIndex) {
        
        NSString *content;
        NSString *phoneNum;
        
        if (0 == buttonIndex) {
            content = NSLocalizedString(@"ABiaoService", @"ABiaoService");
            phoneNum = NSLocalizedString(@"ABiaoPhoneNum", @"ABiaoPhoneNum");
        } else if (1 == buttonIndex) {
            content = NSLocalizedString(@"AHuiService", @"AHuiService");
            phoneNum = NSLocalizedString(@"AHuiPhoneNum", @"AHuiPhoneNum");
        }
        
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:NSLocalizedString(@"WarmServiceTitle", @"WarmServiceTitle") message:content selectedBlock:^(NSInteger index){
            if (1 == index) {
                
                if (0 == buttonIndex) {
                    int times = [[USER_DEFAULT valueForKey:IH_ABIAO_PHONE_NUMBER] intValue];
                    times = times == -1 ? 0 : times;
                    times++;
                    [USER_DEFAULT setValue:[NSString stringWithFormat:@"%d", times] forKey:IH_ABIAO_PHONE_NUMBER];
                } else {
                    int times = [[USER_DEFAULT valueForKey:IH_AHUI_PHONE_NUMBER] intValue];
                    times = times == -1 ? 0 : times;
                    times++;
                    [USER_DEFAULT setValue:[NSString stringWithFormat:@"%d", times] forKey:IH_AHUI_PHONE_NUMBER];
                }
                [USER_DEFAULT synchronize];
                
                [appDelegate.user doCallCallMeService];
                
                NSString *num = [[NSString alloc] initWithFormat:@"tel://%@", phoneNum];
                [[UIApplication sharedApplication] openURL:[NSURL URLWithString:num]];
            }
        } cancelButtonTitle:NSLocalizedString(@"cancel", @"Cancel") otherButtonTitles:NSLocalizedString(@"ok", @"OK")];
        [alert show];
    }
}

@end
