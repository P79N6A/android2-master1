package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.UpdataAvaterBean;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StyleUtil;
import com.wh.wang.scroopclassproject.utils.ToastUtils;
import com.wh.wang.scroopclassproject.views.CenterDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * 修改用户信息
 */
public class ChangeInfoActivity extends Activity implements CenterDialog.OnCenterItemClickListener {

    private ImageView changeinfo_iv_logo;
    private TextView changeinfo_tv_name;
    private EditText et_changename;
    private EditText et_change_palce;
    private EditText et_change_nice;
    private EditText et_change_employ;
    private EditText et_change_phone;
    private Button change_finish_btn;
    private String user_id;
    private String name;
    private String place;
    private String nice;
    private String employ;
    private String phone;
    private CenterDialog centerDialog;
    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
    private static int CROP_REQUEST_CODE = 3;
    private String upfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle(this, R.layout.change_info, "设置个人资料");
        initView();
        initData();
        initListener();
    }

    /**
     * 初始化数据
     */
    private void initView() {

        changeinfo_iv_logo = (ImageView) findViewById(R.id.changeinfo_iv_logo);
        changeinfo_tv_name = (TextView) findViewById(R.id.changeinfo_tv_name);

        String avatar = SharedPreferenceUtil.getStringFromSharedPreference(ChangeInfoActivity.this, "avatar", "");
        if (!avatar.isEmpty()) {
            ImageLoader.getInstance().displayImage(avatar, changeinfo_iv_logo, MyDisplayOptions.getOptions());
        }


        et_changename = (EditText) findViewById(R.id.et_changename);
        et_change_palce = (EditText) findViewById(R.id.et_change_palce);
        et_change_nice = (EditText) findViewById(R.id.et_change_nice);
        et_change_employ = (EditText) findViewById(R.id.et_change_employ);
        et_change_phone = (EditText) findViewById(R.id.et_change_phone);
        change_finish_btn = (Button) findViewById(R.id.change_finish_btn);

        centerDialog = new CenterDialog(ChangeInfoActivity.this, R.layout.dialog_avater_layout, new int[]{R.id.dialog_ll_pic, R.id.dialog_ll_carme});
        centerDialog.setOnCenterItemClickListener(ChangeInfoActivity.this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    /**
     * 初始化数据
     */
    private void initData() {

        String name = SharedPreferenceUtil.getStringFromSharedPreference(ChangeInfoActivity.this, "nickname", "");
        if (!name.isEmpty()) {
            changeinfo_tv_name.setText(name);
            et_changename.setHint(name);
        }

        String area = SharedPreferenceUtil.getStringFromSharedPreference(ChangeInfoActivity.this, "area", "");
        if (!area.isEmpty()) {
            et_change_palce.setHint(area);
        }

        String brand = SharedPreferenceUtil.getStringFromSharedPreference(ChangeInfoActivity.this, "brand", "");
        if (!brand.isEmpty()) {
            et_change_nice.setHint(brand);
        }


        String job = SharedPreferenceUtil.getStringFromSharedPreference(ChangeInfoActivity.this, "job", "");
        if (!job.isEmpty()) {
            et_change_employ.setHint(job);
        }

//        String mobile = SharedPreferenceUtil.getStringFromSharedPreference(ChangeInfoActivity.this, "mobile", "");
//        if (!mobile.isEmpty()) {
//            et_change_phone.setHint(mobile);
//        }
    }

    @Override
    public void OnCenterItemClick(CenterDialog dialog, View view) {
        switch (view.getId()) {
            case R.id.dialog_ll_pic:
                //TODO WH
                getPicFromPhoto();
                break;

            case R.id.dialog_ll_carme:
                getPicFromCarme();
                break;
            default:
                break;
        }
    }

    /**
     * 从相机里获取图片
     */
    private void getPicFromCarme() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    /**
     * 从相册获取图片
     */
    private void getPicFromPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (data == null) {
                return;
            } else {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap bm = extras.getParcelable("data");
                    Uri uri = saveBitmap(bm);
                    startImageZoom(uri);
                }
            }
        } else if (requestCode == GALLERY_REQUEST_CODE) {
            if (data == null) {
                return;
            }
            Uri uri;
            uri = data.getData();
            Uri fileUri = convertUri(uri);
            startImageZoom(fileUri);
        } else if (requestCode == CROP_REQUEST_CODE) {
            if (data == null) {
                return;
            }
            Bundle extras = data.getExtras();
            if (extras == null) {
                return;
            }
            Bitmap bm = extras.getParcelable("data");
            changeinfo_iv_logo.setImageBitmap(bm);
            sendImage(bm);
        }
    }

    private Uri convertUri(Uri uri) {
        InputStream is = null;
        try {
            is = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return saveBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Uri saveBitmap(Bitmap bm) {
        File tmpDir = new File(Environment.getExternalStorageDirectory()
                + "/szktpic");
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }
        File img = new File(tmpDir.getAbsolutePath() + "/" + "person.png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void startImageZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    private void sendImage(Bitmap bm) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 60, stream);
        byte[] bytes = stream.toByteArray();
        upfile = new String(Base64.encodeToString(bytes, Base64.DEFAULT));

        //网络传送图片
        //Log.e("whwh", "img===" + img);
        starUptateAvaterHttp(upfile);
    }

    /**
     * 上传头像地址
     */
    private void starUptateAvaterHttp(String upfile) {
        HttpUserManager.getInstance().updateAvater(upfile, new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                mAvaterHandler.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, UpdataAvaterBean.class);
    }

    private Handler mAvaterHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                int avaterCode = jsonObject.optInt("code");
                String avaterUrl = jsonObject.optString("url");
                if (avaterCode == 200) {
                    SharedPreferenceUtil.putStringValueByKey(ChangeInfoActivity.this, "avatar", avaterUrl);
                    Constants.avatarFlag = 1;
                } else {
                    ToastUtils.showToast(ChangeInfoActivity.this, avaterUrl);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };


    /**
     * 设置事件监听
     * et_changename  et_change_palce et_change_nice et_change_employ et_change_phone
     */
    private void initListener() {


        changeinfo_iv_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerDialog.show();
            }
        });


        change_finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_id = SharedPreferenceUtil.getStringFromSharedPreference(ChangeInfoActivity.this, "user_id", "");
                name = et_changename.getText().toString().trim();
                place = et_change_palce.getText().toString().trim();
                nice = et_change_nice.getText().toString().trim();
                employ = et_change_employ.getText().toString().trim();
                //phone = et_change_phone.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(ChangeInfoActivity.this, "昵称不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(place)) {
                    Toast.makeText(ChangeInfoActivity.this, "地区不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(nice)) {
                    Toast.makeText(ChangeInfoActivity.this, "品牌不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(employ)) {
                    Toast.makeText(ChangeInfoActivity.this, "职位不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //if (TextUtils.isEmpty(phone)) {
                //    Toast.makeText(ChangeInfoActivity.this, "手机号不能为空!", Toast.LENGTH_SHORT).show();
                //    return;
                //}
                startChangeInfoHttp(user_id, name, place, nice, employ, "");
            }
        });
    }

    //网络请求数据
    private void startChangeInfoHttp(String user_id, String name, String place, String nice, String employ, String phone) {
//        HttpUserManager.getInstance().post_changeInfos(user_id, name, place, nice, employ, phone,
//                new GetDataListener() {
//                    @Override
//                    public void onSuccess(Object data) {
//                        Message obtain = Message.obtain();
//                        obtain.obj = data;
//                        mHandler.sendMessage(obtain);
//                    }
//
//                    @Override
//                    public void onFailure(IOException e) {
//
//                    }
//                }, ChangeInfo.class);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                Long changeCode = jsonObject.optLong("code");
                String changeMsg = jsonObject.optString("msg");
                if (changeCode == 200) {
                    ToastUtils.showToast(ChangeInfoActivity.this, changeMsg);
                    SharedPreferenceUtil.putStringValueByKey(ChangeInfoActivity.this, "area", place);
                    SharedPreferenceUtil.putStringValueByKey(ChangeInfoActivity.this, "brand", nice);
                    SharedPreferenceUtil.putStringValueByKey(ChangeInfoActivity.this, "job", employ);
                    SharedPreferenceUtil.putStringValueByKey(ChangeInfoActivity.this, "mobile", phone);
                    SharedPreferenceUtil.putStringValueByKey(ChangeInfoActivity.this, "nickname", name);
                    Intent i = new Intent();
                    i.putExtra("name", name);
                    setResult(11, i);
                    finish();

                } else {
                    ToastUtils.showToast(ChangeInfoActivity.this, changeMsg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

}
