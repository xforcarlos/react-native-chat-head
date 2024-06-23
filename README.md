# React Native Chat Head 🚀

`react-native-chat-head` is a React Native library that allows you to create Facebook Messenger-like chat heads for Android applications.

<p>
  <img width="125" src="./docs/demo.gif" alt="Demo">
</p>

## Features

- Create customizable chat heads
- Display badges on chat heads
- Request and manage overlay permissions
- Simple API to show, hide, and update chat heads

## Installation

To install the package, use npm or yarn:

```bash
npm install react-native-chat-head
# or
yarn add react-native-chat-head
```

### Setup

#### Step 1: Create Layout Files

Create `android/app/src/main/res/layout/chat_head_layout.xml` with the following content:

```xml
<?xml version="1.0" encoding="utf-8" ?>
<RelativeLayout
  xmlns:android="http://schemas.android.com/apk/res/android"
  xmlns:tools="http://schemas.android.com/tools"
  android:layout_width="65dp"
  android:id="@+id/chat_head_root"
  android:layout_height="wrap_content"
  android:orientation="vertical"
>
    <!-- Profile image for the chat head. -->
    <ImageView
      android:id="@+id/chat_head_profile_iv"
      android:layout_width="60dp"
      android:layout_height="60dp"
      android:layout_marginTop="8dp"
      android:src="@mipmap/ic_launcher"
      tools:ignore="ContentDescription"
    />
    <!-- Close button -->
    <ImageView
      android:id="@+id/close_btn"
      android:layout_width="20dp"
      android:layout_height="20dp"
      android:layout_marginLeft="40dp"
      android:src="@drawable/ic_close"
      tools:ignore="ContentDescription"
    />
    <!-- Chat head badge -->
    <TextView
      android:id="@+id/chat_head_badge"
      android:layout_margin="5dp"
      android:layout_width="26dp"
      android:layout_height="26dp"
      android:layout_alignParentStart="true"
      android:text="1"
    />
</RelativeLayout>
```

Create `android/app/src/main/res/layout/dismiss_icon_layout.xml` with the following content:

```xml
<RelativeLayout
  xmlns:android="http://schemas.android.com/apk/res/android"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:layout_gravity="bottom|center_horizontal"
>
    <!-- Dismiss icon -->
    <ImageView
      android:id="@+id/dismiss_icon"
      android:layout_width="48dp"
      android:layout_height="48dp"
      android:layout_gravity="bottom|center_horizontal"
      android:src="@android:drawable/ic_menu_close_clear_cancel"
      android:layout_marginBottom="16dp"
    />
</RelativeLayout>
```

Add an image named `ic_close.png` in `android/app/src/main/res/drawable-nodpi`.

## Usage

Import the library and use the provided methods to manage chat heads:

```javascript
import ChatHead from "react-native-chat-head";

// Check if overlay permission is granted
const hasPermission = await ChatHead.checkOverlayPermission();

// Request overlay permission
await ChatHead.requestPermission();

// Show chat head
ChatHead.showChatHead();

// Hide chat head
ChatHead.hideChatHead();

// Update chat badge count
ChatHead.updateChatBadgeCount(1);
```

## API

- `checkOverlayPermission()`: Checks if the overlay permission is granted. Returns a boolean.
- `requestPermission()`: Requests the overlay permission from the user.
- `showChatHead()`: Displays the chat head.
- `hideChatHead()`: Hides the chat head.
- `updateChatBadgeCount(count)`: Updates the badge count on the chat head.

## Contributing

We welcome contributions! Please see the [contributing guide](https://github.com/xforcarlos/react-native-chat-head/blob/main/CONTRIBUTING.md) to learn how to get involved.

## License

This project is licensed under the MIT License. See the [LICENSE](https://github.com/xforcarlos/react-native-chat-head/blob/main/LICENSE) file for more details.
