package com.bjike.goddess.reportmanagement.service;

import com.bjike.goddess.common.api.dto.Restrict;
import com.bjike.goddess.common.api.exception.SerException;
import com.bjike.goddess.common.jpa.service.ServiceImpl;
import com.bjike.goddess.common.utils.bean.BeanTransform;
import com.bjike.goddess.reportmanagement.bo.FormulaBO;
import com.bjike.goddess.reportmanagement.dto.FormulaDTO;
import com.bjike.goddess.reportmanagement.entity.Formula;
import com.bjike.goddess.reportmanagement.enums.Form;
import com.bjike.goddess.reportmanagement.to.FormulaTO;
import com.bjike.goddess.reportmanagement.utils.Utils;
import com.bjike.goddess.subjectcollect.api.SubjectCollectAPI;
import com.bjike.goddess.subjectcollect.bo.SubjectCollectBO;
import com.bjike.goddess.subjectcollect.dto.SubjectCollectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 对应的公式业务实现
 *
 * @Author: [ chenjunhao ]
 * @Date: [ 2017-06-20 09:56 ]
 * @Description: [ 对应的公式业务实现 ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
@CacheConfig(cacheNames = "reportmanagementSerCache")
@Service
public class FormulaSerImpl extends ServiceImpl<Formula, FormulaDTO> implements FormulaSer {
    @Autowired
    private SubjectCollectAPI subjectCollectAPI;

    @Override
    public List<FormulaBO> findByFid(String foreignId, String startTime, String endTime) throws SerException {
        String[] strings = new String[]{foreignId};
        List<Formula> list = null;
        for (String s : strings) {
            String sql = "select project,type from reportmanagement_formula" +
                    " where foreign_id='" + s + "' ORDER BY type ASC";
            String[] fileds = new String[]{"project", "type"};
            list = super.findBySql(sql, Formula.class, fileds);
        }
        Integer startMonth = 0;
        Integer endMonth = 0;
        LocalDate s = Utils.tranTime(startTime);
        startMonth = s.getMonthValue();
        LocalDate e = Utils.tranTime(endTime);
        endMonth = e.getMonthValue();
        List<FormulaBO> boList = new ArrayList<FormulaBO>();
        if ((list != null) && (!list.isEmpty())) {
            Double beginSum = 0.00;
            Double endSum = 0.00;
            FormulaBO addBO = new FormulaBO();
            addBO.setProject("加：");
            boList.add(addBO);
            boolean b = true;
            for (Formula f : list) {
                if ("2".equals(f.getType()) && b) {    //2代表减
                    FormulaBO bo = new FormulaBO();
                    bo.setProject("减：");
                    boList.add(bo);
                    b = false;
                }
                SubjectCollectDTO subjectCollectDTO = new SubjectCollectDTO();
                subjectCollectDTO.getConditions().add(Restrict.eq("firstSubject", f.getProject()));
                SubjectCollectDTO beginDTO = new SubjectCollectDTO();
                beginDTO.getConditions().add(Restrict.eq("firstSubject", f.getProject()));
                if (LocalDate.now().getYear() == e.getYear()) {
                    if (LocalDate.now().getYear() == s.getYear()) {
                        Integer[] months = new Integer[]{startMonth, endMonth};
                        subjectCollectDTO.getConditions().add(Restrict.between("months", months));
                        SubjectCollectBO subjectCollectBO = subjectCollectAPI.getSum(subjectCollectDTO);
                        FormulaBO bo = BeanTransform.copyProperties(f, FormulaBO.class);
                        if (Form.DEBIT.equals(bo.getForm())) {
                            bo.setEnd(subjectCollectBO.getEndDebitAmount());
                        }else if(Form.CREDIT.equals(bo.getForm())){
                            bo.setEnd(subjectCollectBO.getEndCreditAmount());
                        }
                        if ("1".equals(f.getType())) {     //1代表加
                            endSum += bo.getEnd();
                        } else if ("2".equals(f.getType())) {  //2代表减
                            endSum = endSum - bo.getEnd();
                        }
                        if (startMonth != 1) {
                            beginDTO.getConditions().add(Restrict.eq("months", startMonth - 1));
                            SubjectCollectBO beginBO = subjectCollectAPI.getSum(beginDTO);
                            if (Form.DEBIT.equals(bo.getForm())) {
                                bo.setBegin(beginBO.getEndDebitAmount());
                            }else if(Form.CREDIT.equals(bo.getForm())){
                                bo.setBegin(beginBO.getEndCreditAmount());
                            }
                            if ("1".equals(f.getType())) {     //1代表加
                                beginSum += bo.getBegin();
                            } else if ("2".equals(f.getType())) {  //2代表减
                                beginSum = beginSum - bo.getBegin();
                            }
                        } else {
                            beginDTO.getConditions().add(Restrict.eq("months", 1));
                            SubjectCollectBO beginBO = subjectCollectAPI.getSum(beginDTO);
                            if (Form.DEBIT.equals(bo.getForm())) {
                                bo.setBegin(beginBO.getBeginningDebitAmount());
                            }else if(Form.CREDIT.equals(bo.getForm())){
                                bo.setBegin(beginBO.getBeginningCreditAmount());
                            }
                            if ("1".equals(f.getType())) {     //1代表加
                                beginSum += bo.getBegin();
                            } else if ("2".equals(f.getType())) {  //2代表减
                                beginSum = beginSum - bo.getBegin();
                            }
                        }
                        boList.add(bo);
                    } else {
                        Integer[] months = new Integer[]{1, endMonth};
                        subjectCollectDTO.getConditions().add(Restrict.between("months", months));
                        SubjectCollectBO subjectCollectBO = subjectCollectAPI.getSum(subjectCollectDTO);
                        FormulaBO bo = BeanTransform.copyProperties(f, FormulaBO.class);
                        if (Form.DEBIT.equals(bo.getForm())) {
                            bo.setEnd(subjectCollectBO.getEndDebitAmount());
                        }else if(Form.CREDIT.equals(bo.getForm())){
                            bo.setEnd(subjectCollectBO.getEndCreditAmount());
                        }
                        beginDTO.getConditions().add(Restrict.eq("months", 1));
                        SubjectCollectBO beginBO = subjectCollectAPI.getSum(beginDTO);
                        if (Form.DEBIT.equals(bo.getForm())) {
                            bo.setBegin(beginBO.getBeginningDebitAmount());
                        }else if(Form.CREDIT.equals(bo.getForm())){
                            bo.setBegin(beginBO.getBeginningCreditAmount());
                        }
                        if ("1".equals(f.getType())) {     //1代表加
                            beginSum += bo.getBegin();
                            endSum += bo.getEnd();
                        } else if ("2".equals(f.getType())) {  //2代表减
                            beginSum = beginSum - bo.getBegin();
                            endSum = endSum - bo.getEnd();
                        }
                    }
                }
            }
            FormulaBO bo = new FormulaBO();
            bo.setProject("合计：");
            bo.setBegin(beginSum);
            bo.setEnd(endSum);
            boList.add(bo);
            return boList;
        }
        return null;
    }

//    @Override
//    public FormulaBO add(FormulaTO to) throws SerException {
//        Formula entity = BeanTransform.copyProperties(to, Formula.class, true);
//        entity.setType("1");
//        super.save(entity);
//        return BeanTransform.copyProperties(entity, FormulaBO.class);
//    }

//    @Override
//    public FormulaBO remove(FormulaTO to) throws SerException {
//        Formula entity = BeanTransform.copyProperties(to, Formula.class, true);
//        entity.setType("2");
//        super.save(entity);
//        return BeanTransform.copyProperties(entity, FormulaBO.class);
//    }
}