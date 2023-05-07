package jp.co.molygray.dao.entity;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import jp.co.molygray.annotation.WithExclusiveCheck;
import jp.co.molygray.dto.EmployeeQualificationMapDto;

/**
 * 社員所持資格DAOクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Mapper
public interface EmployeeQualificationMapDao {

  /**
   * 一件選択メソッド
   *
   * @param employeeQualificationMapId 社員所持資格ID
   * @return 社員所持資格
   */
  public Optional<EmployeeQualificationMapDto> select(
      @Param("employeeQualificationMapId") long employeeQualificationMapId);

  /**
   * 一覧選択メソッド
   *
   * @return 社員所持資格リスト
   */
  public List<EmployeeQualificationMapDto> selectList();

  /**
   * 登録メソッド
   *
   * @param dto 社員所持資格Dtoインスタンス
   * @return 登録件数
   */
  public Long insert(EmployeeQualificationMapDto dto);

  /**
   * 更新メソッド
   *
   * @param dto 社員所持資格Dtoインスタンス
   * @return 更新件数
   */
  @WithExclusiveCheck
  public int update(EmployeeQualificationMapDto dto);

  /**
   * 削除メソッド
   *
   * @param employeeId 社員所持資格ID
   * @param exclusiveFlg 排他フラグ
   * @return 削除件数
   */
  @WithExclusiveCheck
  public int delete(@Param("employeeQualificationMapId") long employeeQualificationMapId,
      @Param("exclusiveFlg") String exclusiveFlg);
}
