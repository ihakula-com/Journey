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
             NSLog(@"Country = %@", placemark.country);
             NSLog(@"Postal Code = %@", placemark.postalCode);
             NSLog(@"Locality = %@", placemark.locality);
         } else if (error == nil && [placemarks count] == 0){
             NSLog(@"No results were returned.");
         } else if (error != nil){
             NSLog(@"An error occurred = %@", error);
         }
         
     }];
    
    return @"";
}


@end
