import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-chat-head' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const ChatHead = NativeModules.ChatHead
  ? NativeModules.ChatHead
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function showChatHead(): Promise<boolean> {
  return ChatHead.showChatHead();
}

export function hideChatHead(): Promise<boolean> {
  return ChatHead.hideChatHead();
}

export function updateChatHeadText(text: string): Promise<boolean> {
  return ChatHead.updateChatHeadText(text);
}

export function updateChatBadgeCount(count: number): Promise<boolean> {
  if (typeof count !== 'number') {
    throw new Error('count must be a number');
  }
  return ChatHead.updateBadgeCount(count);
}

export const getCount = (): number => Number(ChatHead.getCount());
