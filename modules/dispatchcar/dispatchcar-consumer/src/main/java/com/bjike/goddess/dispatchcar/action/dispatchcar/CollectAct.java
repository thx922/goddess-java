package com.bjike.goddess.dispatchcar.action.dispatchcar;

import com.bjike.goddess.common.api.exception.ActException;
import com.bjike.goddess.common.api.exception.SerException;
import com.bjike.goddess.common.api.restful.Result;
import com.bjike.goddess.common.consumer.restful.ActResult;
import com.bjike.goddess.common.utils.bean.BeanTransform;
import com.bjike.goddess.dispatchcar.api.DispatchCarInfoAPI;
import com.bjike.goddess.dispatchcar.enums.CollectIntervalType;
import com.bjike.goddess.dispatchcar.enums.CollectType;
import com.bjike.goddess.dispatchcar.vo.DispatchCollectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 汇总
 *
 * @Author: [Jason]
 * @Date: [17-4-14 下午2:50]
 * @Description: []
 * @Version: [1.0.0]
 * @Copy: [com.bjike]
 */
@RestController
@RequestMapping("collect")
public class CollectAct {

    @Autowired
    private DispatchCarInfoAPI dispatchCarInfoAPI;

    /**
     * 出车情况汇总
     *
     * @param collectIntervalType 分页条件
     * @version v1
     */
    @GetMapping("v1/dispatchCollect/{collectIntervalType}/{collectType}")
    public Result dispatchCollect(@PathVariable CollectIntervalType collectIntervalType,@PathVariable CollectType collectType) throws ActException {
        try {
            List<DispatchCollectVO> voList = BeanTransform.copyProperties(dispatchCarInfoAPI.dispatchCollect(collectIntervalType,collectType), DispatchCollectVO.class);
            return ActResult.initialize(voList);
        }catch (SerException e){
            throw new ActException(e.getMessage());
        }
    }

    /**
     * 司机出车情况汇总
     *
     * @param collectIntervalType 分页条件
     * @version v1
     */
    @GetMapping("v1/driverCollect/{collectIntervalType}/{collectType}")
    public Result driverCollect(@PathVariable CollectIntervalType collectIntervalType,@PathVariable CollectType collectType) throws ActException {
        try {
            List<DispatchCollectVO> voList = BeanTransform.copyProperties(dispatchCarInfoAPI.dispatchCollect(collectIntervalType,collectType), DispatchCollectVO.class);
            return ActResult.initialize(voList);
        }catch (SerException e){
            throw new ActException(e.getMessage());
        }
    }
}
