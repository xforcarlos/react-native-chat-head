package com.chathead;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChatHeadModule extends ReactContextBaseJavaModule {
  public static final String NAME = "ChatHead";
  private static final int OVERLAY_PERMISSION_REQ_CODE = 1234;
  private final ReactApplicationContext context;
  private WindowManager windowManager;
  private View chatHeadView;
  private WindowManager.LayoutParams params;
  private TextView chatHeadBadge; // Add this line to declare the TextView for badge count

  Boolean isOpen = false;
  public ChatHeadModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.context = reactContext;
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }





  // Use this method to get the current MainActivity instance
  private Activity getMainActivity() {
    return getCurrentActivity();
  }
  public void startMainActivity() {
    Activity mainActivity = getMainActivity();
    if (mainActivity != null) {
      Intent intent = new Intent(mainActivity, getMainActivity().getClass());
      mainActivity.startActivity(intent);
    }
  }

  public void findChatHeadBadge() {
    // Retrieve the chat head badge TextView when the host resumes
    int badgeId = context.getResources().getIdentifier("chat_head_badge", "id", context.getPackageName());
    if (chatHeadView != null) {
      chatHeadBadge = chatHeadView.findViewById(badgeId);
    }
  }
  private void RunHandler() {
     isOpen = true;
    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run() {
        if (windowManager == null) {
          windowManager = (WindowManager) context.getSystemService(Service.WINDOW_SERVICE);
        }

        params = new WindowManager.LayoutParams(
          WindowManager.LayoutParams.WRAP_CONTENT,
          WindowManager.LayoutParams.WRAP_CONTENT,
          Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
            WindowManager.LayoutParams.TYPE_PHONE,
          WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
          PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 100;

        LayoutInflater inflater = LayoutInflater.from(context);
        chatHeadView = inflater.inflate(context.getResources().getIdentifier("chat_head_layout", "layout", context.getPackageName()), null);

        chatHeadView.setOnTouchListener(new View.OnTouchListener() {
          private int initialX;
          private int initialY;
          private float initialTouchX;
          private float initialTouchY;
          private int lastAction;

          @Override
          public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
              case MotionEvent.ACTION_DOWN:
                //remember the initial position.
                initialX = params.x;
                initialY = params.y;
                //get the touch location
                initialTouchX = event.getRawX();
                initialTouchY = event.getRawY();
                lastAction = event.getAction();
                return true;
              case MotionEvent.ACTION_UP:
                //As we implemented on touch listener with ACTION_MOVE,
                //we have to check if the previous action was ACTION_DOWN
                //to identify if the user clicked the view or not.
                if (lastAction == MotionEvent.ACTION_DOWN) {
                  //Open the chat conversation click.
                  Activity activity = getCurrentActivity();
                  startMainActivity();
                  //close the service and remove the chat heads
                }
                lastAction = event.getAction();
                return true;
              case MotionEvent.ACTION_MOVE:
                //Calculate the X and Y coordinates of the view.
                params.x = initialX + (int) (event.getRawX() - initialTouchX);
                params.y = initialY + (int) (event.getRawY() - initialTouchY);
                //Update the layout with new X & Y coordinate
                windowManager.updateViewLayout(chatHeadView, params);
                lastAction = event.getAction();
                return true;
            }
            return false;
          }
        });


        ImageView closeBtn = chatHeadView.findViewById(context.getResources().getIdentifier("close_btn", "id", context.getPackageName()));
        closeBtn.setOnClickListener(v -> {
          //close the service and remove the chat head from the window
          hideChatHead();
        });

        ImageView chatHeadImage = chatHeadView.findViewById(context.getResources().getIdentifier("chat_head_profile_iv","id",context.getPackageName()));
        windowManager.addView(chatHeadView, params);
        findChatHeadBadge();
      }
    });
  }

  @ReactMethod
  public void showChatHead() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
      Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
        Uri.parse("package:" + context.getPackageName()));
      getCurrentActivity().startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
    } else {
      if (!isOpen) {
        RunHandler();
      }

    }
  }
  @ReactMethod
  public void hideChatHead() {
    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run() {
        if (windowManager != null && chatHeadView != null && chatHeadView.isAttachedToWindow()) {
          windowManager.removeView(chatHeadView);
          chatHeadView = null;
          isOpen = false;
        }
      }
    });
  }
  @ReactMethod
  public void updateBadgeCount(int count) {
    if (chatHeadBadge != null) {
      chatHeadBadge.setText(String.valueOf(count));
    }
  }

}
