import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import { MiPushNotifier } from 'react-native-push-notifier';

export default function App() {
  const [result, setResult] = React.useState<number | undefined>(1212121);

  React.useEffect(() => {
    MiPushNotifier.registerPush('2882303761517330064', '5911733016064');
    MiPushNotifier.setUserAccount('1030000010113915');
    MiPushNotifier.getRegId()
      .then((res) => {
        debugger;
      })
      .catch((err) => {
        debugger;
      });
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
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
