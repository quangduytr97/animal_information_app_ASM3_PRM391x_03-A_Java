package com.example.prm391x_asm3_option2_duytqfx11834.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.prm391x_asm3_option2_duytqfx11834.R;
import com.example.prm391x_asm3_option2_duytqfx11834.dialog.PhoneDialog;
import com.example.prm391x_asm3_option2_duytqfx11834.model.Animal;

import java.util.ArrayList;

public class AnimalDetailAdapter extends PagerAdapter {
    private final ArrayList<Animal> listAnimal; //Danh sách động vật trong nhóm
    private final Context mContext; //Context

    public AnimalDetailAdapter(ArrayList<Animal> listAnimal, Context mContext) {
        this.listAnimal = listAnimal;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //Truyền layout
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_detail, container, false);
        Animal item = listAnimal.get(position);

        //Ánh xạ các view
        ImageView ivAnimalCover = view.findViewById(R.id.iv_animal_cover); //Ảnh bìa con vật
        TextView tvAnimalName = view.findViewById(R.id.tv_animal_name); //Tên con vật
        ImageView ivPhone = view.findViewById(R.id.iv_phone); //Biểu tượng điện thoại
        TextView tvPhone = view.findViewById(R.id.tv_phone); //Số điện thoại
        ImageView ivFavorite = view.findViewById(R.id.iv_favorite); //Biểu tượng yêu thích
        TextView tvContent = view.findViewById(R.id.tv_content); //Nội dung mô tả

        //Thiết lập nội dung cho các view
        ivAnimalCover.setImageBitmap(item.getPhotoBg());
        tvAnimalName.setText(item.getName());
        tvContent.setText(item.getContent());
        //Nội dung số điện thoại
        SharedPreferences sharedPreferencesPhone = mContext.getSharedPreferences("animal_to_phone_pref",
                Context.MODE_PRIVATE);
        tvPhone.setText(sharedPreferencesPhone.getString("avatar/" + item.getTopicName() + "/ic_" +
                item.getName().toLowerCase() + ".png_phone", ""));


        //Nội dung nút yêu thích
        if (item.isFav()) {
            //Nếu con vật hiện tại đang được yêu thích
            ivFavorite.setImageResource(R.drawable.ic_favorite);
        } else {
            //Nếu con vật hiện tại không được yêu thích
            ivFavorite.setImageResource(R.drawable.ic_not_favorite);
        }

        //Thiết lập mở Dialog khi click vào icon điện thoại
        ivPhone.setOnClickListener(v -> {
            PhoneDialog.PhoneDialogListener listener = phone -> tvPhone.setText(phone);
            PhoneDialog phoneDialog = new PhoneDialog(mContext, listener, item, tvPhone.getText().toString());
            phoneDialog.show();
            phoneDialog.getWindow().setLayout(800, 1000);
        });

        //Thiết lập khi click vào icon yêu thích
        ivFavorite.setOnClickListener(v -> {
            //Sửa dữ liệu trong Shared Preferences
            SharedPreferences sharedPreferences = mContext.getSharedPreferences("animal_favorite_pref",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (item.isFav()) {
                //Nếu con vật hiện tại đang được yêu thích thì đổi thành không yêu thích
                ivFavorite.setImageResource(R.drawable.ic_not_favorite);
                item.setFav(false);

                //Xóa dữ liệu trong Shared Preferences
                editor.remove("avatar/" + item.getTopicName() + "/ic_" + item.getName().toLowerCase() + ".png");
            } else {
                //Nếu con vật hiện tại không được yêu thích
                ivFavorite.setImageResource(R.drawable.ic_favorite);
                item.setFav(true);

                //Thêm dữ liệu trong Shared Preferences
                editor.putBoolean("avatar/" + item.getTopicName() + "/ic_" + item.getName().toLowerCase() + ".png", true);
            }
            editor.apply();

            //Cập nhật lại danh sách
            listAnimal.set(position, item);
            notifyDataSetChanged();
        });

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return listAnimal.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
