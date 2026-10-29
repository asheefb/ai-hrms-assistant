package com.asheef.hrms.repo;

import com.asheef.hrms.entity.CommonModules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommonModulesRepository extends JpaRepository<CommonModules, Integer> {

    @Query(value = """
        select m2.* from common_modules m1\s
        left join common_modules m2 on m2.module_id = m1.id and m2.module_code = :childModuleCode AND m2.is_active = 1\s
        where m1.module_code = :parentModuleCode and m1.is_active = 1 AND m2.id is not null
    """, nativeQuery = true)
    public Optional<CommonModules> findChildByModuleCodes(String parentModuleCode, String childModuleCode);

    @Query(value = """
        select child.* from common_modules child
        join common_modules parent on parent.id = child.module_id
        where child.ref_module = :refModule and child.is_active = 1
          and parent.module_code = :parentModuleCode and parent.is_active = 1
    """, nativeQuery = true)
    public Optional<CommonModules> findActiveChildByRefModuleAndParentModuleCode(String refModule, String parentModuleCode);
}
