package jp.co.molygray.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jp.co.molygray.dao.service.EmployeeInfoDao;
import jp.co.molygray.model.EmployeeModel;

/**
 * 社員のサービスクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Service
public class EmployeeService {

  /**
   * 社員情報DAO
   */
  @Autowired
  private EmployeeInfoDao employeeInfoDao;

  /**
   * 社員情報を取得するメソッド
   * <p>
   * 社員ID、社員番号のどちらか一方は必須である。
   * </p>
   *
   * @param employeeId 社員ID
   * @param employeeNumber 社員番号
   * @return 社員情報
   * @throws IllegalArgumentException 社員IDと社員番号がnullの場合
   */
  public EmployeeModel getEmployeeInfo(Long employeeId, String employeeNumber) {
    if (employeeId == null && employeeNumber == null) {
      throw new IllegalArgumentException("Either employeeId or employeeNumber is required.");
    }
    return employeeInfoDao.selectEmployeeInfo(employeeId, employeeNumber).orElse(null);
  }
}
