package com.asheef.hrms.repo;

import com.asheef.hrms.entity.HrmModules;
import com.asheef.hrms.model.response.HrmModulesResponseProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HrmModulesRepository extends JpaRepository<HrmModules, Integer> {
    public Optional<HrmModules> findByModuleCode(String moduleCode);

    public Optional<HrmModules> findByModuleId(Integer moduleId);

    public Optional<HrmModules> findByModuleIdAndModuleCode(Integer moduleId, String moduleCode);

    @Query(value = """
        select m2.* from hrm_modules m1\s
        left join hrm_modules m2 on m2.module_id = m1.id and m2.module_code = :childModuleCode AND m2.is_active = 1\s
        where m1.module_code = :parentModuleCode and m1.is_active = 1 AND m2.id is not null
   \s""",nativeQuery = true)
    public Optional<HrmModules> findChildByModuleCodes(String parentModuleCode, String childModuleCode);

    @Query(value = """
        select 
            m1.id as id,
            m1.module_id as moduleId,
            m1.module_code as moduleCode,
            m1.description as description
        from hrm_modules m1
        left join hrm_modules m2 ON m2.id = m1.module_id and m2.module_code = :ctrlModuleCode AND m2.is_active = 1
        where m1.id = :id and m2.id is not null
   \s""",nativeQuery = true)
    public Optional<HrmModulesResponseProjection> findByIdAndCtrlModuleCode(Integer id, String ctrlModuleCode);
}
