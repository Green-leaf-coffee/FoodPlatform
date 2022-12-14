package com.ccut.dachuang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccut.dachuang.Exception.CustomizeException;
import com.ccut.dachuang.common.CommonResponse;
import com.ccut.dachuang.common.ErrorEnum;
import com.ccut.dachuang.model.pojo.BaseArea;
import com.ccut.dachuang.model.pojo.BaseCity;
import com.ccut.dachuang.model.pojo.BaseProvince;
import com.ccut.dachuang.service.BaseAreaService;
import com.ccut.dachuang.service.BaseCityService;
import com.ccut.dachuang.service.BaseProvinceService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "省市区联动")
@RestController
@RequestMapping("/area")
@Slf4j
public class AreaController {

    @Autowired
     BaseAreaService areaService;
    @Autowired
     BaseCityService cityService;
    @Autowired
    BaseProvinceService provinceService;
    /**
     * 获取所有的省份信息
     * @return :返回所有城市
     */
    @ApiOperation(value = "获取所有的省份信息")
    @GetMapping("/getProvince")
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取所有的省份信息成功"),
            @ApiResponse(code = 123, message = "获取所有的省份信息失败"),
    })
    @ResponseBody
    public CommonResponse<List<BaseProvince>> getProvince(){
        List<BaseProvince> list;
        try {
            list = provinceService.list();
        }catch (Exception e){
            throw  new CustomizeException(ErrorEnum.FAILED_TO_GET_PROVINCE);
        }
        log.info("getProvince成功获得省信息");
        return  new CommonResponse<>("获取省份信息成功", "200", list);
    }

    /**
     * 根据省份id获取城市信息
     * @param provincecode :省的编码
     * @return ：返回所有城市
     */
    @ApiOperation(value = "根据省份id获取城市信息")
    @ApiImplicitParam(name = "provincecode",value = "传入的省份id",required =true,paramType = "path",dataType = "String",defaultValue = "360000")
    @GetMapping("/getCity/{provincecode}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取城市信息成功"),
            @ApiResponse(code = 124, message = "获取城市信息失败"),
    })
    public CommonResponse<List<BaseCity>> getCity(@PathVariable String provincecode){
        List<BaseCity> list;

        try {
            list = cityService.list(new QueryWrapper<BaseCity>().eq("provincecode", provincecode));
        }catch (Exception e){
            throw  new CustomizeException(ErrorEnum.GET_CITY_INFORMATION);
        }
        log.info("getCity成功获得城市信息");
        return  new CommonResponse<>("获取城市信息成功", "200", list);
    }
    /**
     * 根据城市id获取县区信息
     * @param citycode :城市编码
     * @return :返回所有县区
     */
    @ApiOperation(value = "根据城市id获取县区信息")
    @ApiImplicitParam(name = "citycode",value = "传入的城市id",required = true,paramType = "path",dataType = "String",defaultValue = "360700")
    @GetMapping("/getArea/{citycode}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取所有的县区信息成功"),
            @ApiResponse(code = 125, message = "获取所有的县区信息失败"),
    })
    public CommonResponse<List<BaseArea>> getArea(@PathVariable String citycode){
        List<BaseArea> list;
        try {
            list = areaService.list(new QueryWrapper<BaseArea>().eq("citycode", citycode));
        }catch (Exception e){
            throw  new CustomizeException(ErrorEnum.FAILED_TO_GET_COUNTY_INFORMATION);
        }
        log.info("getCity成功获得县区信息");
        return new CommonResponse<>("获取城市信息成功", "200", list);
    }

}

