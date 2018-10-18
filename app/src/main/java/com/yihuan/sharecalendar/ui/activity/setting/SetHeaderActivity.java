package com.yihuan.sharecalendar.ui.activity.setting;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.settting.UserBean;
import com.yihuan.sharecalendar.presenter.SetUserHeaderPresenter;
import com.yihuan.sharecalendar.presenter.contract.SetUserHeaderContract;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.ui.view.pickerview.utils.Utils;
import com.yihuan.sharecalendar.utils.FileUtils;
import com.yihuan.sharecalendar.utils.SDCardUtils;
import com.yihuan.sharecalendar.utils.UIUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/26.
 */

public class SetHeaderActivity extends BaseActivity<SetUserHeaderPresenter> implements SetUserHeaderContract.View {
    public static final int REQUEST_PERMISSION_READ = 110;
    public static final int REQUEST_CODE_CHOOSE = 111;
    public static final int REQUEST_PERMISSION_CAMERA = 112;
    public static final int REQUEST_CODE_CAMERA = 113;

    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.btn_set_header)
    Button btnSetHeader;
    @BindView(R.id.btn_set_header_crame)
    Button btnSetHeaderCrame;

    private String filepath;

    @Override
    protected BasePresenter setPresenter() {
        return new SetUserHeaderPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("头像设置");
        titleView.setRightText("保存");
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                mPresenter.uploadHeader(filepath);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_header;
    }

    @Override
    protected void initView() {
        UserBean user = App.getInstanse().getUser();
        if(user== null)return;
        UIUtils.displayHeader(this, App.getInstanse().getUser().getHeaderImage(), ivHeader, R.mipmap.logo);
    }

    @Override
    protected void refreshData() {

    }

    @OnClick({R.id.btn_set_header, R.id.btn_set_header_crame})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_set_header:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //todo request permission
                    ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_READ);
                } else {
                    toPhotos();
                }
                break;
            case R.id.btn_set_header_crame:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, REQUEST_PERMISSION_CAMERA);
                } else {
                    toCamera();
                }
                break;
        }
    }

    private void toPhotos() {
        Matisse.from(SetHeaderActivity.this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG)) // 选择 mime 的类型
                .countable(true)
                .maxSelectable(1) // 图片选择的最多数量
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
    }

    List<Uri> mSelected;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            if (mSelected != null && mSelected.size() > 0) {
                Uri uri = mSelected.get(0);
                filepath = FileUtils.uriToPath(SetHeaderActivity.this, uri);
            }
            CropActivity.show(this, filepath);
        } else if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            CropActivity.show(this, filepath);
        }

        //裁剪后
        if (resultCode == Constants.REQUEST_CODE_3 && requestCode == CropActivity.REQUEST_CODE_CROP) {
            filepath = data.getStringExtra("crop_path");
            Glide.with(this).load(filepath).placeholder(R.mipmap.logo).into(ivHeader);
//            Bitmap bitmap = BitmapFactory.decodeFile(filepath);
//            ivHeader.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_READ:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    toPhotos();
                } else {
                    showToast("请开启权限！");
                }
                break;
            case REQUEST_PERMISSION_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    toCamera();
                } else {
                    showToast("请开启相机权限");
                }
                break;
        }
    }

    private void toCamera() {
        if (SDCardUtils.isSDCardEnable()) {

            filepath = SDCardUtils.cacheDir + System.currentTimeMillis() + "camera.jpg";
            File file = new File(filepath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            //兼容7.0调用相机
            Uri uri = null;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                uri = Uri.fromFile(file);
            } else {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            }
            Intent intent = new Intent();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        } else {
            showToast("请检查SD卡是否正常！");
        }
    }

    @Override
    public void onUploadOk() {
        setResult(RESULT_OK);
        finish();
    }
}
