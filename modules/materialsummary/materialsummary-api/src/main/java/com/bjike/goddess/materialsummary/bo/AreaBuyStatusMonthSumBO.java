package com.bjike.goddess.materialsummary.bo;

import com.bjike.goddess.common.api.bo.BaseBO;
import com.bjike.goddess.common.api.type.Status;

/**
 * 地区购买情况月汇总业务传输对象
 *
 * @Author: [ sunfengtao ]
 * @Date: [ 2017-05-22 10:54 ]
 * @Description: [ 地区购买情况月汇总业务传输对象 ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
public class AreaBuyStatusMonthSumBO extends BaseBO {

    /**
     * 汇总起始时间
     */
    private String sumStartTime;

    /**
     * 汇总结束时间
     */
    private String sumEndTime;

    /**
     * 地区
     */
    private String area;

    /**
     * 部门(项目组)
     */
    private String projectGroup;

    /**
     * 物资类型
     */
    private String deviceType;

    /**
     * 总数量
     */
    private Integer totalQty;

    /**
     * 总金额
     */
    private Double amoutn;

    /**
     * 状态
     */
    private Status status;

    public String getSumStartTime() {
        return sumStartTime;
    }

    public void setSumStartTime(String sumStartTime) {
        this.sumStartTime = sumStartTime;
    }

    public String getSumEndTime() {
        return sumEndTime;
    }

    public void setSumEndTime(String sumEndTime) {
        this.sumEndTime = sumEndTime;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProjectGroup() {
        return projectGroup;
    }

    public void setProjectGroup(String projectGroup) {
        this.projectGroup = projectGroup;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public Double getAmoutn() {
        return amoutn;
    }

    public void setAmoutn(Double amoutn) {
        this.amoutn = amoutn;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}