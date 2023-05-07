package jp.co.molygray.dao.entity;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import jp.co.molygray.annotation.WithExclusiveCheck;
import jp.co.molygray.dto.EmployeeDto;

/**
 * 社員DAOクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Mapper
public interface EmployeeDao {

  /**
   * 一件選択メソッド
   *
   * @param employeeId 社員ID
   * @return 社員
   */
  public Optional<EmployeeDto> select(@Param("employeeId") long employeeId);

  /**
   * 一覧選択メソッド
   *
   * @return 社員リスト
   */
  public List<EmployeeDto> selectList();

  /**
   * 社員番号による一件選択メソッド
   *
   * @param employeeNumber 社員番号
   * @return 社員
   */
  public EmployeeDto selectByEmployeeNumber(@Param("employeeNumber") String employeeNumber);

  /**
   * 登録メソッド
   *
   * @param dto 社員Dtoインスタンス
   * @return 登録件数
   */
  public Long insert(EmployeeDto dto);

  /**
   * 更新メソッド
   *
   * @param dto 社員Dtoインスタンス
   * @return 更新件数
   */
  @WithExclusiveCheck
  public int update(EmployeeDto dto);

  /**
   * 削除メソッド
   *
   * @param employeeId 社員ID
   * @param exclusiveFlg 排他フラグ
   * @return 削除件数
   */
  @WithExclusiveCheck
  public int delete(@Param("employeeId") long employeeId,
      @Param("exclusiveFlg") String exclusiveFlg);
}
