package com.ecommerce.admin.brand.controller;

import com.ecommerce.admin.brand.exception.BrandNotFoundException;
import com.ecommerce.admin.brand.service.BrandService;

import com.ecommerce.admin.export.BrandExcelExporter;
import com.ecommerce.common.entity.Brand;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/brands")
    public String listFirstPage(Model model) {
        return listByPage(1, model);
    }

    @GetMapping("/brands/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model) {
        Page<Brand> page = brandService.listByPage(pageNum);
        List<Brand> listBrands = page.getContent();
        model.addAttribute("listBrands", listBrands);

        long startCount = (pageNum - 1) * brandService.BRANDS_PER_PAGE + 1;
        long endCount = startCount + brandService.BRANDS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("startCount", startCount);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listBrands", listBrands);

        return "brands/brands";
    }

    @GetMapping("/brands/delete/{id}")
    public String deleteBrand(@PathVariable(name = "id") Integer id, Model model,
                                 RedirectAttributes redirectAttributes) {
        try {
            brandService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The brand's ID: " + id + " has been deleted successful!");
        } catch (BrandNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/brands";
    }

    @GetMapping("/brands/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<Brand> listBrands = brandService.listAll();
        BrandExcelExporter exporter = new BrandExcelExporter();
        exporter.export(listBrands, response);
    }
}
