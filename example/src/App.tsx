import * as React from 'react';

import { StyleSheet, View, Button } from 'react-native';
import {
  showChatHead,
  hideChatHead,
  updateChatBadgeCount,
} from 'react-native-chat-head';

export default function App() {
  React.useEffect(() => {}, []);

  return (
    <View style={styles.container}>
      <Button title="Show Chat Head" onPress={() => showChatHead()} />
      <Button title="Hide Chat Head" onPress={() => hideChatHead()} />
      <Button
        title="Update Chat Head Text"
        onPress={() => updateChatBadgeCount(15)}
      />
      <Button title="Get Count" onPress={() => {}} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
