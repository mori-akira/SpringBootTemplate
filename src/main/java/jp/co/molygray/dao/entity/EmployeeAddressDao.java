package jp.co.molygray.dao.entity;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import jp.co.molygray.annotation.WithExclusiveCheck;
import jp.co.molygray.dto.EmployeeAddressDto;

/**
 * 社員住所DAOクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Mapper
public interface EmployeeAddressDao {

  /**
   * 一件選択メソッド
   *
   * @param employeeAddressId 社員住所ID
   * @return 社員住所
   */
  public Optional<EmployeeAddressDto> select(@Param("employeeAddressId") long employeeAddressId);

  /**
   * 一覧選択メソッド
   *
   * @return 社員住所リスト
   */
  public List<EmployeeAddressDto> selectList();

  /**
   * 登録メソッド
   *
   * @param dto 社員住所Dtoインスタンス
   * @return 登録件数
   */
  public Long insert(EmployeeAddressDto dto);

  /**
   * 更新メソッド
   *
   * @param dto 社員住所Dtoインスタンス
   * @return 更新件数
   */
  @WithExclusiveCheck
  public int update(EmployeeAddressDto dto);

  /**
   * 削除メソッド
   *
   * @param employeeId 社員住所ID
   * @param exclusiveFlg 排他フラグ
   * @return 削除件数
   */
  @WithExclusiveCheck
  public int delete(@Param("employeeAddressId") long employeeAddressId,
      @Param("exclusiveFlg") String exclusiveFlg);
}
