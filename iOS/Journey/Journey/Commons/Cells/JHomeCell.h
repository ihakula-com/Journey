//
//  JHomeCell.h
//  Journey
//
//  Created by Wayde Sun on 6/30/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import <UIKit/UIKit.h>

@class  JHomeCellView;
@interface JHomeCell : UITableViewCell {
    JHomeCellView *_cellView;
}

- (void)setValues:(NSDictionary *)keyedValues;

@end
