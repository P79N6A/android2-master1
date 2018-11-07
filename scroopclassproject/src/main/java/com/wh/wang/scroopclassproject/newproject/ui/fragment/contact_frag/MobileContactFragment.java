package com.wh.wang.scroopclassproject.newproject.ui.fragment.contact_frag;


import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.eventmodel.ContactMap;
import com.wh.wang.scroopclassproject.newproject.eventmodel.EventContactEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CheckEventEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ContactEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.OperationContactEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CheckEventPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.ContactPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.AddContactAdapter;
import com.wh.wang.scroopclassproject.newproject.view.SwipeItemLayout;
import com.wh.wang.scroopclassproject.utils.BToast;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class MobileContactFragment extends Fragment implements View.OnClickListener, AddContactAdapter.OnEditClickListener, AddContactAdapter.OnSelectClickListener {
    private LinearLayout mAddContact;
    private RecyclerView mContactList;
    private TextView mApplyNum;
    private TextView mFinish;
    private CheckEventPresenter mCheckEventPresenter = new CheckEventPresenter();
    private String mUser_id;
    private String mEvent_id = "";
    private AddContactAdapter mAddContactAdapter;
//    private List<EventMoreEntity> mEventMoreEntityList = new ArrayList<>();
    private ContactPresenter mContactPresenter = new ContactPresenter();
    private String mLoginRandStr;
    private List<ContactEntity.InfoBean> mContactInfoList ;

    private ContactMap mContact_map;
    private Map<String,ContactEntity.InfoBean> map;
    private Set<String> mContactKey;
    private int mApplyType;

    public MobileContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mobile_contact, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        mEvent_id = getArguments().getString("id");
        mContact_map = (ContactMap) getArguments().getSerializable("contact_map");
        mApplyType = getArguments().getInt("apply_type",0);
        map = mContact_map.getInfoBeanMap();
        mContactKey = map.keySet();
        setApplyNumTv(mCurrentNum);

        Log.e("DH_ApplyType",mApplyType+"");
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mLoginRandStr = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "login_rand_str", "");
        mContactPresenter.getContact(mUser_id, mLoginRandStr,mEvent_id,mApplyType==0?"1":"", new OnResultListener() {

            @Override
            public void onSuccess(Object... value) {
                ContactEntity entity = (ContactEntity) value[0];
                if(entity.getStatus()==1){
                    mContactInfoList = entity.getInfo();
                    if(mContactInfoList==null){
                        mContactInfoList = new ArrayList<ContactEntity.InfoBean>();
                    }
                    if(mContactKey.size()>0&&mContactInfoList.size()>0){
                        for (int i = 0; i < mContactInfoList.size(); i++) {
                            if(mContactKey.contains(mContactInfoList.get(i).getPhone())){
                                mCurrentNum++;
                                mContactInfoList.get(i).setCheck(true);
                                mContactInfoList.get(i).setOperation(true);
//                                if(mContact_map.getInfoBeanMap().get(mContactInfoList.get(i).getPhone()).getPrice()==null){
//                                    mContactInfoList.get(i).setPrice("");
//                                }else{
//                                    mContactInfoList.get(i).setPrice(mContact_map.getInfoBeanMap().get(mContactInfoList.get(i).getPhone()).getPrice());
//                                }
                            }
                        }
                        setApplyNumTv(mCurrentNum);
                    }
                    mAddContactAdapter = new AddContactAdapter(mContactInfoList,getContext());
                    mContactList.setAdapter(mAddContactAdapter);
                    mAddContactAdapter.setOnEditClickListener(MobileContactFragment.this);
                    mAddContactAdapter.setOnSelectClickListener(MobileContactFragment.this);
                }else{
                    Toast.makeText(getContext(), ""+entity.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {
                if(mContactInfoList==null){
                    mContactInfoList = new ArrayList<ContactEntity.InfoBean>();
                }
//                Log.e()
            }
        });
    }

    private void initListener() {
        mAddContact.setOnClickListener(this);
        mFinish.setOnClickListener(this);
    }

    private void initView(View view) {
        mAddContact = (LinearLayout) view.findViewById(R.id.add_contact);
        mContactList = (RecyclerView)  view.findViewById(R.id.contact_list);
        mApplyNum = (TextView)  view.findViewById(R.id.apply_num);
        mFinish = (TextView)  view.findViewById(R.id.finish);
        mContactList.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getActivity()));
        mContactList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_contact:
                showSelectDig();
                break;
            case R.id.finish:
                List<ContactEntity.InfoBean> list = new ArrayList<>();
                for (int i = 0; i < mContactInfoList.size(); i++) {
                    if(mContactInfoList.get(i).isCheck()){
                        list.add(mContactInfoList.get(i));
                    }
                }
                getActivity().finish();
                EventBus.getDefault().post(new EventContactEntity(list));
                break;
        }
    }

    private Dialog mSelectDig;
    private void showSelectDig() {
        mSelectDig = new Dialog(getActivity(), R.style.ActionSheetDialogStyle);
        View inflate = LayoutInflater.from(MyApplication.mApplication).inflate(R.layout.dig_select_add_contact, null);
        inflate.findViewById(R.id.add_hm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectDig.dismiss();
                showAddDialog(new ContactEntity.InfoBean(),0,false);
            }
        });
        inflate.findViewById(R.id.add_address_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectDig.dismiss();
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_CONTACTS},
                            1);
                }else{
                    Uri uri = Uri.parse("content://contacts/people");
                    Intent intent = new Intent(Intent.ACTION_PICK, uri);
                    startActivityForResult(intent, 0);
                }

            }
        });
        inflate.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectDig!=null&&mSelectDig.isShowing()){
                    mSelectDig.dismiss();
                }
            }
        });

        //将布局设置给Dialog
        mSelectDig.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = mSelectDig.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = lp.MATCH_PARENT;
        lp.height = lp.WRAP_CONTENT;
        getActivity().getWindow().getDecorView().setPadding(0, 0, 0, 0);
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);
        mSelectDig.show();//显示对话框
    }


    /**
     * 手动添加/修改
     */
    private Dialog mAddDig;
    private void showAddDialog(final ContactEntity.InfoBean entity, final int isAdd, final boolean isSelf) {
        mAddDig = new Dialog(getActivity(), R.style.MyDialog);
        mAddDig.setContentView(R.layout.event_dialog_new);
        TextView mTitle = (TextView) mAddDig.findViewById(R.id.title);
        final EditText name = (EditText) mAddDig.findViewById(R.id.name);
        final EditText tel = (EditText) mAddDig.findViewById(R.id.tel);
        mAddDig.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddDig.dismiss();
            }
        });

        TextView addUser = (TextView) mAddDig.findViewById(R.id.add_user);

        if(isAdd==0){
            mTitle.setText("添加人员");
            addUser.setText("确认添加");
        } else{
            mTitle.setText("修改人员信息");
            addUser.setText("确认修改");

            String s_phone = "";
            String s_name = "";
            if(StringUtils.isNotEmpty(entity.getPhone())){
                s_phone = entity.getPhone().trim();
            }
            if(StringUtils.isNotEmpty(entity.getName())){
                s_name = entity.getName().trim();
            }
            if(isSelf){
                tel.setBackgroundResource(R.drawable.shape_forbid_edit);
                s_phone = s_phone+"(不可编辑)";
                tel.setTextColor(Color.parseColor("#9AA3B2"));
                tel.setEnabled(false);
            }else{
                tel.setBackgroundResource(R.drawable.event_edit_shape);
                tel.setTextColor(Color.parseColor("#282828"));
                tel.setEnabled(true);
            }
            name.setText(s_name);
            tel.setText(s_phone);
            tel.setSelection(s_phone.length());
            name.setSelection(s_name.length());
        }

        StringUtils.setEditTextInhibitInputSpace(name);
        StringUtils.setEditTextInhibitInputSpace(tel);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String dialogName = name.getText() != null ? name.getText().toString().trim() : "";
                final String dialogTel = tel.getText() != null ? tel.getText().toString().trim() : "";
                if (TextUtils.isEmpty(dialogName)) {
                    BToast.showText(getContext(), "输入姓名不能为空");
                    return;
                }

                if (TextUtils.isEmpty(dialogTel)) {
                    BToast.showText(getContext(), "输入手机号不能为空!");
                    return;
                }
                if (!StringUtils.isPhoneNum(dialogTel.replace("(不可编辑)",""))) {
                    BToast.showText(getContext(), "请输入正确的手机号!");
                    return;
                }
//                Log.e("DH_CONTACT_PARAM","tel: "+((dialogTel.replace("(不可编辑)","")).equals(entity.getPhone())));
                Operation(dialogName, dialogTel.replace("(不可编辑)",""),entity,isAdd,isSelf);
            }
        });
        mAddDig.show();
    }

    //添加、修改并检查
    private void Operation(final String dialogName, final String dialogTel, final ContactEntity.InfoBean entity, final int isAdd, final boolean isSelf) {
        String isVideo = "";
        if(mApplyType==0){
            isVideo = "1";
        }
        Log.e("DH_CONTACT","user_id:"+mUser_id+"  str:"+mLoginRandStr+"  isAdd"+((isAdd==0)?"":entity.getId())+"  name:"+dialogName+"  tel:"+dialogTel+"  event_id:"+mEvent_id+"  isv:"+isVideo);
        mContactPresenter.contactOperation(mUser_id, mLoginRandStr, (isAdd==0)?"0":entity.getId(), dialogName, dialogTel,mEvent_id,isVideo, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                OperationContactEntity ocEntity = (OperationContactEntity) value[0];
                if(ocEntity.getStatus()==1){
                    if(mAddDig!=null&&mAddDig.isShowing()){
                        mAddDig.dismiss();
                    }
                    entity.setName(ocEntity.getInfo().getName());
                    if(!isSelf){
                        entity.setPhone(ocEntity.getInfo().getPhone());
                    }
                    entity.setJoinAccess(ocEntity.getInfo().isJoinAccess());
                    entity.setPrice(ocEntity.getInfo().getPrice());
                    entity.setReason(ocEntity.getInfo().getReason());
                    if(isAdd==0) {
                        entity.setId(ocEntity.getInfo().getId());
                        if (mContactInfoList == null) {
                            mContactInfoList = new ArrayList<>();
                        }
                        mContactInfoList.add(entity);
                    }
                    if(mAddContactAdapter!=null) mAddContactAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(), ""+ocEntity.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                Log.e("DH_CONTACT",error);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                if(data==null){
                    return;
                }
                //处理返回的data,获取选择的联系人信息
                Uri uri=data.getData();
                String[] contacts=getPhoneContacts(uri);
                if(contacts[1]!=null&&!"".equals(contacts[1].trim().replace(" ",""))){
                    Operation(contacts[0], contacts[1].trim().replace(" ","").replace("+86",""),new ContactEntity.InfoBean(),0,false);
                }else{
                    Toast.makeText(getContext(), "该用户没有手机号", Toast.LENGTH_SHORT).show();
                }

                Log.e("DH_CONTACT","name:"+contacts[0]+"  phone:"+contacts[1]);
                break;
        }
    }

    private String[] getPhoneContacts(Uri uri){
        String[] contact=new String[2];
        //得到ContentResolver对象
        ContentResolver cr = getActivity().getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor=cr.query(uri,null,null,null,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            //取得联系人姓名
            int nameFieldColumnIndex=cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contact[0]=cursor.getString(nameFieldColumnIndex);
            //取得电话号码
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
            if(phone != null){
                phone.moveToFirst();
                contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            phone.close();
            cursor.close();
        }
        else
        {
            return null;
        }
        return contact;
    }

    @Override
    public void onEditClick(ContactEntity.InfoBean eventMoreEntity,int pos) {
        showAddDialog(eventMoreEntity,1,(pos==0));
    }

    private int mCurrentNum = 0;
    @Override
    public void onSelectClick(int pos, final ContactEntity.InfoBean infoBean, final ImageView imageView) {
        if(infoBean.isCheck()){
            imageView.setImageResource(R.drawable.bm_icon_unselect);
            infoBean.setCheck(false);
            mCurrentNum--;
        }else{
            imageView.setImageResource(R.drawable.bm_icon_select);
            infoBean.setCheck(true);
            mCurrentNum++;
        }
        setApplyNumTv(mCurrentNum);
    }

    public void setApplyNumTv(int mCurrentNum){
        String content = "报名人数："+mCurrentNum+"人";
        if(mApplyType==0){
            content = "购买人数："+mCurrentNum+"人";
        }
        int f_index = content.indexOf("：");
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#fd733d")), f_index+1,content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mApplyNum.setText(spannableString);
    }


    public void checkContact(final String id, final String name, final String phone, final ContactEntity.InfoBean entity, final int flag){
        /**
         * TODO 添加更多检查
         */
        mCheckEventPresenter.checkMoreEvent(phone, mEvent_id, "", mUser_id, new OnResultListener() {

            @Override
            public void onSuccess(Object... value) {
                CheckEventEntity eventEntity = (CheckEventEntity) value[0];
                entity.setName(name);
                entity.setPhone(phone);
                entity.setId(id);
                if(eventEntity.getStatus()==1){
                    entity.setJoinAccess(true);
                    entity.setPrice(eventEntity.getInfo());
                }else{
                    entity.setJoinAccess(false);
                }
                if(flag==0) {
                    if (mContactInfoList == null) {
                        mContactInfoList = new ArrayList<>();
                    }
                    mContactInfoList.add(entity);
                }
                if(mAddContactAdapter!=null) mAddContactAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFault(String error) {
                Toast.makeText(getContext(), "检测异常", Toast.LENGTH_SHORT).show();
                Log.e("DH_CHECK_MORE", error);
            }
        });
    }
}
