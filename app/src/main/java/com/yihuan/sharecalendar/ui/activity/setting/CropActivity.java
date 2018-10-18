package com.yihuan.sharecalendar.ui.activity.setting;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.ui.view.cropimg.CropLayout;
import com.yihuan.sharecalendar.utils.FileUtils;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/28.
 * 裁剪
 */

public class CropActivity extends BaseActivity {
    public static final int REQUEST_CODE_CROP = 0x04;
    @BindView(R.id.cropLayout)
    CropLayout cropLayout;
    @BindView(R.id.tv_crop)
    TextView tvCrop;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;

    private static String path;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_crop;
    }

    @Override
    protected void initView() {
        UIUtils.displayImgBg(this, path, cropLayout.getImageView());
        int width = 700;
        int height = 700;
        if (getIntent() != null && getIntent().hasExtra("width") && getIntent().hasExtra("height")) {
            width = getIntent().getIntExtra("width", 700);
            height = getIntent().getIntExtra("height", 700);
        }
        cropLayout.setCropWidth(width);
        cropLayout.setCropHeight(height);
        cropLayout.start();
    }

    public static void show(Activity activity, String filePath) {
        show(activity, filePath, 700, 700);
    }

    public static void show(Activity activity, String filePath, int width, int height) {
        Intent intent = new Intent(activity, CropActivity.class);
        path = filePath;
        intent.putExtra("width", width);
        intent.putExtra("height", height);
        activity.startActivityForResult(intent, REQUEST_CODE_CROP);
    }

    @Override
    protected void refreshData() {

    }

    @OnClick({R.id.tv_crop, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_crop:
                Bitmap bitmap = null;
                FileOutputStream os = null;
                try {
                    bitmap = cropLayout.cropBitmap();
                    File file = new File(getFilesDir() + "/crop");
                    if(!file.exists()){
                        file.mkdirs();
                    }else{
                        deleteF(file);
                        file.mkdirs();
                    }
                    String path = System.currentTimeMillis()+ ".jpg";
                    File imgFile = new File(file.getPath(), path);
                    os = new FileOutputStream(imgFile.getAbsolutePath(), false);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.flush();
                    os.close();

                    Intent intent = new Intent();
                    intent.putExtra("crop_path", imgFile.getAbsolutePath());
                    setResult(Constants.REQUEST_CODE_3, intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bitmap != null) bitmap.recycle();
                    FileUtils.close(os);
                }
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    private void deleteF(File file) {
        if(file.isDirectory()){
            for (File f : file.listFiles()){
                if(f.isDirectory()){
                    deleteF(f);
                }else if(f.isFile()){
                    f.delete();
                }
            }
        }
    }
}
