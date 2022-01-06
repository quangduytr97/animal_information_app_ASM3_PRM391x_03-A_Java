package com.example.prm391x_asm3_option2_duytqfx11834.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.prm391x_asm3_option2_duytqfx11834.R;
import com.example.prm391x_asm3_option2_duytqfx11834.model.Animal;

public class PhoneDialog extends Dialog {
    private final Context mContext; //Context
    private final Animal animal; //Con vật hiện tại
    private String phone; //Số điện thoại
    private final PhoneDialogListener listener; //Listener để truyền dữ liệu

    public PhoneDialog(Context mContext, PhoneDialogListener listener, Animal animal, String phone) {
        super(mContext);
        this.mContext = mContext;
        this.listener = listener;
        this.animal = animal;
        this.phone = phone;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Thiết lập không có tiêu đề
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Truyền vào layout
        setContentView(R.layout.phone_dialog);

        //Khởi tạo các view
        intViews();
    }

    /**
     * Phương thức initViews khởi tạo các view trong Dialog
     * */
    private void intViews() {
        //Ánh xạ các view
        ImageView ivAvatar = findViewById(R.id.iv_avatar); //Ảnh đại diện con vật
        EditText edtPhone = findViewById(R.id.edt_phone); //Khung nhập số điện thoại
        Button btnSave = findViewById(R.id.btn_save); //Nút save
        Button btnDelete = findViewById(R.id.btn_delete); //Nút delete

        //Thiết lập ảnh đại diện
        ivAvatar.setImageBitmap(animal.getPhoto());

        //Thiết lập khung nhập số
        edtPhone.setText(phone);

        //Tạo SharedPreferences Editor
        SharedPreferences sharedPreferencesAP = mContext.getSharedPreferences("animal_to_phone_pref",
                Context.MODE_PRIVATE); //key:animal, value:phone
        SharedPreferences.Editor editorAP = sharedPreferencesAP.edit(); //key:animal, value:phone

        SharedPreferences sharedPreferencesPA = mContext.getSharedPreferences("phone_to_animal_pref",
                Context.MODE_PRIVATE); //key:phone, value:animal
        SharedPreferences.Editor editorPA = sharedPreferencesPA.edit(); //key:phone, value:animal

        //Đường dẫn ảnh đại diện
        String photoPath = "avatar/" + animal.getTopicName() + "/ic_" + animal.getName().toLowerCase() + ".png";

        //Thiết lập nút Save
        btnSave.setOnClickListener(v -> {
            //Xóa dữ liệu trong Shared Preferences phone to animal
            editorPA.remove(phone);

            //Cập nhật số điện thoại
            phone = edtPhone.getText().toString();
            listener.showPhone(phone);

            if(phone.equals("")){
                //Nếu số điện thoại rỗng
                //Xóa dữ liệu trong Shared Preferences animal to phone
                editorAP.remove(photoPath + "_phone");
            } else {
                //Cập nhật dữ liệu trong Shared Preferences animal to phone
                editorAP.putString(photoPath + "_phone", phone);

                //Thêm dữ liệu trong Shared Preferences phone to animal
                editorPA.putString(phone, photoPath);
            }
            editorAP.apply();
            editorPA.apply();

            //Tắt Dialog
            dismiss();
        });

        //Thiết lập nút Delete
        btnDelete.setOnClickListener(v -> {
            //Xóa dữ liệu trong 2 Shared Preferences
            editorAP.remove(photoPath + "_phone");
            editorPA.remove(phone);
            editorAP.apply();
            editorPA.apply();

            //Xóa số điện thoại
            phone = "";
            listener.showPhone(phone);

            //Tắt Dialog
            dismiss();
        });
    }

    /**
     * Interface PhoneDialogListener giúp hiển thị dữ liệu từ Dialog
     * */
    public interface PhoneDialogListener {
        /**
         * Phương thức showPhone giúp hiển thị dữ liệu là số điện thoại
         *
         * @param phone Số điện thoại cần hiển thị
         * */
        void showPhone(String phone);
    }
}
