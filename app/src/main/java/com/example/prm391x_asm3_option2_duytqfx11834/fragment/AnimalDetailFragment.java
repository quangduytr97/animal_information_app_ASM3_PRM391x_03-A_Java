package com.example.prm391x_asm3_option2_duytqfx11834.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.prm391x_asm3_option2_duytqfx11834.R;
import com.example.prm391x_asm3_option2_duytqfx11834.activity.MainActivity;
import com.example.prm391x_asm3_option2_duytqfx11834.adapter.AnimalDetailAdapter;
import com.example.prm391x_asm3_option2_duytqfx11834.model.Animal;

import java.util.ArrayList;

public class AnimalDetailFragment extends Fragment {
    private Context mContext; //Context
    private ArrayList<Animal> listAnimal; //Danh sách con vật trong chủ đề
    private String topicName; //Tên chủ đề được hiển thị
    private int topicId; //Id chủ đề được hiển thị
    private Animal currentAnimal; //Con vật hiện tại được hiển thị

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Truyền layout vào fragment detail
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        //Khởi tạo các view
        initViews(rootView);

        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * Phương thức initViews khởi tạo các view trong màn hình detail
     *
     * @param v View gốc màn hình
     */
    private void initViews(View v) {
        //Ánh xạ các view
        ImageView ivMenu = v.findViewById(R.id.iv_menu); //Icon Menu
        TextView tvMenu = v.findViewById(R.id.tv_function_name); //Tiêu đề trên action bar

        //Thiết lập action bar
        String topicFirstLetter = topicName.substring(0, 1).toUpperCase();
        String topicRemainLetter = topicName.substring(1);
        tvMenu.setText(topicFirstLetter + topicRemainLetter);
        ivMenu.setImageResource(R.drawable.ic_back);

        //Đổ adapter vào ViewPager
        ViewPager viewPager = v.findViewById(R.id.vp_detail);
        AnimalDetailAdapter animalDetailAdapter = new AnimalDetailAdapter(listAnimal, mContext);
        viewPager.setAdapter(animalDetailAdapter);
        viewPager.setCurrentItem(listAnimal.indexOf(currentAnimal), true);

        //Thiết lập quay lại trang danh sách con vật khi click vào icon
        ivMenu.setOnClickListener(v1 -> {
            //Cập nhật lại danh sách tại Main Activity
            ((MainActivity) mContext).updateListAnimal(listAnimal, topicId);

            //Mở trang danh sách con vật
            ((MainActivity)mContext).showAnimal(topicId, true);
        });
    }

    /**
     * Phương thức setData thiết lập dữ liệu hiển thị cho trang chi tiết
     *
     * @param topicId Id chủa chủ đề
     * @param listAnimal Danh sách các con vật trong chủ đề
     * @param currentAnimal Con vật hiện tại
     * */
    public void setData(int topicId, ArrayList<Animal> listAnimal, Animal currentAnimal) {
        this.topicId = topicId;
        this.listAnimal = listAnimal;
        this.currentAnimal = currentAnimal;
        this.topicName = currentAnimal.getTopicName();
    }
}
