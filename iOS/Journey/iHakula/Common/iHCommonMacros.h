//
//  iHCommonMacros.h
//  iHakula
//
//  Created by Wayde Sun on 2/21/13.
//  Copyright (c) 2013 iHakula. All rights reserved.
//

#ifndef iHakula_iHCommonMacros_h
#define iHakula_iHCommonMacros_h

#pragma mark - shortcuts

#define USER_DEFAULT [NSUserDefaults standardUserDefaults]

#define ImageNamed(_pointer) [UIImage imageNamed:_pointer]


////////////////////////////////////////////////////////////////////////////////
#pragma mark - common functions

#define RELEASE_SAFELY(__POINTER) { if(__POINTER) { [__POINTER release]; __POINTER = nil; }}


////////////////////////////////////////////////////////////////////////////////
#pragma mark - degrees/radian functions

#define degreesToRadian(x) (M_PI * (x) / 180.0)

#define radianToDegrees(radian) (radian*180.0)/(M_PI)

////////////////////////////////////////////////////////////////////////////////
#pragma mark - color functions

#define RGBCOLOR(r,g,b) [UIColor colorWithRed:(r)/255.0f green:(g)/255.0f blue:(b)/255.0f alpha:1]

#define RGBACOLOR(r,g,b,a) [UIColor colorWithRed:(r)/255.0f green:(g)/255.0f blue:(b)/255.0f alpha:(a)]

////////////////////////////////////////////////////////////////////////////////
#pragma mark - System and Screen adapter
#define IS_IPHONE_5 (fabs((double)[[UIScreen mainScreen] bounds].size.height - (double )568) < DBL_EPSILON ) 
#define IS_IOS_5    floorf([[UIDevice currentDevice].systemVersion floatValue]) == 5.0 ? 1 : 0
#define IPHONE_SCREEN_WIDTH     320
#define IPHONE_SCREEN_5_HEIGHT  568
#define IPHONE_SCREEN_HEIGHT    480
#define IPHONE_DEVICE_HEIGHT    [[UIScreen mainScreen] bounds].size.height;

////////////////////////////////////////////////////////////////////////////////
#pragma mark - App
#define APP_DID_ENTER_BACKGROUND    @"AppDidEnterBackground"

#ifndef SERVICE_ROOT_URL
//#define SERVICE_ROOT_URL            @"http://www.baidu.com"
#endif

#ifndef HOST_NAME
//#define HOST_NAME                   @"www.baidu.com"
#endif

#endif
