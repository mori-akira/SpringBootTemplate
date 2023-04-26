package jp.co.molygray.dao.entity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
   * IDによる単一SELECT処理
   *
   * @param id ID
   * @return 社員DTO
   */
  public EmployeeDto select(@Param("id") long id);

  /**
   * 社員番号による単一SELECT処理
   *
   * @param employeeNumber 社員番号
   * @return 社員DTO
   */
  public EmployeeDto selectByEmployeeNumber(@Param("employeeNumber") String employeeNumber);
}
