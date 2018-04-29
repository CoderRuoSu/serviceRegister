package com.aaa.demo.service;

import com.aaa.demo.vo.ResultVo;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ruosu on 2018/4/26.
 */
@Service
public class RealAuthService {

    // 测试方法
    public List getList() {
        return Arrays.asList(1, 2, 3, 4);
    }

    // 测试方法前
    public ResultVo pregetList() {
        System.err.println("准备开始-----");
        return new ResultVo("准备好了");
    }

    // 测试方法后
    public ResultVo postgetList(List list) {
        System.err.println(list);
        System.err.println("打扫现场-----");
        return new ResultVo("执行完毕");
    }

}
