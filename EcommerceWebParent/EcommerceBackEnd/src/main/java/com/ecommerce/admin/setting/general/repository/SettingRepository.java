package com.ecommerce.admin.setting.general.repository;

import com.ecommerce.common.entity.Setting;
import com.ecommerce.common.entity.SettingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettingRepository extends JpaRepository<Setting, String> {

    public List<Setting> findByCategory(SettingCategory category);
}
