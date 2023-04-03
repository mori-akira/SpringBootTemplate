package jp.co.molygray.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.molygray.dto.DepartmentDto;

/**
 * 部署Daoクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Mapper
public interface DepartmentDao {

    /**
     * 部署テーブル一覧選択
     *
     * @param parentDepartmentIdList 親部署IDリスト
     * @param departmentName 部署名
     * @param departmentFullName 部署正式名
     * @return 部署リスト
     */
    // @formatter:off
    public List<DepartmentDto> selectList(
            @Param("parentDepartmentIdList") List<Long> parentDepartmentIdList,
            @Param("departmentName") String departmentName,
            @Param("departmentFullName") String departmentFullName
    );
    // @formatter:on
}
