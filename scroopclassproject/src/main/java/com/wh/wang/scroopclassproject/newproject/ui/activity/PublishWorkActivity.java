package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sendtion.xrichtext.RichTextEditor;
import com.sendtion.xrichtext.SDCardUtil;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.UpLoadAvatarPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.WorkPresenter;
import com.wh.wang.scroopclassproject.newproject.utils.ImageUtils;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;
import com.wh.wang.scroopclassproject.newproject.view.PopPhoto;
import com.wh.wang.scroopclassproject.utils.KeyBoardUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.ToastUtils;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.sendtion.xrichtext.SDCardUtil.getPictureDir;


public class PublishWorkActivity extends Activity implements View.OnClickListener {
    private ImageView mCamera;
    private RichTextEditor mEtNewContent;
    private TextView mTitle;
    private ImageView mTitlebarbackssImageViewback;
    private TextView mTitlebarbackssName;
    private TextView mTitlebarbackssAction;

    protected static final int CAMERA_REQUEST_CODE = 1;
    protected static final int GALLERY_REQUEST_CODE = 2;
    private Subscription subsInsert;
    private UpLoadAvatarPresenter mUpLoadAvatarPresenter = new UpLoadAvatarPresenter();
    private WorkPresenter mWorkPresenter = new WorkPresenter();
    private String mZuoye_id;
    private String mUserId;
//    private DialogUtils mDialogUtils;
    private RelativeLayout mActivityPublishWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_work);
        initView();
        initDatas();
        initListener();
    }

    private void initDatas() {
        String title = getIntent().getStringExtra("title");
        mZuoye_id = getIntent().getStringExtra("zuoye_id");
        mTitle.setText(title);
    }

    private void initListener() {
        mCamera.setOnClickListener(this);
        mTitlebarbackssAction.setOnClickListener(this);
        mTitlebarbackssImageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mCamera = (ImageView) findViewById(R.id.camera);
        mEtNewContent = (RichTextEditor) findViewById(R.id.et_new_content);
        mTitle = (TextView) findViewById(R.id.title);
        mActivityPublishWork = (RelativeLayout) findViewById(R.id.activity_publish_work);
        mTitlebarbackssImageViewback = (ImageView) findViewById(R.id.titlebarbackss_imageViewback);
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mTitlebarbackssAction = (TextView) findViewById(R.id.titlebarbackss_action);
        mTitlebarbackssAction.setText(R.string.publish);
        mTitlebarbackssName.setText(R.string.publish_work);
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera:
                KeyBoardUtils.hideKeyboard(this);
                showPhotoPop();
                break;
            case R.id.titlebarbackss_action:
//                if(mDialogUtils==null){
//                    mDialogUtils = new DialogUtils(this);
//                }
//                mDialogUtils.showLoading();
                LoadingUtils.getInstance().showNetLoading(this);
                String editData = getEditData();
                Log.e("DH_WORK_CONTENT",editData);
                mWorkPresenter.upWorkContent(mUserId, mZuoye_id, editData, new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {
                        LoadingUtils.getInstance().hideNetLoading();
                        Toast.makeText(MyApplication.mApplication, "发布成功", Toast.LENGTH_SHORT).show();
                        Log.e("DH_WORK_CONTENT","success");
                        finish();
                    }

                    @Override
                    public void onFault(String error) {
                        LoadingUtils.getInstance().hideNetLoading();
                        Log.e("DH_WORK_CONTENT",error);
                    }
                });
                break;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    if(mPhotoPath!=null){
                        File file = new File(mPhotoPath);
                        if(file.exists()){
//                            if(mDialogUtils==null){
//                                mDialogUtils = new DialogUtils(this);
//                            }
//                            mDialogUtils.showLoading();
                            LoadingUtils.getInstance().showNetLoading(this);
                            Uri uri = Uri.fromFile(file);
                            insertImagesSync(uri);
                        }
                    }
                    break;
                case GALLERY_REQUEST_CODE:
//                    if(mDialogUtils==null){
//                        mDialogUtils = new DialogUtils(this);
//                    }
//                    mDialogUtils.showLoading();
                    LoadingUtils.getInstance().showNetLoading(this);
                    insertImagesSync(data.getData());
                    break;
            }
        }
    }

    private void insertImagesSync(final Uri data) {
        subsInsert = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    mEtNewContent.measure(0, 0);
                    int width = ScreenUtils.getScreenWidth(PublishWorkActivity.this);
                    int height = ScreenUtils.getScreenHeight(PublishWorkActivity.this);
                    String imagePath = getRealFilePath(data);
                    Bitmap bitmap = ImageUtils.getSmallBitmap(imagePath,width ,height);//压缩图片
                    imagePath = SDCardUtil.saveToSdCard(bitmap);
//                    subscriber.onNext(imagePath);
//                    subscriber.onCompleted();
                    sendImage(bitmap,subscriber);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        mEtNewContent.addEditTextAtIndex(mEtNewContent.getLastIndex(), " ");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(String imagePath) {
                        mEtNewContent.insertImage(imagePath, mEtNewContent.getMeasuredWidth());
                    }
                });
    }

//    public class InsertImagesSync extends AsyncTask<Intent,String,String>{
//        @Override
//        protected String doInBackground(Intent... params) {
//            Intent intent = params[0];
//            if(intent!=null){
//                int width = ScreenUtils.getScreenWidth(PublishWorkActivity.this);
//                int height = ScreenUtils.getScreenHeight(PublishWorkActivity.this);
//                ArrayList<String> photos = intent.getStringArrayListExtra("");
//                //可以同时插入多张图片
//                for (String imagePath : photos) {
//                    //Log.i("NewActivity", "###path=" + imagePath);
//                    Bitmap bitmap = ImageUtils.getSmallBitmap(imagePath, width, height);//压缩图片
//                    //bitmap = BitmapFactory.decodeFile(imagePath);
//                    imagePath = SDCardUtil.saveToSdCard(bitmap);
//                    //Log.i("NewActivity", "###imagePath="+imagePath);
//                    subscriber.onNext(imagePath);
//                }
//            }
//            return null;
//        }
//
//
//    }

    public String getRealFilePath(final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    /**
     * 上传图片
     */
    private void sendImage(Bitmap bm, final Subscriber<? super String> subscriber) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] bytes = stream.toByteArray();
        String upfile = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        mUpLoadAvatarPresenter.upWorkImg(upfile, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                LoadingUtils.getInstance().hideNetLoading();
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
//                        String url = "http://www.shaoziketang.com/" + avaterUrl;
//                        imagePath = SDCardUtil.saveToSdCard(bitmap);
                        subscriber.onNext(avaterUrl);
                        subscriber.onCompleted();
                        Log.e("DH_UP_CONTENT_IMG", avaterUrl);
                    } else {
                        ToastUtils.showToast(PublishWorkActivity.this, avaterUrl);
                    }
                } catch (JSONException e) {
                    LoadingUtils.getInstance().hideNetLoading();
                    Log.e("DH_UP_CONTENT_IMG", "http://www.shaoziketang.com/-");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFault(String error) {
                LoadingUtils.getInstance().hideNetLoading();
                Toast.makeText(MyApplication.mApplication, "访问异常", Toast.LENGTH_SHORT).show();
                Log.e("DH_UP_CONTENT_IMG", error);
            }
        });
    }

    /**
     * 负责处理编辑数据提交等
     */
    private String getEditData() {
        List<RichTextEditor.EditData> editList = mEtNewContent.buildEditData();
        StringBuffer content = new StringBuffer();
        content.append("<html>");
        content.append("<body>");
        for (RichTextEditor.EditData itemData : editList) {
            if (itemData.inputStr != null) {
                content.append("<p>");
                content.append(itemData.inputStr);
                content.append("</p>");
            } else if (itemData.imagePath != null) {
                content.append("<img src=\"").append(itemData.imagePath).append("\"/>");
                //Log.d("RichEditor", "commit imgePath=" + itemData.imagePath);
                //imageList.add(itemData.imagePath);
            }
        }
        content.append("</html>");
        content.append("</body>");
        return content.toString();
    }

    private PopPhoto mPopPhoto;

    public void showPhotoPop() {
        mPopPhoto = new PopPhoto(this);
        mPopPhoto.showAtLocation(mActivityPublishWork, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.4f;
        getWindow().setAttributes(params);
        mPopPhoto.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
        mPopPhoto.setOnSelectPhotoClickListener(new PopPhoto.OnSelectPhotoClickListener() {
            @Override
            public void onSelectPhotoClick(int way) {
                if (way != -1) {
                    if (way == 0) {
                        if (ContextCompat.checkSelfPermission(PublishWorkActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(PublishWorkActivity.this,
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

    private String mPhotoPath;
    private void getPicFromCarme() {
        mPhotoPath = getPictureDir() + System.currentTimeMillis();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        Uri photoUri;
        if (Build.VERSION.SDK_INT >= 24) {
            File file = new File(mPhotoPath);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            photoUri = FileProvider.getUriForFile(this, "com.wh.wang.scroopclassproject.fileprovider", file);
        } else {
            photoUri = Uri.fromFile(new File(mPhotoPath));
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 更改系统默认存储路径
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }
}
