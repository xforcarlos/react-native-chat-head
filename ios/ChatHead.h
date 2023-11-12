
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNChatHeadSpec.h"

@interface ChatHead : NSObject <NativeChatHeadSpec>
#else
#import <React/RCTBridgeModule.h>

@interface ChatHead : NSObject <RCTBridgeModule>
#endif

@end
