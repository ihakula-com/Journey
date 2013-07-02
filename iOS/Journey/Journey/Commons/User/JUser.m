//
//  JUser.m
//  Journey
//
//  Created by Wayde Sun on 7/1/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import "JUser.h"

@interface JUser ()

@end

@implementation JUser

- (id)init {
    self = [super init];
    if (self) {
        self.myLocation = nil;
        _address = nil;
    }
    return self;
}


#pragma mark - Public Methods
- (NSString *)getAddress {
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
             //
         } else if (error != nil){
             //
         }
         
     }];
    
    return @"";
}


@end
