package com.example.prm391x_asm3_option2_duytqfx11834.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm391x_asm3_option2_duytqfx11834.R;
import com.example.prm391x_asm3_option2_duytqfx11834.activity.MainActivity;
import com.example.prm391x_asm3_option2_duytqfx11834.adapter.AnimalAdapter;
import com.example.prm391x_asm3_option2_duytqfx11834.model.Animal;

import java.util.ArrayList;

public class AnimalFragment extends Fragment implements View.OnClickListener{
    private Context mContext; //Context
    private String topicName; //Tên chủ đề được hiển thị
    private int topicId; //Id chủ đề được hiển thị
    private ArrayList<Animal> listAnimal; //Danh sách con vật trong chủ đề
    private DrawerLayout drawerLayout; //Drawer Layout


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // //Truyền layout vào fragment animal
        View rootView = inflater.inflate(R.layout.fragment_animal, container, false);

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
     * Phương thức initViews khởi tạo các view trong màn hình animal
     *
     * @param v View gốc màn hình
     * */
    private void initViews(View v) {
        //Ánh xạ các view
        ImageView ivMenu = v.findViewById(R.id.iv_menu); //Icon Menu
        drawerLayout = v.findViewById(R.id.drawer_layout); //Drawer Layout
        TextView tvMenu = v.findViewById(R.id.tv_function_name); //Tiêu đề trên action bar
        TableRow trSeas = v.findViewById(R.id.seas_menu); //Mục Seas trong menu
        TableRow trMammals = v.findViewById(R.id.mammals_menu); //Mục Mammals trong menu
        TableRow trBirds = v.findViewById(R.id.birds_menu); //Mục Birds trong menu

        //Thiết lập action bar
        String topicFirstLetter = topicName.substring(0, 1).toUpperCase();
        String topicRemainLetter = topicName.substring(1);
        tvMenu.setText(topicFirstLetter + topicRemainLetter);
        ////Thiết lập mở menu khi click vào icon
        ivMenu.setOnClickListener(v1 -> drawerLayout.openDrawer(GravityCompat.START));

        //Thiết lập khi click vào các mục trong menu
        trSeas.setOnClickListener(this);
        trMammals.setOnClickListener(this);
        trBirds.setOnClickListener(this);


        //Đổ Adapter vào Recycler View
        RecyclerView rvAnimal = v.findViewById(R.id.rv_animal);
        rvAnimal.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false));
        rvAnimal.setAdapter(new AnimalAdapter(topicId, listAnimal, mContext));
    }

    /**
     * Phương thức setData thiết lập dữ liệu cho fragment
     *
     * @param topicId Id chủ đề
     * @param topicName tên chủ đề
     * @param listAnimal Danh sách các con vật theo chủ đề
     * */
    public void setData(int topicId, String topicName, ArrayList<Animal> listAnimal) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.listAnimal = listAnimal;
    }

    /**
     * Phương thức onClick thiết lập khi click vào các mục trong menu
     *
     * @param v mục được chọn
     * */
    @Override
    public void onClick(View v) {
        //Đóng menu
        drawerLayout.closeDrawer(GravityCompat.START);

        //Hiển thị Fragment với chủ đề tương ứng
        ((MainActivity)mContext).showAnimal(Integer.parseInt((String) v.getTag()), false);
    }
}
