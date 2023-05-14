package jp.co.molygray.dao.service;

import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import jp.co.molygray.model.EmployeeModel;

/**
 * 社員情報取得Daoクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Mapper
public interface EmployeeInfoDao {

  /**
   * 社員情報取得メソッド
   * <p>
   * 引数で指定する社員IDまたは社員番号は、非nullの場合に条件に使用する。
   * </p>
   *
   * @param employeeId 社員ID
   * @param employeeNumber 社員番号
   * @return 社員情報
   */
  public Optional<EmployeeModel> selectEmployeeInfo(
      @Param("employeeId") Long employeeId,
      @Param("employeeNumber") String employeeNumber);
}
