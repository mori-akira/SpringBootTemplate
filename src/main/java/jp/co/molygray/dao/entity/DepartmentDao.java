package jp.co.molygray.dao.entity;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import jp.co.molygray.annotation.WithExclusiveCheck;
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
   * 一件選択メソッド
   *
   * @param departmentId 部署ID
   * @return 部署
   */
  @Select("SELECT * FROM department WHERE department_id = #{departmentId} AND delete_flg = FALSE")
  public Optional<DepartmentDto> select(@Param("departmentId") long departmentId);

  /**
   * 一覧選択メソッド
   *
   * @return 部署リスト
   */
  @Select("SELECT * FROM department WHERE delete_flg = FALSE ORDER BY department_id")
  public List<DepartmentDto> selectList();

  /**
   * 一覧検索メソッド
   *
   * @param parentDepartmentIdList 親部署IDリスト
   * @param departmentName 部署名
   * @param departmentFullName 部署正式名
   * @return 部署リスト
   */
  public List<DepartmentDto> searchList(
      @Param("parentDepartmentIdList") List<Long> parentDepartmentIdList,
      @Param("departmentName") String departmentName,
      @Param("departmentFullName") String departmentFullName);

  /**
   * 登録メソッド
   *
   * @param dto 部署Dtoインスタンス
   * @return 登録件数
   */
  public Long insert(DepartmentDto dto);

  /**
   * 更新メソッド
   *
   * @param dto 部署Dtoインスタンス
   * @return 更新件数
   */
  @WithExclusiveCheck
  public int update(DepartmentDto dto);

  /**
   * 削除メソッド
   *
   * @param departmentId 部署ID
   * @param exclusiveFlg 排他フラグ
   * @return 削除件数
   */
  @WithExclusiveCheck
  public int delete(@Param("departmentId") long departmentId,
      @Param("exclusiveFlg") String exclusiveFlg);
}
