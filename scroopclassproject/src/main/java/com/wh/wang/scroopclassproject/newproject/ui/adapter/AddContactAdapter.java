package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ContactEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.OperationContactEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.ContactPresenter;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import java.util.List;

/**
 * Created by chdh on 2018/1/30.
 */

public class AddContactAdapter extends RecyclerView.Adapter<AddContactAdapter.AddContactViewHolder> {
    private String mUser_id;
    private String mLoginRandStr;
    private List<ContactEntity.InfoBean> mEventMoreEntityList;
    private LayoutInflater mInflater;
    private ContactPresenter mContactPresenter = new ContactPresenter();
    private Context mContext;
    public AddContactAdapter(List<ContactEntity.InfoBean> eventMoreEntityList,Context context) {
        mContext = context;
        mEventMoreEntityList = eventMoreEntityList;
        mInflater = LayoutInflater.from(MyApplication.mApplication);
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mLoginRandStr = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "login_rand_str", "");
    }

    @Override
    public AddContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AddContactViewHolder(mInflater.inflate(R.layout.item_add_contact,parent,false));
    }

    @Override
    public void onBindViewHolder(final AddContactViewHolder holder, final int position) {
        if(holder!=null&&mEventMoreEntityList!=null&&mEventMoreEntityList.get(position)!=null){
            final ContactEntity.InfoBean eventMoreEntity = mEventMoreEntityList.get(position);
            if(position==0||eventMoreEntity.isOperation()){
                holder.mDel.setVisibility(View.GONE);
                holder.mDel.setOnClickListener(null);
            }else{
                holder.mDel.setVisibility(View.VISIBLE);
                holder.mDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("是否确定删除该联系人")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mContactPresenter.contactOperation(mUser_id, mLoginRandStr, mEventMoreEntityList.get(position).getId(), "", "","","", new OnResultListener() {
                                            @Override
                                            public void onSuccess(Object... value) {
                                                OperationContactEntity entity = (OperationContactEntity) value[0];
                                                if(entity.getStatus()==1){
                                                    Toast.makeText(MyApplication.mApplication, "删除成功", Toast.LENGTH_SHORT).show();
                                                    if(mEventMoreEntityList.get(position).isCheck()){
                                                        if(mOnSelectClickListener!=null){
                                                            mOnSelectClickListener.onSelectClick(position,eventMoreEntity,holder.mSelect);
                                                        }
                                                    }

                                                    mEventMoreEntityList.remove(position);
                                                    AddContactAdapter.this.notifyDataSetChanged();
                                                }else{
                                                    Toast.makeText(MyApplication.mApplication, ""+entity.getError(), Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFault(String error) {
                                                Log.e("DH_CONTACT",error);
                                                Toast.makeText(MyApplication.mApplication, "删除发生错误", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                        Dialog dialog = builder.create();
                        dialog.setCancelable(false);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                    }
                });
            }
            if(eventMoreEntity.isCheck()){
                holder.mSelect.setImageResource(R.drawable.bm_icon_select);
            }else{
                holder.mSelect.setImageResource(R.drawable.bm_icon_unselect);
            }

            if(eventMoreEntity.isOperation()){
                holder.mEdit.setVisibility(View.INVISIBLE);
                holder.mEdit.setOnClickListener(null);
                holder.mItem.setOnClickListener(null);
            }else{
                holder.mEdit.setVisibility(View.VISIBLE);
                holder.mItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if(eventMoreEntity.isCheck()){
//                            holder.mSelect.setImageResource(R.drawable.bm_icon_unselect);
//                            eventMoreEntity.setCheck(false);
//                        }else{
//                            holder.mSelect.setImageResource(R.drawable.bm_icon_select);
//                            eventMoreEntity.setCheck(true);
//                        }
                        if(mOnSelectClickListener!=null){
                            mOnSelectClickListener.onSelectClick(position,eventMoreEntity,holder.mSelect);
                        }
                    }
                });
                holder.mEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mOnEditClickListener!=null){
                            mOnEditClickListener.onEditClick(eventMoreEntity,position);
                        }
                    }
                });
            }

            if(!eventMoreEntity.isJoinAccess()){
                holder.mName.setTextColor(Color.parseColor("#9AA3B2"));
                holder.mTel.setTextColor(Color.parseColor("#9AA3B2"));
                holder.mSelect.setVisibility(View.INVISIBLE);
                holder.mEdit.setVisibility(View.INVISIBLE);
                holder.mHint.setText(eventMoreEntity.getReason()+"");
            }else{
                holder.mSelect.setVisibility(View.VISIBLE);
                holder.mEdit.setVisibility(View.VISIBLE);
                holder.mName.setTextColor(Color.parseColor("#282828"));
                holder.mTel.setTextColor(Color.parseColor("#282828"));
                holder.mHint.setText("");
            }
            holder.mName.setText(eventMoreEntity.getName());
            holder.mTel.setText(eventMoreEntity.getPhone());

        }
    }

    @Override
    public int getItemCount() {
        return mEventMoreEntityList!=null?mEventMoreEntityList.size():0;
    }

    class AddContactViewHolder extends RecyclerView.ViewHolder{
        private ImageView mSelect;
        private TextView mName;
        private TextView mTel;
        private ImageView mEdit;
        private Button mDel;
        private RelativeLayout mItem;
        private TextView mHint;

        public AddContactViewHolder(View itemView) {
            super(itemView);
            mSelect = (ImageView) itemView.findViewById(R.id.select);
            mName = (TextView) itemView.findViewById(R.id.name);
            mTel = (TextView) itemView.findViewById(R.id.tel);
            mEdit = (ImageView) itemView.findViewById(R.id.edit);
            mDel = (Button) itemView.findViewById(R.id.del);
            mItem = (RelativeLayout) itemView.findViewById(R.id.item);
            mHint = (TextView) itemView.findViewById(R.id.hint);

        }
    }
    private OnSelectClickListener mOnSelectClickListener;
    public void setOnSelectClickListener(OnSelectClickListener onSelectClickListener) {
        mOnSelectClickListener = onSelectClickListener;
    }

    public interface OnSelectClickListener{
        void onSelectClick(int pos,ContactEntity.InfoBean bean,ImageView img);
    }

    private OnEditClickListener mOnEditClickListener;

    public void setOnEditClickListener(OnEditClickListener onEditClickListener) {
        mOnEditClickListener = onEditClickListener;
    }

    public interface OnEditClickListener{
        void onEditClick(ContactEntity.InfoBean eventMoreEntity,int pos);
    }

    private OnDelClickListener mOnDelClickListener;

    public void setOnDelClickListener(OnDelClickListener onDelClickListener) {
        mOnDelClickListener = onDelClickListener;
    }

    public interface OnDelClickListener{
        void onDelClick(int pos);
    }
}
