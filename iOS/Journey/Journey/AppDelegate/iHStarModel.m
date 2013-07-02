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
        self.sightsMottoArr = [NSArray arrayWithObjects:
                               @"西双版纳：雀之灵，热带雨林奇异美景",
                               @"九乡：风吹石弯，地下倒石林",
                               @"石林：鬼斧神工，啧啧称奇",
                               @"崇圣：看三塔倒影，携朝圣之心",
                               @"玉龙雪山：云不念峰，岭岭若洗",
                               @"丽江古城：酒不醉人人自醉，纳西风情",
                               @"白水台：“仙人遗田”名副其实",
                               @"松赞林寺：小布达拉宫，庄严而神秘",
                               @"泸沽三岛：摩梭人心中的圣湖",
                               @"泸沽湖：湛蓝湖水，宛如世外桃园",
                               nil];
    }
    return self;
}

@end
