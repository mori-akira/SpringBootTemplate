package jp.co.molygray.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.molygray.dao.EmployeeDao;
import jp.co.molygray.dto.EmployeeDto;

/**
 * 社員のサービスクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Service
public class EmployeeService {

    /**
     * 社員DAO
     */
    @Autowired
    private EmployeeDao employeeDao;

    /**
     * 社員情報を取得するメソッド
     *
     * @param employeeNumber 社員番号
     * @return 社員情報
     */
    public EmployeeDto getEmployeeInfo(String employeeNumber) {
        return employeeDao.selectByEmployeeNumber(employeeNumber);
    }
}
