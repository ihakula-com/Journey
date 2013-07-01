//
//  iHStarModel.m
//  Journey
//
//  Created by Wayde Sun on 6/30/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import "iHStarModel.h"

@implementation iHStarModel

- (id)init {
    self = [super init];
    if (self) {
        NSString *dataPath = [[NSBundle mainBundle] pathForResource:@"sightSpotHome" ofType:@"plist"];
        NSDictionary *dataDic = [NSDictionary dictionaryWithContentsOfFile:dataPath];
        self.servicesArr = [dataDic objectForKey:@"data"];
    }
    return self;
}

@end
