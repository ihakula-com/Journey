//
//  JHomeCell.m
//  Journey
//
//  Created by Wayde Sun on 6/30/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#import "JHomeCell.h"
#import "JHomeCellView.h"

@implementation JHomeCell

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        _cellView = [JHomeCellView viewFromNib];
        
        [self.contentView addSubview:_cellView];
        
    }
    return self;
}

- (void)setValues:(NSDictionary *)keyedValues {
    NSString *title = [keyedValues objectForKey:@"title"];
    NSString *description = [keyedValues objectForKey:@"description"];
    NSString *image = [keyedValues objectForKey:@"image"];
    
    CGSize realSize = [description sizeWithFont:_cellView.descriptionLabel.font constrainedToSize:CGSizeMake(_cellView.descriptionLabel.width, 1000)];
    if (realSize.height > 21) {
        _cellView.titleLabel.top = 2;
        _cellView.descriptionLabel.top = 20;
        _cellView.descriptionLabel.height = realSize.height;
    } else {
        _cellView.titleLabel.top = 6;
        _cellView.descriptionLabel.top = 22;
        _cellView.descriptionLabel.height = 21;
    }
    
    _cellView.titleLabel.text = title;
    _cellView.descriptionLabel.text = description;
    _cellView.iconImageView.image = ImageNamed(image);
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
