package com.iflytek.voicecloud.itm.dao.config;

import com.iflytek.voicecloud.itm.entity.config.variable.Variable;

import java.util.List;
import java.util.Map;

/**
 * 变量数据访问层接口
 */
public interface VariableDao {

    /**
     * 添加变量
     * @param variable 变量子类
     * @return  添加成功返回主键id
     */
    int addVariable(Object variable);

    /**
     * 根据主键id获取变量对象
     * @param id  主键id
     * @return  变量对象
     */
    Variable getVariableById(int id);

    /**
     * 根据条件查询变量列表
     * @param condition  查询的变量对象
     * @return  对象列表
     */
    List<Variable> getVariable(Map<String, Object> condition);

    /**
     * 通过变量主键删除变量
     * @param variableId    变量主键
     * @return  影响行数
     */
    int deleteVariableById(int variableId);

    /**
     * 更新变量
     * @param variable    变量对象
     * @return 影响行数
     */
    int updateVariableById(Object variable);
}
