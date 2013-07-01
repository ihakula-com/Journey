//
//  JStarChoiceModel.m
//  Journey
//
//  Created by Wayde Sun on 7/1/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import "JStarChoiceModel.h"

@implementation JStarChoiceModel

- (id)init {
    self = [super init];
    if (self) {
        NSString *dataPath = [[NSBundle mainBundle] pathForResource:@"sightSpotHome" ofType:@"plist"];
        NSDictionary *dataDic = [NSDictionary dictionaryWithContentsOfFile:dataPath];
        self.productsArr = [dataDic objectForKey:@"data"];
    }
    return self;
}

@end
