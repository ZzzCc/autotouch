package com.hdf.autotouch.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.hdf.autotouch.R;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import static com.blankj.utilcode.util.StringUtils.getString;

@SuppressLint("InflateParams")
public class DialogHelper {

    public static void showConfirmDialog(int stringId, String content, OnConfirmListener listener) {
        Activity activity = ActivityUtils.getTopActivity();
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_confirm, null);
        Dialog dialog = new AlertDialog
                .Builder(activity)
                .setView(view)
                .show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = SizeUtils.dp2px(280);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        TextView tvConfirmAlert = view.findViewById(R.id.tv_confirm_alert);
        if (!"".equals(content)) {
            tvConfirmAlert.setText(content);
        } else {
            tvConfirmAlert.setText(String.format(activity.getString(R.string.confirm_alert), activity.getString(stringId)));
        }
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> dialog.dismiss());
        view.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            dialog.dismiss();
            listener.onConfirm();
        });
    }

    public static void showPurchaseMineFieldDialog(String str1, String str2, OnConfirmListener listener) {
        Activity activity = ActivityUtils.getTopActivity();
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_purchase_mine_field, null);
        Dialog dialog = new AlertDialog
                .Builder(activity)
                .setView(view)
                .show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = SizeUtils.dp2px(280);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        TextView tvMessage = view.findViewById(R.id.tv_message);
        tvMessage.setText(String.format(activity.getString(R.string.purchase_mine_field_alert), str1, str2));
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> dialog.dismiss());
        view.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            dialog.dismiss();
            listener.onConfirm();
        });
    }

    public static void showPurchaseAgreementDialog() {
        Activity activity = ActivityUtils.getTopActivity();
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_purchase_agreement, null);
        Dialog dialog = new AlertDialog
                .Builder(activity)
                .setView(view)
                .show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = SizeUtils.dp2px(280);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        TextView tvMessage = view.findViewById(R.id.tv_message);
        String str = ResourceUtils.readAssets2String("alert.txt");
        tvMessage.setText(str);
        tvMessage.setMovementMethod(ScrollingMovementMethod.getInstance());
        view.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            dialog.dismiss();
            SPManager.setIsAgree(true);
        });
    }

    public static void showCustomerServiceDialog(String text) {
        Activity activity = ActivityUtils.getTopActivity();
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_customer_service, null);
        Dialog dialog = new AlertDialog
                .Builder(activity)
                .setView(view)
                .show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = SizeUtils.dp2px(300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        TextView tvCustomerService = view.findViewById(R.id.tv_customer_service1);
        SpanUtils.with(tvCustomerService)
                .append(getString(R.string.customer_service) + " ")
                .setForegroundColor(ColorUtils.getColor(R.color.light_black1))
                .append(text)
                .setForegroundColor(ColorUtils.getColor(R.color.app_color))
                .create();
        view.findViewById(R.id.btn_copy).setOnClickListener(v -> {
            dialog.dismiss();
            ClipboardUtils.copyText(text);
        });
    }

    public static void showVersionCodeDialog(String str1, int status, OnVersionListener listener1) {
        Activity activity = ActivityUtils.getTopActivity();
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_version_code, null);
        Dialog dialog = new AlertDialog
                .Builder(activity)
                .setView(view)
                .show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = SizeUtils.dp2px(280);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        TextView tvContext = view.findViewById(R.id.tv_context);
        TextView btn_cancel = view.findViewById(R.id.btn_cancel);
        TextView btn_confirm = view.findViewById(R.id.btn_confirm);
        TextView btn_confirm1 = view.findViewById(R.id.btn_confirm1);
        TextView view_divider = view.findViewById(R.id.view_divider);
        tvContext.setText(str1);
        btn_cancel.setOnClickListener(v -> {
            dialog.dismiss();
            listener1.onCancel();
        });
        //status  0,不强制 1，强制
        if (status == 0) {
            btn_cancel.setVisibility(View.VISIBLE);
            btn_confirm.setOnClickListener(v -> {
                dialog.dismiss();
                listener1.onConfirm();
            });
        } else if (status == 1) {
            btn_cancel.setVisibility(View.GONE);
            btn_confirm.setVisibility(View.GONE);
            btn_confirm1.setOnClickListener(v -> {
                listener1.onConfirm();
            });
        }
    }

    public static void showSelectCurrencyDialog(OnSelectCurrencyListener listener) {
        Activity activity = ActivityUtils.getTopActivity();
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_select_currency, null);
        Dialog dialog = new Dialog(activity, R.style.BottomDialogStyle);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ScreenUtils.getScreenWidth();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
        view.findViewById(R.id.tv_gtse).setOnClickListener(v -> {
            dialog.dismiss();
            listener.onSelectCurrency(getString(R.string.filecoin));
        });
        view.findViewById(R.id.tv_usdt).setOnClickListener(v -> {
            dialog.dismiss();
            listener.onSelectCurrency(getString(R.string.usdt));
        });
    }

    public static void showExchangeConfirmDialog(String rollOut, String intoNumber, String exchangeMinerFee, OnConfirmListener listener) {
        Activity activity = ActivityUtils.getTopActivity();
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_exchange_confirm, null);
        Dialog dialog = new Dialog(activity, R.style.BottomDialogStyle);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ScreenUtils.getScreenWidth();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.show();
        TextView tvRollOutValue = view.findViewById(R.id.tv_roll_out_value);
        TextView tvIntoNumberValue = view.findViewById(R.id.tv_into_number_value);
        TextView tvExchangeMinerFeeValue = view.findViewById(R.id.tv_exchange_miner_fee_value);
        tvRollOutValue.setText(rollOut);
        tvIntoNumberValue.setText(intoNumber);
        tvExchangeMinerFeeValue.setText(exchangeMinerFee);
        view.findViewById(R.id.iv_close).setOnClickListener(v -> dialog.dismiss());
        view.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            dialog.dismiss();
            listener.onConfirm();
        });
    }

    public interface OnConfirmListener {
        void onConfirm();
    }

    public interface OnVersionListener {
        void onCancel();

        void onConfirm();
    }

    public interface OnSelectCurrencyListener {
        void onSelectCurrency(String currency);
    }


    public static void showNoeBinndDialog(String finishTime, OnLockedListener listener) {
        Activity activity = ActivityUtils.getTopActivity();
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_noe_bind, null);
        Dialog dialog = new AlertDialog
                .Builder(activity)
                .setView(view)
                .show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = SizeUtils.dp2px(280);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        TextView mTvBoxAlert = view.findViewById(R.id.tv_box_alert);
        TextView mTvBoxContext = view.findViewById(R.id.tv_box_context);
        QMUIRoundButton mBtnCancel = view.findViewById(R.id.btn_cancel);
        QMUIRoundButton mBtnConfirm = view.findViewById(R.id.btn_confirm);

        mTvBoxAlert.setText(getString(R.string.one_binding));
        mTvBoxContext.setText(finishTime);
        mBtnCancel.setText(getString(R.string.think));
        mBtnConfirm.setText(getString(R.string.onfirm));

        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> {
            dialog.dismiss();
        });
        view.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            dialog.dismiss();
            listener.onLocked();
        });
    }

    public interface OnLockedListener {
        void onLocked();
    }

}
