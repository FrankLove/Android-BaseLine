package com.android.baseline.framework.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.baseline.AppDroid;
import com.android.baseline.R;
import com.android.baseline.R2;
import com.android.baseline.framework.logic.InfoResult;
import com.android.baseline.framework.ui.activity.base.BaseActivity;
import com.android.baseline.framework.ui.activity.base.UIInterface;
import com.android.baseline.framework.ui.adapter.page.PageWrapper;
import com.android.baseline.framework.ui.view.CustomDialog;
import com.android.baseline.framework.ui.view.LoadingView;
import com.android.baseline.util.APKUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 基类Activity [主要提供对话框、进度条和其他有关UI才做相关的功能]
 *
 * @author hiphonezhu@gmail.com
 * @version [Android-BaseLine, 2014-9-15]
 */
public class BasicActivity extends BaseActivity implements UIInterface {
    /**
     * 基类Toast
     */
    private static Toast mToast;
    protected boolean mPaused;
    protected boolean mDestroyed;

    /**
     * 加载进度
     */
    LoadingView mLoadingView;
    /**
     * 视图加载器
     */
    protected LayoutInflater mInflater;

    /**
     * 标题栏
     */
    protected View llLeft;
    protected Button leftBtn;
    protected TextView titleTxt;
    protected TextView subTitleTxt;
    protected View llRight;
    protected Button rightBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppDroid.getInstance().uiStateHelper.addActivity(this);
    }

    @Override
    protected void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);
        toolbar.removeAllViews();
        // add custom title bar here
        mInflater = LayoutInflater.from(this);

        // 通用标题栏
        if (defaultTitleBarVisible()) {
            if (getCustomTitleLayout() != -1) {
                // 使用用户自定义的标题布局
                mInflater.inflate(getCustomTitleLayout(), toolbar);
            } else {
                // 通用标题栏
                View commonTitle = mInflater.inflate(R.layout.layout_common_title, toolbar);
                llLeft = commonTitle.findViewById(R.id.ll_left);
                leftBtn = (Button) commonTitle.findViewById(R.id.title_left_btn);
                titleTxt = (TextView) commonTitle.findViewById(R.id.title_txt);
                subTitleTxt = (TextView) commonTitle.findViewById(R.id.sub_title_txt);
                llRight = commonTitle.findViewById(R.id.ll_right);
                rightBtn = (Button) commonTitle.findViewById(R.id.title_right_btn);
                setLeftFinish(null);
            }
        }
    }

    protected void afterSetContentView() {
        ButterKnife.bind(this);

        mLoadingView = (LoadingView) findViewById(R.id.loading_view);
        if (mLoadingView != null) {
            mLoadingView.register(this);
        }
    }

    /**
     * 返回用户自定义的标题布局
     *
     * @return
     */
    protected
    @LayoutRes
    int getCustomTitleLayout() {
        return -1;
    }

    protected void setLeftText(@StringRes int left) {
        llLeft.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) leftBtn.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        leftBtn.setLayoutParams(layoutParams);
        leftBtn.setText(left);
        leftBtn.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
    }

    protected void setLeftDrawable(@DrawableRes int left) {
        llLeft.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) leftBtn.getLayoutParams();
        layoutParams.width = APKUtil.dip2px(this, 20);
        layoutParams.height = APKUtil.dip2px(this, 20);
        leftBtn.setLayoutParams(layoutParams);
        leftBtn.setText(null);
        leftBtn.setBackgroundResource(left);
    }

    protected void setLeftDrawable(@DrawableRes int left, int wDp, int hDp) {
        llLeft.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) leftBtn.getLayoutParams();
        layoutParams.width = wDp;
        layoutParams.height = hDp;
        leftBtn.setLayoutParams(layoutParams);
        leftBtn.setText(null);
        leftBtn.setBackgroundResource(left);
    }

    protected void hideLeft() {
        llLeft.setVisibility(View.INVISIBLE);
    }

    protected void setLeftListener(View.OnClickListener listener) {
        if (llLeft != null) {
            llLeft.setOnClickListener(listener);
        }
        if (leftBtn != null) {
            leftBtn.setOnClickListener(listener);
        }
    }

    protected void setLeftFinish(final View.OnClickListener listener) {
        setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                finish();
            }
        });
    }

    protected void setRightText(@StringRes int right) {
        llRight.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rightBtn.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        rightBtn.setLayoutParams(layoutParams);
        rightBtn.setText(right);
        rightBtn.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
    }

    protected void setRightDrawable(@DrawableRes int right) {
        llRight.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rightBtn.getLayoutParams();
        layoutParams.width = APKUtil.dip2px(this, 20);
        layoutParams.height = APKUtil.dip2px(this, 20);
        rightBtn.setLayoutParams(layoutParams);
        rightBtn.setText(null);
        rightBtn.setBackgroundResource(right);
    }

    protected void setRightDrawable(@DrawableRes int right, int wDp, int hDp) {
        llRight.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rightBtn.getLayoutParams();
        layoutParams.width = wDp;
        layoutParams.height = hDp;
        rightBtn.setLayoutParams(layoutParams);
        rightBtn.setText(null);
        rightBtn.setBackgroundResource(right);
    }

    protected void hideRight() {
        llRight.setVisibility(View.INVISIBLE);
    }

    protected void setRightListener(View.OnClickListener listener) {
        if (llRight != null) {
            llRight.setOnClickListener(listener);
        }
        if (rightBtn != null) {
            rightBtn.setOnClickListener(listener);
        }
    }

    protected void setTitleText(@StringRes int title) {
        titleTxt.setText(title);
    }

    protected void setSubTitleText(@StringRes int subTitle) {
        subTitleTxt.setVisibility(View.VISIBLE);
        subTitleTxt.setText(subTitle);
    }

    protected void hideSubTitle() {
        subTitleTxt.setVisibility(View.GONE);
    }

    /**
     * 设置标题栏属性
     *
     * @param leftVisible  左侧按钮是否可见
     * @param resId        标题资源id
     * @param rightVisible 右侧按钮是否可见
     */
    protected void setTitleBar(boolean leftVisible, @StringRes int resId, boolean rightVisible) {
        setTitleBar(leftVisible, getString(resId), rightVisible);
    }

    /**
     * 设置标题栏属性
     *
     * @param leftVisible  左侧按钮是否可见
     * @param title        标题
     * @param rightVisible 右侧按钮是否可见
     */
    protected void setTitleBar(boolean leftVisible, String title, boolean rightVisible) {
        llLeft.setVisibility(leftVisible ? View.VISIBLE : View.INVISIBLE);
        titleTxt.setText(title);
        llRight.setVisibility(rightVisible ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 正在加载
     */
    protected void onLoading() {
        onLoading(R.string.app_name);
    }

    /**
     * 正在加载
     *
     * @param obj
     */
    protected void onLoading(Object obj) {
        onLoading(R.string.app_name, obj);
    }

    /**
     * 正在加载
     *
     * @param stringId 描述信息
     */
    protected void onLoading(int stringId) {
        onLoading(getResources().getString(stringId));
    }

    /**
     * 正在加载
     *
     * @param stringId 描述信息
     * @param obj
     */
    public void onLoading(int stringId, Object obj) {
        onLoading(getResources().getString(stringId), obj);
    }

    /**
     * 正在加载
     *
     * @param loadDesc 描述信息
     */
    protected void onLoading(String loadDesc) {
        mLoadingView.onLoading(loadDesc, null);
    }

    /**
     * 正在加载
     *
     * @param loadDesc 描述信息
     * @param obj      传递的参数
     */
    public void onLoading(String loadDesc, Object obj) {
        mLoadingView.onLoading(loadDesc, obj);
    }

    /**
     * 失败
     */
    protected void onFailure() {
        onFailure(R.string.loading_failure);
    }

    /**
     * 失败
     *
     * @param stringId 描述信息
     */
    protected void onFailure(int stringId) {
        onFailure(getResources().getString(stringId));
    }

    /**
     * 失败
     *
     * @param errorDesc 描述信息
     */
    protected void onFailure(String errorDesc) {
        mLoadingView.onFailure(errorDesc);
    }

    /**
     * 成功
     */
    protected void onSuccess() {
        mLoadingView.onSuccess();
    }

    /**
     * 根据字符串 show toast<BR>
     *
     * @param message 字符串
     */
    public void showToast(CharSequence message) {
        if (mPaused) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(),
                    message,
                    Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    /**
     * 分页查询空数据提示语
     * 注意：需要在 {@link PageWrapper#addDataSource}] 之前调用。
     * @param pageWrapper
     * @param source
     * @param <T>
     */
    public <T> void showPagingEmptyToast(PageWrapper pageWrapper, List<T> source) {
        if (source == null || source.size() == 0) {
            if (!pageWrapper.isFirstPage()) {
                showToast(getString(R.string.nomore_data));
            } else {
                showToast(getString(R.string.no_data));
            }
        }
    }

    public void showProgress(String message) {
        showProgress(message, true);
    }

    CustomDialog customDialog;
    TextView tipTextView;

    public void showProgress(String message, boolean cancelable) {
        if (mPaused) {
            return;
        }
        if (customDialog == null) {
            customDialog = new CustomDialog(this).setContentView(R.layout.dialog_loading)
                    .setCancelable(cancelable)
                    .setCanceledOnTouchOutside(false)
                    .create();
        } else {
            customDialog.dismiss();
        }
        customDialog.getDialog().setCancelable(cancelable);
        customDialog.show();

        if (tipTextView == null) {
            tipTextView = (TextView) customDialog.findViewById(R.id.tipTextView);
        }

        if (!TextUtils.isEmpty(message)) {
            tipTextView.setText(message);
        } else {
            tipTextView.setText("数据加载中...");
        }
    }

    public void hideProgress() {
        if (customDialog != null) {
            customDialog.dismiss();
        }
    }

    protected boolean checkResponse(Message msg) {
        return checkResponse(msg, null, null, true);
    }

    protected boolean checkResponse(Message msg, boolean tipError) {
        return checkResponse(msg, null, null, tipError);
    }

    protected boolean checkResponse(Message msg, String errorTip) {
        return checkResponse(msg, null, errorTip, true);
    }

    protected boolean checkResponse(Message msg, String successTip, String errorTip) {
        return checkResponse(msg, successTip, errorTip, true);
    }

    /**
     * 校验服务器响应结果
     *
     * @param msg
     * @param successTip 成功提示
     * @param errorTip   失败提示    为空使用服务器信息或本地固定信息
     * @param tipError   是否提示错误信息
     * @return true 业务成功, false业务失败
     */
    protected boolean checkResponse(Message msg, String successTip, String errorTip, boolean tipError) {
        if (msg.obj instanceof InfoResult) {
            InfoResult result = (InfoResult) msg.obj;
            if (result.isSuccess()) {
                if (!TextUtils.isEmpty(successTip)) {
                    showToast(successTip);
                }
                return true;
            } else {
                if (tipError) {
                    if (!TextUtils.isEmpty(errorTip)) {
                        showToast(errorTip);
                    } else if (!TextUtils.isEmpty(result.getDesc())) {
                        showToast(result.getDesc());
                    } else {
                        showToast(getString(R.string.requesting_failure));
                    }
                }
                return false;
            }
        } else {
            if (tipError) {
                if (!TextUtils.isEmpty(errorTip)) {
                    showToast(errorTip);
                } else {
                    showToast(getString(R.string.requesting_failure));
                }
            }
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPaused = false;
    }

    /**
     * 此处重写默认情况下关闭loading dialog [子类希望改变此行为, 可以调用defaultHideDialog(false), 详见
     * {@link #defaultDialogHidden(boolean)}]
     *
     * @param msg
     * @see BaseActivity#onResponse(android.os.Message)
     */
    public void onResponse(Message msg) {
        if (dialogHidden) {
            hideProgress();
        }
    }

    boolean dialogHidden = true;

    /**
     * 设置网络请求结束是否关闭对话框, 默认是关闭
     *
     * @param hidden true关闭 false不关闭
     */
    protected void defaultDialogHidden(boolean hidden) {
        dialogHidden = hidden;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPaused = true;
        /**
         * 这里进行一些输入法的隐藏操作
         */
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (null != imm && imm.isActive()) {
            if (null != this.getCurrentFocus() && null != this.getCurrentFocus().getWindowToken()) {
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getApplicationWindowToken(),
                        0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgress();
        AppDroid.getInstance().uiStateHelper.removeActivity(this);
        mDestroyed = true;
    }
}
