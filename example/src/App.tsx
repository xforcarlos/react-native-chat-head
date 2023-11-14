import * as React from 'react';

import { StyleSheet, View, Text, Button } from 'react-native';
import {
  showChatHead,
  hideChatHead,
  updateChatHeadText,
} from 'react-native-chat-head';

__DEV__ && console.log('multiply', showChatHead, showChatHead);
export default function App() {
  const [result, setResult] = React.useState<number | undefined>();

  React.useEffect(() => {}, []);

  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
      <Button title="Show Chat Head" onPress={() => showChatHead()} />
      <Button title="Hide Chat Head" onPress={() => hideChatHead()} />

      <Button
        title="Update Chat Head Text"
        onPress={() => updateChatHeadText('15')}
      />
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
