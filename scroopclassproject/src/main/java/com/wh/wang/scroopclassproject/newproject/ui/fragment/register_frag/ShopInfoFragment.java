package com.wh.wang.scroopclassproject.newproject.ui.fragment.register_frag;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.newproject.callback.OnRegisterNextClickListener;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.RegisterEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.RegisterNXPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.UpLoadAvatarPresenter;
import com.wh.wang.scroopclassproject.newproject.view.NumLoop;
import com.wh.wang.scroopclassproject.newproject.view.PopPhoto;
import com.wh.wang.scroopclassproject.utils.KeyBoardUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopInfoFragment extends Fragment implements View.OnClickListener {
    private TextView mNext;
    private OnRegisterNextClickListener mOnRegisterNextClickListener;
    private RegisterNXPresenter mRegisterNXPresenter = new RegisterNXPresenter();
    //头像上传
    private UpLoadAvatarPresenter mUpLoadAvatarPresenter = new UpLoadAvatarPresenter();
    private TextView mShopNum;
    private TextView mStaffSize;
    private TextView mLicense;
    private RelativeLayout mFragShop;
    private ImageView mThumbnail;
    private RelativeLayout mUpLicense;
    private EditText mCompanyName;


    private List<String> mShopNumList;
    private List<String> mStaffSizeList;

    private String mLicenseUrl = "";
    private String mUser_id;

    public ShopInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnRegisterNextClickListener) {
            mOnRegisterNextClickListener = (OnRegisterNextClickListener) activity;
        } else {
            Log.e("DH_NEXT_LISTENER", "未绑定监听");
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_info, container, false);
        initView(view);
        initListener();
        initSize();
        return view;
    }

    private int mPhoneW = 800;
    private int mPhoneH = 480;

    private void initSize() {
        DisplayMetrics d = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(d);
        mPhoneW = d.widthPixels;
        mPhoneH = d.heightPixels;
    }

    private void initView(View view) {
        mNext = (TextView) view.findViewById(R.id.next);
        mShopNum = (TextView) view.findViewById(R.id.shop_num);
        mStaffSize = (TextView) view.findViewById(R.id.staff_size);
        mLicense = (TextView) view.findViewById(R.id.license);
        mFragShop = (RelativeLayout) view.findViewById(R.id.frag_shop);
        mThumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        mUpLicense = (RelativeLayout) view.findViewById(R.id.up_license);
        mCompanyName = (EditText) view.findViewById(R.id.company_name);
    }

    private void initListener() {
        mNext.setOnClickListener(this);
        mShopNum.setOnClickListener(this);
        mStaffSize.setOnClickListener(this);
        mUpLicense.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(mContext, "user_id", "");
    }

    //TODO
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                if(StringUtils.isEmpty(mUser_id)){
                    Intent intent = new Intent(getContext(), LoginNewActivity.class);
                    startActivity(intent);
                    return;
                }
                if (!checkInfo()) {
                    Toast.makeText(getContext(), "请将信息填写完整", Toast.LENGTH_SHORT).show();
                    return;
                }
                mRegisterNXPresenter.registerNx(mUser_id, getParamMap(), new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {
                        RegisterEntity entity = (RegisterEntity) value[0];
                        if(entity.getCode()==200){
                            if (mOnRegisterNextClickListener != null) {
                                mOnRegisterNextClickListener.OnNextClick(1);
                            }else{
                                Toast.makeText(getContext(), ""+entity.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getContext(), ""+entity.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFault(String error) {
                        Toast.makeText(getContext(), "访问异常", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.shop_num:
                if (mShopNumList == null) {
                    mShopNumList = new ArrayList<>();
                    mShopNumList.add("正在筹备中");
                    mShopNumList.add("1-3家店");
                    mShopNumList.add("3-10家店");
                    mShopNumList.add("10-30家店");
                    mShopNumList.add("30家以上");
                }
                KeyBoardUtils.hideKeyboard(getActivity());
                showPayPop(mShopNumList, 0);
                break;
            case R.id.staff_size:
                if (mStaffSizeList == null) {
                    mStaffSizeList = new ArrayList<>();
                    mStaffSizeList.add("5-20人");
                    mStaffSizeList.add("20-50人");
                    mStaffSizeList.add("50-150人");
                    mStaffSizeList.add("150-500人");
                    mStaffSizeList.add("500人以上");
                }
                KeyBoardUtils.hideKeyboard(getActivity());
                showPayPop(mStaffSizeList, 1);
                break;
            case R.id.up_license:
                KeyBoardUtils.hideKeyboard(getActivity());
                showPhotoPop();
                break;
        }
    }

    private Map<String,String> getParamMap() {
        Map<String,String> map = new HashMap<>();
        map.put("company_name",mCompanyName.getText().toString());
        map.put("company_md",mShopNum.getText().toString());
        map.put("company_per",mStaffSize.getText().toString());
        map.put("company_img",mLicenseUrl);
        return map;
    }

    private boolean checkInfo() {
        if(mCompanyName==null||mCompanyName.getText()==null||mCompanyName.getText().toString()==null||"".equals(mCompanyName.getText().toString().trim())){
            return false;
        }
        if(mShopNum==null||mShopNum.getText()==null||mShopNum.getText().toString()==null||"".equals(mShopNum.getText().toString().trim())){
            return false;
        }
        if(mStaffSize==null||mStaffSize.getText()==null||mStaffSize.getText().toString()==null||"".equals(mStaffSize.getText().toString().trim())){
            return false;
        }
        if(mLicenseUrl==null||"".equals(mLicenseUrl)){
            return false;
        }
        return true;
    }

    private NumLoop mNumLoop;

    public void showPayPop(List<String> list, final int flag) {
        mNumLoop = new NumLoop(getActivity(), list);
        mNumLoop.showAtLocation(mFragShop, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        final WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.4f;
        getActivity().getWindow().setAttributes(params);
        mNumLoop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1f;
                getActivity().getWindow().setAttributes(params);
            }
        });
        mNumLoop.setOnSelectItemClickListener(new NumLoop.OnSelectItemClickListener() {
            @Override
            public void onSelectItemClick(int pos, String item) {
                if (flag == 0) {
                    mShopNum.setText(item);
                } else {
                    mStaffSize.setText(item);
                }
            }
        });
    }

    private PopPhoto mPopPhoto;

    public void showPhotoPop() {
        mPopPhoto = new PopPhoto(getActivity());
        mPopPhoto.showAtLocation(mFragShop, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        final WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.4f;
        getActivity().getWindow().setAttributes(params);
        mPopPhoto.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1f;
                getActivity().getWindow().setAttributes(params);
            }
        });
        mPopPhoto.setOnSelectPhotoClickListener(new PopPhoto.OnSelectPhotoClickListener() {
            @Override
            public void onSelectPhotoClick(int way) {
                if (way != -1) {
                    if (way == 0) {
                        if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.CAMERA},
                                    1);
                        }else{
                            getPicFromCarme();
                        }
                    } else {
                        getPicFromPhoto();
                    }
                }
            }
        });
    }


    /**
     * 相机拍照相关
     */
    protected static final int CAMERA_REQUEST_CODE = 1;
    protected static final int GALLERY_REQUEST_CODE = 2;
    protected static final int CROP_REQUEST_CODE = 3;
    private String CORP_LICENSE = Environment.getExternalStorageDirectory() + "/crop_license.jpg";
    private String LICENSE = Environment.getExternalStorageDirectory() + "/license.jpg";
    private Uri tempUri;

    /**
     * 从相机里获取图片
     */
    private void getPicFromCarme() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= 23) {
            // 需要申请动态权限
            int check = ContextCompat.checkSelfPermission(getActivity(), permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//add by chx 2016-08-04:MIUI系统照相不能返回的问题

            File file = new File(LICENSE);
            if (file.exists()) {
                try {
                    file.delete();
                    file = new File(LICENSE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                file.createNewFile();
                file.setReadable(true);
                file.setWritable(true);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            //判断是否是AndroidN以及更高的版本
            if (Build.VERSION.SDK_INT >= 24) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                tempUri = FileProvider.getUriForFile(getContext(), "com.wh.wang.scroopclassproject.fileprovider", file);
            } else {
                tempUri = Uri.fromFile(new File(LICENSE));
            }
            // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
            intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }
    }

    /**
     * 从相册获取图片
     */
    private void getPicFromPhoto() {
        Intent picture = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, GALLERY_REQUEST_CODE);
    }

    public void crop(Uri uri) {
        // 裁剪图片意图
        File file = new File(CORP_LICENSE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    showPicture(tempUri);
                    break;
                case GALLERY_REQUEST_CODE:
                    Uri uri_pic = data.getData();
                    showPicture(uri_pic);
                    break;
                case CROP_REQUEST_CODE:
                    break;
            }
        }
    }

    private void showPicture(Uri tempUri) {
        try {
            Bitmap photoBmp = getBitmapFormUri(getActivity(), tempUri);
            Bitmap newPhotoBmp = rotateBitmapByDegree(photoBmp, 90);
            if (newPhotoBmp != null) {
                mThumbnail.setImageBitmap(newPhotoBmp);
                mLicense.setVisibility(View.GONE);
                sendImage(newPhotoBmp);
            } else {
                Toast.makeText(getContext(), "加载图片信息失败", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过uri获取图片并进行压缩
     *
     * @param uri
     */
    public Bitmap getBitmapFormUri(Activity ac, Uri uri) throws FileNotFoundException, IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = mPhoneH/2;//这里设置高度为800f
        float ww = mPhoneW/2;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    /**
     * 上传图片
     */
    private void sendImage(Bitmap bm) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 60, stream);
        byte[] bytes = stream.toByteArray();
        String upfile = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        mUpLoadAvatarPresenter.uploadAvatar(upfile, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                String json = value[0].toString();
                if (json != null && json.length() > 2) {
                    json = json.substring(1, json.length() - 1);
                }
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(json);
                    int avaterCode = jsonObject.optInt("code");
                    String avaterUrl = jsonObject.optString("url");
                    if (avaterCode == 200) {
                        mLicenseUrl = "http://www.shaoziketang.com/" + avaterUrl;
                        Log.e("DH_UP_AVATAR", "http://www.shaoziketang.com/" + avaterUrl);
                    } else {
                        ToastUtils.showToast(getActivity(), avaterUrl);
                    }
                } catch (JSONException e) {
                    Log.e("DH_UP_AVATAR", "http://www.shaoziketang.com/-");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFault(String error) {
                Toast.makeText(getContext(), "访问异常", Toast.LENGTH_SHORT).show();
                Log.e("DH_UP_AVATAR", error);
            }
        });
    }
}
