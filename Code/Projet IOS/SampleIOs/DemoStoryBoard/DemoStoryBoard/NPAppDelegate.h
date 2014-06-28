//
//  NPAppDelegate.h
//  DemoStoryBoard
//
//  Created by Fabrice Dewasmes on 5/23/14.
//  Copyright (c) 2014 NEOPIXL. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface NPAppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;
@property (nonatomic, copy) NSString *title;
@property (nonatomic, copy) NSNumber *date;
@property (nonatomic, copy) NSString *author;
@property (nonatomic, copy) NSString *actors;
@property (nonatomic, assign) BOOL *vailable;
@property (nonatomic, assign) BOOL *view;


@end
