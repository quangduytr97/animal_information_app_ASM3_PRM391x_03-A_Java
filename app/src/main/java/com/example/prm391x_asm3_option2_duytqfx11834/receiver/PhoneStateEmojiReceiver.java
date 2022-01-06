package com.example.prm391x_asm3_option2_duytqfx11834.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.prm391x_asm3_option2_duytqfx11834.R;

import java.io.IOException;

public class PhoneStateEmojiReceiver extends BroadcastReceiver  {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            //Tạo mới đối tượng Telephony Manager
            TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            telephony.listen(new PhoneStateListener() {
                @Override
                public void onCallStateChanged(int state, String incomingNumber) {
                    super.onCallStateChanged(state, incomingNumber);

                    //Nếu có cuộc gọi đến
                    if (state == TelephonyManager.CALL_STATE_RINGING) {
                        //Thực hiện hiện emoji toast
                        try {
                            makeEmojiToast(context, incomingNumber);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    /**
     * Phương thức makeEmojiToast hiện một Emoji Toast
     *
     * @param context Context
     * @param incomingNumber Số gọi đến
     * */
    private void makeEmojiToast(Context context, String incomingNumber) throws IOException {
        //Inflate layout
        View layout = LayoutInflater.from(context).inflate(R.layout.item_toast, (ViewGroup) null);

        //Ánh xạ view
        ImageView ivToast = layout.findViewById(R.id.iv_toast);

        //Gọi Shared Preferences
        SharedPreferences sharedPreferencesPA = context.getSharedPreferences("phone_to_animal_pref",
                Context.MODE_PRIVATE); //key:phone, value:animal
        String photoPath = sharedPreferencesPA.getString(incomingNumber, "");

        //Nếu số điện thoại cho phép hiện emojji
        if(!photoPath.equals("")) {
            //Gán ảnh vào toast
            ivToast.setImageBitmap(BitmapFactory.decodeStream(context.getAssets().open(photoPath)));

            //Tạo và hiện toast
            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }
    }
}
