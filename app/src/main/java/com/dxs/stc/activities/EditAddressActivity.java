
package com.dxs.stc.activities;

import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.widget.AddressPicker.AddressPickerView;
import com.dxs.stc.widget.EditTextWithAnimator;
import com.dxs.stc.widget.ImageTextView;
import com.smarttop.library.bean.City;
import com.smarttop.library.bean.County;
import com.smarttop.library.bean.Province;
import com.smarttop.library.bean.Street;
import com.smarttop.library.widget.AddressSelector;
import com.smarttop.library.widget.BottomDialog;
import com.smarttop.library.widget.OnAddressSelectedListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditAddressActivity extends CompatStatusBarActivity implements OnAddressSelectedListener, AddressSelector.OnDialogCloseListener, AddressSelector.onSelectorAreaPositionListener {

    @BindView(R.id.tv_bar_text)
    TextView mTitleCenter;

    @BindView(R.id.et_recipient)
    EditTextWithAnimator mRecipientEt;
    @BindView(R.id.et_contact_number)
    EditTextWithAnimator mContactNumberEt;
    @BindView(R.id.itv_region)
    ImageTextView mRegionItv;
    @BindView(R.id.et_details_address)
    EditTextWithAnimator mDetailsAddressEt;
    @BindView(R.id.cb_is_default)
    CheckBox mIsDefaultCb;

    private BottomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        getWindow().setBackgroundDrawableResource(R.color.color_E6);
        ButterKnife.bind(this);
        setStatus(true, true, ContextCompat.getColor(this, R.color.navColor));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTitleCenter.setText(getString(R.string.address_add));

        initView();
    }

    private void initView() {

    }

    @OnClick({R.id.iv_bar_left, R.id.btn_add_address, R.id.itv_region})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_left:
                onBackPressed();
                break;
            case R.id.btn_add_address:
                Loger.debug("收件人：" + mRecipientEt.getText());
                Loger.debug("联系电话：" + mContactNumberEt.getText());
                Loger.debug("所在地区：" + mRegionItv.getText());
                Loger.debug("详细地址：" + mDetailsAddressEt.getText());
                Loger.debug("是否是默认地址：" + mIsDefaultCb.isChecked());
                break;
            case R.id.itv_region:
//                showAddressPickerPop();
                showBottomDialog();
                break;
        }
    }


    /**
     * 显示地址选择的 pop
     */
    private void showAddressPickerPop() {
        final PopupWindow popupWindow = new PopupWindow(this);
        View rootView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_address, null, false);
        AddressPickerView addressView = rootView.findViewById(R.id.apvAddress);
        addressView.setOnAddressPickerSure(new AddressPickerView.OnAddressPickerSureListener() {
            @Override
            public void onSureClick(String address, String provinceCode, String cityCode, String districtCode) {
                mRegionItv.setText(address);
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(rootView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAsDropDown(mRegionItv);

    }

    private void showBottomDialog() {
        if (dialog != null) {
            dialog.show();
        } else {
            dialog = new BottomDialog(this);
            dialog.setOnAddressSelectedListener(this);
            dialog.setDialogDismisListener(this);
            dialog.setTextSize(14);// 设置字体的大小
            dialog.setIndicatorBackgroundColor(R.color.mainColor);// 设置指示器的颜色
            dialog.setTextSelectedColor(R.color.mainColor);// 设置字体获得焦点的颜色
            dialog.setTextUnSelectedColor(R.color.navColor);// 设置字体没有获得焦点的颜色
            dialog.setSelectorAreaPositionListener(this);
            dialog.show();
        }
    }

    private String provinceCode;
    private String cityCode;
    private String countyCode;
    private String streetCode;
    private int provincePosition;
    private int cityPosition;
    private int countyPosition;
    private int streetPosition;

    @Override
    public void dialogclose() {

        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void selectorAreaPosition(int provincePosition, int cityPosition, int countyPosition, int streetPosition) {

        this.provincePosition = provincePosition;
        this.cityPosition = cityPosition;
        this.countyPosition = countyPosition;
        this.streetPosition = streetPosition;
        Loger.debug(" 省份位置 =" + provincePosition);
        Loger.debug(" 城市位置 =" + cityPosition);
        Loger.debug(" 乡镇位置 =" + countyPosition);
        Loger.debug(" 街道位置 =" + streetPosition);
    }

    @Override
    public void onAddressSelected(Province province, City city, County county, Street street) {

        provinceCode = (province == null ? "" : province.code);
        cityCode = (city == null ? "" : city.code);
        countyCode = (county == null ? "" : county.code);
        streetCode = (street == null ? "" : street.code);
        Loger.debug(" 省份 id=" + provinceCode);
        Loger.debug(" 城市 id=" + cityCode);
        Loger.debug(" 乡镇 id=" + countyCode);
        Loger.debug(" 街道 id=" + streetCode);
        String s = (province == null ? "" : province.name) + " " +
                (city == null ? "" : city.name) + " " +
                (county == null ? "" : county.name) + " " +
                (street == null ? "" : street.name);
        mRegionItv.setText(s);
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
