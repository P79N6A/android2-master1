package com.wh.wang.scroopclassproject.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.adapter.AbstractWheelTextAdapter;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.bean.ChangeInfo;
import com.wh.wang.scroopclassproject.bean.UpdataAvaterBean;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ProvinceEntity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.JsonFileReader;
import com.wh.wang.scroopclassproject.utils.BToast;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.ImageUtils;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;
import com.wh.wang.scroopclassproject.utils.OnePickerDialog;
import com.wh.wang.scroopclassproject.utils.PermissionUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.utils.StyleUtil;
import com.wh.wang.scroopclassproject.utils.ToastUtils;
import com.wh.wang.scroopclassproject.views.CenterDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 修改用户信息
 */
public class ChangeInfoNewActivity extends Activity implements CenterDialog.OnCenterItemClickListener, View.OnClickListener {
    private String user_id;
    private CenterDialog centerDialog;
    protected static final int CAMERA_REQUEST_CODE = 1;
    protected static final int GALLERY_REQUEST_CODE = 2;
    protected static final int CROP_REQUEST_CODE = 3;
    private String upfile = "";
    private ImageView changeinfonew_iv_logo;
    private RadioGroup rgSex;
    private TextView changeinfonew_et_name;
    private RadioButton rbMan;
    private RadioButton rbWoman;
    private RelativeLayout changeinfonew_llayout_three;
    private TextView changeinfonew_et_place;
    private EditText changeinfonew_et_nice;
    private EditText changeinfonew_et_employ;
    private EditText changeinfonew_et_nums;
    private EditText changeinfonew_et_email;
    private Button changeinfonew_save_btn;
    private String name;
    private String sex;
    private String place;
    private String nice;
    private String employ;
    private String nums;
    private String emails;
    private RelativeLayout mine_rlayout_logo;
    private Uri imageUri;
    protected static Uri tempUri;
    private Uri uritempFile;
    private String CORP_AVATAR_NAME = Environment.getExternalStorageDirectory()+"/crop_persions.jpg";
    private String AVATAR_NAME = Environment.getExternalStorageDirectory()+"/persions.jpg";

    /**
     * 地址键盘
     */
    private RelativeLayout mAddressLoop;
    private LoopView mProvinceLoop;
    private LoopView mCityLoop;
    private LoopView mDistrictLoop;
    private ImageView mKeyCancel;
    private ImageView mKeyOk;
    private List<ProvinceEntity> mProvinceEntityList;
    private List<ProvinceEntity.CityBean> mCityBeanEntityList;
    private List<String> mProvinceList = new ArrayList<>();
    private List<String> mCityList = new ArrayList<>();
    private List<String> mDistrictList = new ArrayList<>();
    private int mPhoneHight;
    private TextView mTitleName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle2(this, R.layout.change_info_new, "个人资料修改");
        initSize();
        initView();
        initData();
        initAddress();
        initListener();
        PermissionUtils.per(this);
    }

    private void initSize() {
        DisplayMetrics d = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(d);
        mPhoneHight = d.heightPixels;
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
    private void initView() {
        mine_rlayout_logo = (RelativeLayout) findViewById(R.id.mine_rlayout_logo);
        changeinfonew_iv_logo = (ImageView) findViewById(R.id.changeinfonew_iv_logo);//头像修改
        mTitleName = (TextView) findViewById(R.id.title_name);
        mTitleName.setText("个人资料修改");
        String avatar = SharedPreferenceUtil.getStringFromSharedPreference(ChangeInfoNewActivity.this, "avatar", "");
        if (!avatar.isEmpty()) {
            ImageLoader.getInstance().displayImage(avatar, changeinfonew_iv_logo, MyDisplayOptions.getOptions());
        }

        changeinfonew_et_name = (EditText) findViewById(R.id.changeinfonew_et_name);//获取姓名
        rgSex = (RadioGroup) findViewById(R.id.changeinfonew_rg_sex); //RadioGroup
        rbMan = (RadioButton) findViewById(R.id.changeinfonew_rb_male); //RadioButton 男
        rbWoman = (RadioButton) findViewById(R.id.changeinfonew_rb_male); //RadioButton 女

        changeinfonew_llayout_three = (RelativeLayout) findViewById(R.id.changeinfonew_llayout_three); //地址选择器
        changeinfonew_et_place = (TextView) findViewById(R.id.changeinfonew_et_place);//确定的地址

        changeinfonew_et_nice = (EditText) findViewById(R.id.changeinfonew_et_nice); //品牌

        changeinfonew_et_employ = (EditText) findViewById(R.id.changeinfonew_et_employ);//职位

        changeinfonew_et_nums = (EditText) findViewById(R.id.changeinfonew_et_nums); //门店数量

        changeinfonew_et_email = (EditText) findViewById(R.id.changeinfonew_et_email); //邮箱

        changeinfonew_save_btn = (Button) findViewById(R.id.changeinfonew_save_btn);//保存

        centerDialog = new CenterDialog(ChangeInfoNewActivity.this, R.layout.dialog_avater_layout, new int[]{R.id.dialog_ll_pic, R.id.dialog_ll_carme});
        centerDialog.setOnCenterItemClickListener(ChangeInfoNewActivity.this);

        /**
         * 地址键盘
         */
        mAddressLoop = (RelativeLayout) findViewById(R.id.address_loop);
        mProvinceLoop = (LoopView) findViewById(R.id.province_loop);
        mCityLoop = (LoopView) findViewById(R.id.city_loop);
        mDistrictLoop = (LoopView) findViewById(R.id.district_loop);
        mKeyCancel = (ImageView) findViewById(R.id.key_cancel);
        mKeyOk = (ImageView) findViewById(R.id.key_ok);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        String nickname = SharedPreferenceUtil.getStringFromSharedPreference(ChangeInfoNewActivity.this, "nickname", "");
        sex = SharedPreferenceUtil.getStringFromSharedPreference(ChangeInfoNewActivity.this, "sex", "");
        String area = SharedPreferenceUtil.getStringFromSharedPreference(ChangeInfoNewActivity.this, "area", "");
        String brand = SharedPreferenceUtil.getStringFromSharedPreference(ChangeInfoNewActivity.this, "brand", "");
        String job = SharedPreferenceUtil.getStringFromSharedPreference(ChangeInfoNewActivity.this, "job", "");
        String nums = SharedPreferenceUtil.getStringFromSharedPreference(ChangeInfoNewActivity.this, "nums", "");
        String emails = SharedPreferenceUtil.getStringFromSharedPreference(ChangeInfoNewActivity.this, "emails", "");
        changeinfonew_et_name.setText(nickname);
        if("男".equals(sex)){
            rbMan.setChecked(true);
        }else if("女".equals(sex)){
            rbWoman.setChecked(true);
        }
        changeinfonew_et_place.setText(area);
        changeinfonew_et_employ.setText(job);
        changeinfonew_et_nums.setText(nums);
        changeinfonew_et_email.setText(emails);
        changeinfonew_et_nice.setText(brand);
    }

    @Override
    public void OnCenterItemClick(CenterDialog dialog, View view) {
        switch (view.getId()) {
            case R.id.dialog_ll_pic:

                getPicFromPhoto();
                break;

            case R.id.dialog_ll_carme:
                if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) this,
                            new String[]{Manifest.permission.CAMERA},
                            1);
                }else{
                    getPicFromCarme();
                }

//                gallery();
                break;
            default:
                break;
        }
    }

    /**
     * 从相机里获取图片
     */
    private void getPicFromCarme() {
        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(intent, CAMERA_REQUEST_CODE);

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= 23) {
            // 需要申请动态权限
            int check = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝


            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//add by chx 2016-08-04:MIUI系统照相不能返回的问题

            File file = new File(AVATAR_NAME);
            if (file.exists()) {
                try {
                    file.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    file.createNewFile();
                    file.setReadable(true);
                    file.setWritable(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }

            //判断是否是AndroidN以及更高的版本
            if (Build.VERSION.SDK_INT >= 24) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //uritempFile = Uri.parse();
                tempUri = FileProvider.getUriForFile(ChangeInfoNewActivity.this, "com.wh.wang.scroopclassproject.fileprovider", file);
            } else {
                tempUri = Uri.fromFile(new File(AVATAR_NAME));
            }
            // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
            intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }
    }
    private File tempFile;
    public void gallery() {
        int currentapiVersion = Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            tempFile = new File(Environment.getExternalStorageDirectory(), AVATAR_NAME);
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                Uri uri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            } else {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                Intent intent1 = new Intent("com.android.camera.action.CROP");
                intent1.setDataAndType(getImageContentUri(this, tempFile), "image/*");//主要问题就在这个File Uri上面  ————代码语句A
                intent1.putExtra("crop", "true");
                intent1.putExtra("aspectX", 1);
                intent1.putExtra("aspectY", 1);
                intent1.putExtra("outputX", 180);
                intent1.putExtra("outputY", 180);
                intent1.putExtra("scale", true);
                intent1.putExtra("return-data", false);
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, uri);//定义输出的File Uri，之后根据这个Uri去拿裁剪好的图片信息  ————代码B
                intent1.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                intent1.putExtra("noFaceDetection", true);
                startActivityForResult(intent, CROP_REQUEST_CODE);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
    }

    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                , new String[]{MediaStore.Images.Media._ID}
                , MediaStore.Images.Media.DATA + "=? "
                , new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    /**
     * 从相册获取图片
     */
    private void getPicFromPhoto() {
        //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType("image/*");
        //startActivityForResult(intent, GALLERY_REQUEST_CODE);

        Intent picture = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, GALLERY_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("whwh", "requestCode==" + requestCode);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    crop(tempUri); // 开始对图片进行裁剪处理
                    break;
                case GALLERY_REQUEST_CODE:
                    Uri uri_pic = data.getData();
                    crop(uri_pic); // 开始对图片进行裁剪处理
                    break;
                case CROP_REQUEST_CODE:
//                    try {
//                        Bitmap fileBit = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                        Log.e("CROP_BITMAP",(data.getStringExtra("data")==null)+"  ---");
//                        if(fileBit!=null){
//                            fileBit = ImageUtils.toRoundBitmap(fileBit);
//                            changeinfonew_iv_logo.setImageBitmap(fileBit);
//                            sendImage(fileBit);
//                        }else{
//                            Toast.makeText(ChangeInfoNewActivity.this, "加载图片信息失败", Toast.LENGTH_SHORT).show();
//                        }
                        Bitmap fileBit = null;
                        File file = new File(CORP_AVATAR_NAME);
                        if (file.exists()) {
                            fileBit = BitmapFactory.decodeFile(CORP_AVATAR_NAME);
                            Log.e("CROP_BITMAP",(fileBit==null)+" "+(uritempFile==null));
//                        Log.e("CROP_BITMAP",(ContextCompat.checkSCROP_BITMAPelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
//                                +"  "+(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED));
                            if(fileBit!=null){
                                fileBit = ImageUtils.toRoundBitmap(fileBit);
                                changeinfonew_iv_logo.setImageBitmap(fileBit);
                                sendImage(fileBit);
                            }else{
                                Toast.makeText(ChangeInfoNewActivity.this, "加载图片信息失败", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(ChangeInfoNewActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
                        }
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                    break;
            }
        }
    }

    private void startPhotoZoom(Uri uri) {
        File file = new File(CORP_AVATAR_NAME);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        }
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);

        /**
         * 此方法返回的图片只能是小图片（sumsang测试为高宽160px的图片）
         * 故将图片保存在Uri中，调用时将Uri转换为Bitmap，此方法还可解决miui系统不能return data的问题
         */

//        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "persions.jpg");
//        Log.e("CRAMEAR_CROP",uri==null?"null":uri.toString());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);
        intent.putExtra("scale", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }
    public void crop(Uri uri) {
        // 裁剪图片意图
        File file = new File(CORP_AVATAR_NAME);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);

        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 图片格式
        intent.putExtra("scale", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    private void sendImage(Bitmap bm) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 60, stream);
        byte[] bytes = stream.toByteArray();
        upfile = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        //网络传送图片
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
    private String mNewAvatar = "";
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
                    mNewAvatar = avaterUrl;
//                    SharedPreferenceUtil.putStringValueByKey(ChangeInfoNewActivity.this, "avatar",avaterUrl);
                    Constants.avatarFlag = 1;
                } else {
                    ToastUtils.showToast(ChangeInfoNewActivity.this, avaterUrl);
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
        mAddressLoop.setOnClickListener(this);
        mKeyCancel.setOnClickListener(this);
        mKeyOk.setOnClickListener(this);
        //TODO 头像设置
        mine_rlayout_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerDialog.show();
            }
        });

        //性别设置
        //RadioGroup 监听事件
        rgSex.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //获取变更后的选中项的ID
                int radioButtonId = group.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) findViewById(radioButtonId);
                //更新文本内容，以符合选中项
                //tv.setText("您的性别是：" + );
                sex = rb.getText().toString();
            }
        });

        rgSex.check(R.id.changeinfonew_rb_male);

        //地址设置
        changeinfonew_llayout_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDateDialog();
                startKeyStoreAni();
            }
        });


        changeinfonew_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_id = SharedPreferenceUtil.getStringFromSharedPreference(ChangeInfoNewActivity.this, "user_id", "");
                name = changeinfonew_et_name.getText().toString().trim();
                nice = changeinfonew_et_nice.getText().toString().trim();
                employ = changeinfonew_et_employ.getText().toString().trim();
                nums = changeinfonew_et_nums.getText().toString().trim();
                emails = changeinfonew_et_email.getText().toString().trim();
                place = changeinfonew_et_place.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(ChangeInfoNewActivity.this, "名称不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(sex)) {
                    Toast.makeText(ChangeInfoNewActivity.this, "性别选择不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(place)) {
                    Toast.makeText(ChangeInfoNewActivity.this, "地区不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(nice)) {
                    Toast.makeText(ChangeInfoNewActivity.this, "品牌不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(employ)) {
                    Toast.makeText(ChangeInfoNewActivity.this, "职位不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(nums)) {
                    Toast.makeText(ChangeInfoNewActivity.this, "门店数量不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(emails)) {
                    Toast.makeText(ChangeInfoNewActivity.this, "电子邮箱不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(StringUtils.isEmpty(mNewAvatar)){
                    mNewAvatar = SharedPreferenceUtil.getStringFromSharedPreference(ChangeInfoNewActivity.this, "avatar", "");
                }
                Log.e("DH_AVATAR",mNewAvatar);
                startChangeInfoHttp(user_id, name, sex, place, nice, employ, nums, emails, mNewAvatar);
            }
        });
    }

    private void showDateDialog() {

        final ArrayList<String> years = new ArrayList<String>();
        years.add("北京市");
        years.add("天津市");
        years.add("上海市");
        years.add("重庆市");//4

        years.add("河北省");
        years.add("山西省");
        years.add("辽宁省");
        years.add("吉林省");
        years.add("黑龙江省");
        years.add("江苏省");//6

        years.add("浙江省");
        years.add("安徽省");
        years.add("福建省");
        years.add("江西省");
        years.add("山东省");//5

        years.add("河南省");
        years.add("湖北省");
        years.add("湖南省");
        years.add("广东省");
        years.add("海南省");//5

        years.add("四川省");
        years.add("贵州省");
        years.add("云南省");
        years.add("陕西省");
        years.add("甘肃省");
        years.add("青海省");//6

        years.add("内蒙古");
        years.add("广西");
        years.add("西藏");
        years.add("宁夏");
        years.add("新疆");//5

        years.add("台湾");//5
        years.add("香港");
        years.add("澳门");//2


        AbstractWheelTextAdapter adapter = new AbstractWheelTextAdapter(ChangeInfoNewActivity.this,
                R.layout.wheel_text) {

            @Override
            public int getItemsCount() {

                return years.size();
            }

            @Override
            protected CharSequence getItemText(int index) {

                return years.get(index);
            }
        };

        OnePickerDialog picker = new OnePickerDialog(ChangeInfoNewActivity.this, adapter, new OnePickerDialog.onSelectListener() {

            @Override
            public void onSelect(AbstractWheelTextAdapter adapter, int position) {
                changeinfonew_et_place.setText(years.get(position));
                place = years.get(position);
            }
        });
        picker.show();
    }

    //网络请求数据  user_id, name,sex,place, nice, employ, nums,emails
    private void startChangeInfoHttp(String user_id, String name, String sex, String place, String nice, String employ, String nums, String emails, String avatar) {
        HttpUserManager.getInstance().post_changeInfos(user_id, name, sex, place, nice, employ, nums, emails, avatar,
                new GetDataListener() {
                    @Override
                    public void onSuccess(Object data) {
                        Message obtain = Message.obtain();
                        obtain.obj = data;
                        mHandler.sendMessage(obtain);
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }, ChangeInfo.class);
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
                    BToast.showText(ChangeInfoNewActivity.this, changeMsg, true);
                    SharedPreferenceUtil.putStringValueByKey(ChangeInfoNewActivity.this, "nickname", name);
                    SharedPreferenceUtil.putStringValueByKey(ChangeInfoNewActivity.this, "sex", sex);
                    SharedPreferenceUtil.putStringValueByKey(ChangeInfoNewActivity.this, "area", place);
                    SharedPreferenceUtil.putStringValueByKey(ChangeInfoNewActivity.this, "brand", nice);
                    SharedPreferenceUtil.putStringValueByKey(ChangeInfoNewActivity.this, "job", employ);
                    SharedPreferenceUtil.putStringValueByKey(ChangeInfoNewActivity.this, "nums", nums);
                    SharedPreferenceUtil.putStringValueByKey(ChangeInfoNewActivity.this, "emails", emails);
                    SharedPreferenceUtil.putStringValueByKey(ChangeInfoNewActivity.this, "avatar", mNewAvatar);
                    finish();

                } else {
                    BToast.showText(ChangeInfoNewActivity.this, changeMsg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
//    public static Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight) {
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(filePath, options);
//
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        //避免出现内存溢出的情况，进行相应的属性设置。
//        options.inPreferredConfig = Bitmap.Config.RGB_565;
//        options.inDither = true;
//
//        return BitmapFactory.decodeFile(filePath, options);
//    }


    public void startKeyStoreAni(){
        ObjectAnimator animator;
        if(mAddressLoop.getVisibility()==View.GONE){
            mAddressLoop.setVisibility(View.VISIBLE);
            animator = ObjectAnimator.ofFloat(mAddressLoop, "translationY",mPhoneHight,0);
            animator.start();//开始动画
        }else{
            mAddressLoop.setVisibility(View.GONE);
            animator = ObjectAnimator.ofFloat(mAddressLoop, "translationY",mPhoneHight- DisplayUtil.dip2px(MyApplication.getApplication(),216),mPhoneHight);
            animator.start();//开始动画
        }
    }

    private void setAddressInfo() {
        String pro = mProvinceList.get(mProvinceLoop.getSelectedItem());
        String city = mCityList.get(mCityLoop.getSelectedItem());
        String dis = mDistrictList.get(mDistrictLoop.getSelectedItem());
        if("北京市".equals(pro)||"上海市".equals(pro)||"重庆市".equals(pro)||"天津市".equals(pro)||"香港".equals(pro)||"澳门".equals(pro)||"台湾省".equals(pro)){
//            mCoachVerifiedAddress.setText(city+" "+dis);
//            place = city+" "+dis;
            place = city;
            changeinfonew_et_place.setText(place);
        }else{
//            mCoachVerifiedAddress.setText(pro+" "+city+" "+dis);
            place = pro+" "+city;
//            place = pro+" "+city+" "+dis;
            changeinfonew_et_place.setText(place);
        }
    }

    private void initAddress() {
        mProvinceLoop.setNotLoop();
        mCityLoop.setNotLoop();
        mDistrictLoop.setNotLoop();

        mProvinceLoop.setTextSize(13);
        mCityLoop.setTextSize(13);
        mDistrictLoop.setTextSize(13);
        //  获取json数据
        String province_data_json = JsonFileReader.getJson(this, "province_data.json");
        //  解析json数据
        if(province_data_json!=null&&province_data_json.length()>0){
            Gson gson = new Gson();
            mProvinceEntityList = gson.fromJson(province_data_json,new TypeToken<List<ProvinceEntity>>() {}.getType());

            mProvinceLoop.setItems(getProvince());
            mProvinceLoop.setOuterTextColor(getResources().getColor(R.color.white_color_aplha));
            mProvinceLoop.setCenterTextColor(getResources().getColor(R.color.black));

            mCityLoop.setItems(getCity(0));
            mCityLoop.setOuterTextColor(getResources().getColor(R.color.white_color_aplha));
            mCityLoop.setCenterTextColor(getResources().getColor(R.color.black));

            mDistrictLoop.setItems(getDistrict(0));
            mDistrictLoop.setOuterTextColor(getResources().getColor(R.color.white_color_aplha));
            mDistrictLoop.setCenterTextColor(getResources().getColor(R.color.black));

            mProvinceLoop.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    mCityLoop.setItems(getCity(index));
                    mCityLoop.setCurrentPosition(0);
                    mDistrictLoop.setItems(getDistrict(0));
                    mDistrictLoop.setCurrentPosition(0);
                }
            });

            mCityLoop.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    mDistrictLoop.setItems(getDistrict(index));
                    mDistrictLoop.setCurrentPosition(0);
                }
            });

            mDistrictLoop.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {

                }
            });
        }

    }

    public List<String> getProvince(){
        for (int i = 0; i < mProvinceEntityList.size(); i++) {
            mProvinceList.add(mProvinceEntityList.get(i).getName());
        }
        return mProvinceList;
    }

    public List<String> getCity(int index){
        mCityList.clear();
        mCityBeanEntityList = mProvinceEntityList.get(index).getCity();
        for (int i = 0; i < mCityBeanEntityList.size(); i++) {
            mCityList.add(mCityBeanEntityList.get(i).getName());
        }
        return mCityList;
    }

    public List<String> getDistrict(int index){
        mDistrictList.clear();
        for (int i = 0; i < mCityBeanEntityList.get(index).getArea().size(); i++) {
            mDistrictList.add(mCityBeanEntityList.get(index).getArea().get(i));
        }
        return mDistrictList;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.key_cancel:
            case R.id.address_loop:
                startKeyStoreAni();
                break;
            case R.id.key_ok:
                setAddressInfo();
                startKeyStoreAni();
                break;
        }
    }
}
