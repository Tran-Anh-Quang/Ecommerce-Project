package com.ecommerce.admin.setting.general.controller;

import com.ecommerce.admin.FileUploadUtil;
import com.ecommerce.admin.setting.general.GeneralSettingBag;
import com.ecommerce.admin.setting.general.repository.CurrencyRepository;
import com.ecommerce.admin.setting.general.service.SettingService;
import com.ecommerce.common.entity.Currency;
import com.ecommerce.common.entity.Setting;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class SettingController {

    @Autowired
    private SettingService settingService;

    @Autowired
    private CurrencyRepository currencyRepository;

    @GetMapping("/settings")
    public String listAll(Model model){
        List<Setting> listSettings = settingService.listAllSettings();
        List<Currency> listCurrencies = currencyRepository.findAllByOrderByNameAsc();

        model.addAttribute("listCurrencies", listCurrencies);

        for (Setting setting : listSettings){
            model.addAttribute(setting.getKey(), setting.getValue());
        }
        return "settings/settings";
    }

    @PostMapping("/settings/save_general")
    public String saveGeneralSettings(@RequestParam("fileImage")MultipartFile multipartFile, HttpServletRequest request, RedirectAttributes ra) throws IOException {

        GeneralSettingBag generalSettingBag = settingService.getGeneralSettings();
        saveSiteLogo(multipartFile, generalSettingBag);
        saveCurrencySymbol(request, generalSettingBag);
        updateSettingValuesFromForm(request, generalSettingBag.list());

        ra.addFlashAttribute("message", "General Setting have been save.");

        return "redirect:/settings";
    }

    private void saveSiteLogo(MultipartFile multipartFile, GeneralSettingBag generalSettingBag) throws IOException {
        if (multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            String value = "/site-logo/" + fileName;
            generalSettingBag.updateSiteLogo(value);

            String uploadDir = "/site-logo/";
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
    }

    private void saveCurrencySymbol(HttpServletRequest request, GeneralSettingBag generalSettingBag){
        Integer currencyId = Integer.parseInt(request.getParameter("CURRENCY_ID"));
        Optional<Currency> findByIdResult = currencyRepository.findById(currencyId);

        if (findByIdResult.isPresent()){
            Currency currency = findByIdResult.get();
            String symbol = currency.getSymbol();
            generalSettingBag.updateCurrencySymbol(symbol);
        }
    }

    private void updateSettingValuesFromForm(HttpServletRequest request, List<Setting> listSettings){
        for (Setting setting : listSettings){
            String value = request.getParameter(setting.getKey());
            if (value != null){
                setting.setValue(value);
            }
        }
        settingService.saveAll(listSettings);
    }
}
