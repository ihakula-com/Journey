//
//  JUser.m
//  Journey
//
//  Created by Wayde Sun on 7/1/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import "JUser.h"
#import "iHPubSub.h"

#define FEEDBACK_SERVICE        @"FeedbackService"
#define CALL_ME_SERVICE         @"CallMeService"


@interface JUser ()
- (void)restoreDefaultCallCounter;
@end

@implementation JUser

- (id)init {
    self = [super init];
    if (self) {
        self.myLocation = nil;
        _address = @"";
        _platform = [UIDevice currentDevice].systemName;
        _os = [UIDevice currentDevice].systemVersion;
        _device = [UIDevice currentDevice].localizedModel;
        
        if (![USER_DEFAULT valueForKey:IH_ABIAO_PHONE_NUMBER]) {
            [self restoreDefaultCallCounter];
        }
    }
    return self;
}


#pragma mark - Public Methods
- (NSString *)getAddress {
    
    if (![_address isEqualToString:@""]) {
        return _address;
    }
    
    CLGeocoder *geocoder = [[CLGeocoder alloc] init];
    [geocoder reverseGeocodeLocation:self.myLocation
     completionHandler:^(NSArray *placemarks, NSError *error) {
         if (error == nil && [placemarks count] > 0){
             CLPlacemark *placemark = [placemarks objectAtIndex:0];
             NSString *locality = placemark.locality;
             NSString *administrativeArea = placemark.administrativeArea;
             NSString *subLocality = placemark.subLocality;
             
             if (locality || administrativeArea || subLocality) {
                 if (!locality) {
                     locality = @"";
                 }
                 if (!administrativeArea) {
                     administrativeArea = @"";
                 }
                 if (!subLocality) {
                     subLocality = @"";
                 }
                 _address = [NSString stringWithFormat:@"%@%@%@", administrativeArea, locality, subLocality];
             }
         } else if (error == nil && [placemarks count] == 0){
             _address = @"";
         } else if (error != nil){
             _address = @"";
         }
         
     }];
    
    return @"";
}

- (void)doCallFeedbackService:(NSString *)feedback {
    theRequest.requestMethod = iHRequestMethodPost;
    NSDictionary *paras = [NSDictionary dictionaryWithObjectsAndKeys:
                           _platform, @"platform",
                           _os, @"os",
                           _device, @"device",
                           feedback, @"description",
                           nil];
    
    [self doCallService:FEEDBACK_SERVICE withParameters:paras andServiceUrl:SERVICE_FEEDBACK forDelegate:self];
}

- (void)doCallCallMeService {
    int times = 0;
    NSString *serviceNumber = @"";
    
    int abiaoTimes = [[USER_DEFAULT valueForKey:IH_ABIAO_PHONE_NUMBER] intValue];
    int ahuiTimes = [[USER_DEFAULT valueForKey:IH_AHUI_PHONE_NUMBER] intValue];
    
    if (abiaoTimes > 0) {
        times += abiaoTimes;
        serviceNumber = LOCALIZED_STRING(@"ABiaoPhoneNum");
    }
    if (ahuiTimes > 0) {
        times += ahuiTimes;
        if (abiaoTimes > 0) {
            serviceNumber = [NSString stringWithFormat:@"%@,%@", serviceNumber, LOCALIZED_STRING(@"AHuiPhoneNum")];
        } else {
            serviceNumber = LOCALIZED_STRING(@"AHuiPhoneNum");
        }
    }
    
    theRequest.requestMethod = iHRequestMethodPost;
    NSDictionary *paras = [NSDictionary dictionaryWithObjectsAndKeys:
                           _platform, @"platform",
                           _os, @"os",
                           _device, @"device",
                           [self getAddress], @"address",
                           @"", @"number",
                           serviceNumber, @"service_number",
                           [NSString stringWithFormat:@"%d", times], @"times",
                           nil];
    
    [self doCallService:CALL_ME_SERVICE withParameters:paras andServiceUrl:SERVICE_CALL_ME forDelegate:self];
}

#pragma mark - Private Methods
- (void)restoreDefaultCallCounter {
    [USER_DEFAULT setValue:@"-1" forKey:IH_ABIAO_PHONE_NUMBER];
    [USER_DEFAULT setValue:@"-1" forKey:IH_AHUI_PHONE_NUMBER];
    [USER_DEFAULT synchronize];
}

- (void)cancelRequest {
    [iHPubSub publishMsgWithSubject:[NSString stringWithFormat:@"%@Canceld", FEEDBACK_SERVICE] andDataDic:[NSDictionary dictionaryWithObject:FEEDBACK_SERVICE forKey:@"serviceName"]];
}

#pragma mark - iHRequestDelegate
- (void)requestDidCanceld {
    [super requestDidCanceld];
}

- (void)serviceCallSuccess:(iHResponseSuccess *)response {
    [super serviceCallSuccess:response];
    if ([response.serviceName isEqualToString:CALL_ME_SERVICE]) {
        [self restoreDefaultCallCounter];
    }
    
}


@end
