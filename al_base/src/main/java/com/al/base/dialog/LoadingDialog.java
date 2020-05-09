package com.al.base.dialog;

import android.content.Context;
import android.view.View;

import com.al.base.R;

public class LoadingDialog {

    private static ALDialog loading;

    public static void showLoading(Context c) {
        if (loading == null) {
            loading = new ALDialog.Builder(c, R.style.Loading_Dialog)
                    .setContentView(R.layout.dialog_layout_loading)
                    .setCancelable(false)
                    .create();
        }
        loading.show();
    }

    public static void showLoading(Context c, String msg) {
        if (loading == null) {
            loading = new ALDialog.Builder(c, R.style.Loading_Dialog)
                    .setContentView(R.layout.dialog_layout_loading)
                    .setCancelable(false)
                    .create();
        }
        loading.getView(R.id.tv_loading_dialog_msg).setVisibility(View.VISIBLE);
        loading.setText(R.id.tv_loading_dialog_msg, msg);
        loading.show();
    }

    public static void hideLoading() {
        if (loading != null && loading.isShowing()) {
            loading.dismiss();
            loading = null;
        }
    }
}
