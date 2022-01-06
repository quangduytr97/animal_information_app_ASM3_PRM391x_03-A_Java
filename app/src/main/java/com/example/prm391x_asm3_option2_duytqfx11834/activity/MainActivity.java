package com.example.prm391x_asm3_option2_duytqfx11834.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.prm391x_asm3_option2_duytqfx11834.R;
import com.example.prm391x_asm3_option2_duytqfx11834.fragment.AnimalDetailFragment;
import com.example.prm391x_asm3_option2_duytqfx11834.fragment.AnimalFragment;
import com.example.prm391x_asm3_option2_duytqfx11834.fragment.HomeFragment;
import com.example.prm391x_asm3_option2_duytqfx11834.model.Animal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ArrayList<Animal>> listAnimal; //Danh sách các con vật
    private static final String[] topics = {"home", "seas", "mammals", "birds"}; //Tên các chủ đề
    private static final int REQUEST_CODE = 999999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Khởi tạo bộ nhớ cho listAnimal
        listAnimal = new ArrayList<>();

        //Kiểm tra các quyền
        checkPermissions();

        //Tải dữ liệu
        try {
            loadListAnimal();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Hiển thị fragment home
        showHome();
    }

    /**
     * Phương thức checkPermissions kiểm tra các quyền cần thiết của ứng dụng
     * */
    private void checkPermissions() {
        // Với API >= 23, cần hỏi người dùng để cấp quyền
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                //Nếu chưa được cấp quyền, tiến hành hiện hộp thoại để hỏi người dùng
                requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
            }
        }
    }

    /**
     * Phương thức onRequestPermissionResult kiểm tra kết quả người dùng trả lời trong hộp thoại
     * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE) {
            //Nếu người dùng không đồng ý cấp quyền
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                //Hiện thông báo
                Toast.makeText(this, "Please allow this permission to use features of the app", Toast.LENGTH_LONG).show();

                //Tắt chương trình
                finish();
            }
        }
    }

    /**
     * Phương thức loadListAnimal tải dữ liệu các con vật theo chủ đề
     * */
    private void loadListAnimal() throws IOException {
        for(int i = 1;  i <= 3; i++) {
            //Mở folder chứa thông tin các con vật dựa theo chủ đê
            AssetManager assetManager = this.getAssets();
            String[] listFile = assetManager.list("avatar/" + topics[i]); //Danh sách file

            //Duyệt các con vật trong chủ đề
            ArrayList<Animal> listTmp = new ArrayList<>(); //Danh sách tạm thời lưu các con vật

            for(String fileName: listFile) {
                //Lấy tên con vật do tên file là ic_
                String animalName = fileName.substring(3, fileName.indexOf("."));

                //Lấy ảnh đại diện
                String photoPath = "avatar/" + topics[i] + "/" + fileName;//Đường dẫn ảnh đại diện
                Bitmap photo = BitmapFactory.decodeStream(assetManager.open(photoPath));//Ảnh đại diện

                //Lấy ảnh bìa
                String photoBgPath = "background/" + topics[i] + "/bg_" + animalName + ".jpg"; //Đường dẫn ảnh bìa
                Bitmap photoBg = BitmapFactory.decodeStream(assetManager.open(photoBgPath));//Ảnh bìa

                //Lấy nội dung mô tả
                StringBuilder content = new StringBuilder();
                String str;
                InputStream inputStream = assetManager.open("description/" + topics[i] + "/des_" + animalName + ".txt");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                //Tiến hành đọc file và nối vào content
                while ((str = bufferedReader.readLine()) != null) {
                    content.append(str).append("\n");
                }
                bufferedReader.close();

                //Chuyển tên con vật thành viết hoa chữ cái đầu
                String animalNameFirstLetter = animalName.substring(0, 1).toUpperCase();
                String animalNameRemainLetter = animalName.substring(1);
                animalName = animalNameFirstLetter + animalNameRemainLetter;

                //Tải dữ liệu về việc các con vật được yêu thích không qua Share Preferences
                SharedPreferences sharedPreferences = getSharedPreferences("animal_favorite_pref", MODE_PRIVATE);
                boolean isFav = sharedPreferences.getBoolean("avatar/" + topics[i] + "/" + fileName, false);



                //Lưu các con vật vào danh sách tạm thời
                listTmp.add(new Animal(topics[i], photo, photoBg, animalName, content.toString(), isFav));
            }

            //Đưa danh sách mới tạo vào danh sách chính
            listAnimal.add(listTmp);
        }
    }

    /**
     * Phương thức showHome hiển thị fragment Home lên màn hình
     * */
    public void showHome() {
        Fragment frg = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Tạo hiệu ứng khi chuyển màn hình
        fragmentTransaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit);
        fragmentTransaction.replace(R.id.ln_main, frg);
        fragmentTransaction.commit();
    }

    /**
     * Phương thức showAnimal hiển thị fragment Animal lên màn hình
     *
     * @param topicId Id của chủ đề cần hiển thị
     * @param showAnimation Hiện hiệu ứng chuyển màn hình hay không
     * */
    public void showAnimal(int topicId, boolean showAnimation) {
        AnimalFragment frg = new AnimalFragment();
        //Thiết lập dữ liệu cho fragment
        frg.setData(topicId, topics[topicId], listAnimal.get(topicId - 1));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(showAnimation) {
            //Tạo hiệu ứng khi chuyển màn hình
            fragmentTransaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit);
        }
        fragmentTransaction.replace(R.id.ln_main, frg);
        fragmentTransaction.commit();
    }

    /**
     * Phương thức showDetail hiển thị fragment Detail lên màn hình
     *
     * @param topicId Id của chủ đề cần hiển thị
     * @param currentAnimal Con vật được hiển thị
     * */
    public void showDetail(int topicId, Animal currentAnimal) {
        AnimalDetailFragment frg = new AnimalDetailFragment();
        //Thiết lập dữ liệu cho fragment
        frg.setData(topicId, listAnimal.get(topicId - 1), currentAnimal);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Tạo hiệu ứng khi chuyển màn hình
        fragmentTransaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit);
        fragmentTransaction.replace(R.id.ln_main, frg);
        fragmentTransaction.commit();
    }

    /**
     * Phương thức updateListAnimal cập nhật lại dánh sách các con vật
     *
     * @param listAnimal Danh sách con vật
     * @param topicId Id của chủ đề
     * */
    public void updateListAnimal(ArrayList<Animal> listAnimal, int topicId) {
        //Cập nhật lại danh sách con vật tương ứng
        this.listAnimal.set(topicId - 1, listAnimal);
    }
}