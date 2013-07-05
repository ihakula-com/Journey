//
//  JUser.h
//  Journey
//
//  Created by Wayde Sun on 7/1/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import "JBaseModel.h"
#import <CoreLocation/CoreLocation.h>

@interface JUser : JBaseModel {
    NSString *_address;
    NSString *_platform;
    NSString *_os;
    NSString *_device;
}

@property(strong, nonatomic) CLLocation *myLocation;

- (NSString *)getAddress;
- (void)doCallFeedbackService:(NSString *)feedback;
- (void)doCallCallMeService;

@end
