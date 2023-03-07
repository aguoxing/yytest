package com.test.common;

import com.github.pagehelper.PageInfo;
import com.test.common.page.PageUtils;
import com.test.common.page.TableDataInfo;

import java.util.List;

/**
 * @author gx
 * @date 2023/3/7 17:02
 */
public class BaseController {

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected R toJson(int rows) {
        return rows > 0 ? R.success() : R.error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected R toJson(boolean result) {
        return result ? R.success() : R.error();
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageUtils.startPage();
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(200);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }
}
