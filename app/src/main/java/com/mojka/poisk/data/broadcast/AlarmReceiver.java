package com.mojka.poisk.data.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.activity.OrderListActivity;
import com.mojka.poisk.utils.DateUtils;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "ProfileActivity";

    public static final String KEY_ORDER_ID = "KEY_ORDER_ID";
    public static final String KEY_ORDER_DATE = "KEY_ORDER_DATE";

    @Override
    public void onReceive(Context context, Intent intent) {
        Integer orderId = intent.getExtras().getInt(KEY_ORDER_ID, -1);
        Long orderDate = intent.getExtras().getLong(KEY_ORDER_DATE, 0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        PendingIntent pIntent = PendingIntent.getActivity(context,
                orderId,
                new Intent(context, OrderListActivity.class),
                0);

        Notification notification = new Notification.Builder(context)
                .setContentTitle("МойкАвто")
                .setContentText("Ваша запись состоится сегодня в (" + DateUtils.millisToPatternWithLocal(orderDate, DateUtils.PATTERN_HOUR_MIN) + "). МойкАвто")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(0, notification);
    }
}
