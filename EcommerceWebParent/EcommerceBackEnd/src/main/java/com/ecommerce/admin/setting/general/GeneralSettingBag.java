package com.ecommerce.admin.setting.general;

import com.ecommerce.common.entity.Setting;
import com.ecommerce.common.entity.SettingBag;

import java.util.List;

public class GeneralSettingBag extends SettingBag {
    public GeneralSettingBag(List<Setting> listSettings) {
        super(listSettings);
    }

    public void updateCurrencySymbol(String value){
        super.update("CURRENCY_SYMBOL", value);
    }

    public void updateSiteLogo(String value){
        super.update("SITE_LOGO", value);
    }
}
