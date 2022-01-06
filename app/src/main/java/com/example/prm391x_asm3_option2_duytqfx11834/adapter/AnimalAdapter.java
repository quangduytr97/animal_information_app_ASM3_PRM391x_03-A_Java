package com.example.prm391x_asm3_option2_duytqfx11834.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm391x_asm3_option2_duytqfx11834.R;
import com.example.prm391x_asm3_option2_duytqfx11834.activity.MainActivity;
import com.example.prm391x_asm3_option2_duytqfx11834.model.Animal;

import java.util.ArrayList;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalHolder> {
    private final int topicId; //Id chủ đề được hiển thị
    private final ArrayList<Animal> listAnimal; //Danh sách động vật theo chủ đề
    private final Context mContext; //Context

    public AnimalAdapter(int topicId, ArrayList<Animal> listAnimal, Context mContext) {
        this.topicId = topicId;
        this.listAnimal = listAnimal;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AnimalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Truyền layout
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_animal, parent, false);
        return new AnimalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalHolder holder, int position) {
        Animal item = listAnimal.get(position);

        //Đặt ảnh đại diện
        holder.ivAvatar.setImageBitmap(item.getPhoto());

        //Hiện tên con vật
        holder.tvAnimalName.setText(item.getName());

        //Gắn tag vào tên con vật
        holder.tvAnimalName.setTag(item);

        //Cho biết con vật có được yêu thích không
        if(item.isFav()){
            holder.ivFavorite.setVisibility(View.VISIBLE);
        } else {
            holder.ivFavorite.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listAnimal.size();
    }

    public class AnimalHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar; //Image View chứa ảnh đại diện
        TextView tvAnimalName; //Tên con vật
        ImageView ivFavorite; //Biểu tượng con vật được yêu thích

        public AnimalHolder(@NonNull View itemView) {
            super(itemView);
            //Ánh xạ các view
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvAnimalName = itemView.findViewById(R.id.tv_animal_name);
            ivFavorite = itemView.findViewById(R.id.iv_favorite);

            //Hiệu ứng alpha cho các icon con vật
            Animation iconAlpha = AnimationUtils.loadAnimation(mContext, R.anim.icon_alpha);
            ivAvatar.startAnimation(iconAlpha);

            //Thiết lập khi click vào các con vật sẽ đi đến trang chi tiết
            itemView.setOnClickListener(v -> ((MainActivity)mContext).showDetail(topicId, (Animal) tvAnimalName.getTag()));
        }
    }
}
