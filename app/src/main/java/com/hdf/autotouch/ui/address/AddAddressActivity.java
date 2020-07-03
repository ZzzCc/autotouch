package com.hdf.autotouch.ui.address;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.Address;
import com.hdf.autotouch.entity.AreaCode;
import com.hdf.autotouch.ui.areacode.AreaCodeActivity;
import com.hdf.autotouch.util.DialogHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAddressActivity extends BaseActivity<AddressPresenter> implements AddressView {

    @BindView(R.id.et_consignee)
    EditText mEtConsignee;
    @BindView(R.id.tv_area_code)
    TextView mTvAreaCode;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.tv_deliver_explain_content)
    TextView mTvDeliverExplainContent;
    @BindView(R.id.tv_del_address)
    TextView mTvDelAddress;
    @BindView(R.id.tv_save_address)
    TextView mTvSaveAddress;
    private Address mAddress;

    public static void start(Context context, Address address) {
        Intent intent = new Intent(context, AddAddressActivity.class);
        intent.putExtra("address", address);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mAddress = bundle.getParcelable("address");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_add_address;
    }

    @Override
    public void initView() {
        mPresenter = new AddressPresenter(this);

        if (ObjectUtils.isEmpty(mAddress)) {
            setTitleText(R.string.add_new_address1);
            mTvDelAddress.setVisibility(View.GONE);
        } else {
            setTitleText(R.string.edit_address);
            mEtConsignee.setText(mAddress.getUserName());
            mTvAreaCode.setText(mAddress.getPrefix());
            mEtPhone.setText(mAddress.getReceiptPhone());
            mTvDelAddress.setVisibility(View.VISIBLE);
        }

        applyClickListener(mTvAreaCode, mTvDelAddress, mTvSaveAddress);
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
        if (view.getId() == R.id.tv_area_code) {
            AreaCodeActivity.start(this);
        } else if (view.getId() == R.id.tv_del_address) {
            DialogHelper.showConfirmDialog(R.string.delete, "", () -> {
                Message msg = new Message();
                msg.what = R.id.msg_delete_address;
                msg.obj = mAddress;
                EventBus.getDefault().post(msg);
                mActivity.finish();
            });
        } else if (view.getId() == R.id.tv_save_address) {
            String consignee = mEtConsignee.getText().toString();
            String areaCode = mTvAreaCode.getText().toString();
            String phone = mEtPhone.getText().toString();
            if (ObjectUtils.isEmpty(mAddress)) {
                mPresenter.addAddress(consignee, areaCode, phone);
            } else {
                mPresenter.editAddress(mAddress.getAId(), consignee, areaCode, phone);
            }
        }
    }

    @Override
    public void onMessageEvent(Message msg) {
        super.onMessageEvent(msg);
        if (msg.what == R.id.msg_item_click) {
            AreaCode areaCode = (AreaCode) msg.obj;
            mTvAreaCode.setText(String.format(getString(R.string.plus), areaCode.getAreacode()));
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_top, menu);
//        MenuItem menuSave = menu.findItem(R.id.menu_save);
//        menuSave.setVisible(true);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu_save) {
//
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void getAddress(List<Address> list) {
    }

    @Override
    public void updateAddress() {
        sendMessage(R.id.msg_update_address, null);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
