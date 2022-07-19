package com.ah.cloud.permissions.biz.infrastructure.config.mp;

import cn.hutool.core.util.ObjectUtil;
import com.ah.cloud.permissions.biz.application.strategy.datascope.DataScopeStrategy;
import com.ah.cloud.permissions.biz.application.strategy.facroty.DataScopeStrategyFactory;
import com.ah.cloud.permissions.biz.infrastructure.annotation.DataScope;
import com.ah.cloud.permissions.enums.DataScopeEnum;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yinjinbiao
 * @version 1.0
 * @description
 * @create 2022/5/30 10:02
 */
@Slf4j
public class DataScopePermissionHandler implements DataPermissionHandler {

    @SneakyThrows
    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        DataScope dataScope = DataScopeContextHolder.getContext();
        if (ObjectUtil.isNull(dataScope)) {
            return where;
        }
        DataScopeStrategy dataScopeStrategy = DataScopeStrategyFactory.get(DataScopeEnum.DEFAULT_SCOPE);
        String sql = dataScopeStrategy.getSql();
        if (StringUtils.isBlank(sql)) {
            return where;
        }

        if (ObjectUtil.isNull(where)) {
            where = new HexValue(" 1 = 1 ");
        }

        Expression expression = CCJSqlParserUtil.parseCondExpression(sql);
        return new AndExpression(where, new Parenthesis(expression));
    }

}
