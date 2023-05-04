package jp.co.molygray.dao.entity;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
  @Select("SELECT * FROM employee WHERE employee_id = #{employeeId} AND delete_flg = FALSE")
  public Optional<EmployeeDto> select(@Param("employeeId") long employeeId);

  /**
   * 一覧選択メソッド
   *
   * @return 社員リスト
   */
  @Select("SELECT * FROM employee WHERE delete_flg = FALSE ORDER BY employee_id")
  public List<EmployeeDto> selectList();

  /**
   * 社員番号による一件選択メソッド
   *
   * @param employeeNumber 社員番号
   * @return 社員
   */
  public EmployeeDto selectByEmployeeNumber(@Param("employeeNumber") String employeeNumber);
}
