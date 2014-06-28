//
//  NPTableViewCell.m
//  DemoStoryBoard
//
//  Created by Fabrice Dewasmes on 5/23/14.
//  Copyright (c) 2014 NEOPIXL. All rights reserved.
//

#import "NPTableViewCell.h"

@interface NPTableViewCell()
@property (weak, nonatomic) IBOutlet UILabel *label;

@end

@implementation NPTableViewCell

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)awakeFromNib
{
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
