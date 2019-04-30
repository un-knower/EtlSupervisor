package com.dr.leo.etlsupervisor.dialect;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IAttribute;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * templateMode: 模板模式，这里使用HTML模板。
 * dialectPrefix: 标签前缀。即xxx:text中的xxx。在此例子中prefix为thSys。
 * elementName：匹配标签元素名。举例来说如果是div，则我们的自定义标签只能用在div标签中。为null能够匹配所有的标签。
 * prefixElementName: 标签名是否要求前缀。
 * attributeName: 自定义标签属性名。这里为text。
 * prefixAttributeName：属性名是否要求前缀，如果为true，Thymeeleaf会要求使用text属性时必须加上前缀，即thSys:text。
 * precedence：标签处理的优先级，此处使用和Thymeleaf标准方言相同的优先级。
 * removeAttribute：标签处理后是否移除自定义属性。
 *
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/4/30 14:09
 */
@Slf4j
public class ShowExecuteLengthTagProcessor extends AbstractAttributeTagProcessor {
    private static final String START_TIME_ATTRIBUTE = "diffTime";


    public ShowExecuteLengthTagProcessor(String prefix) {
        super(TemplateMode.HTML, prefix, null, false,
                START_TIME_ATTRIBUTE, true, 1000, true);
    }

    @Override
    protected void doProcess(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag,
                             AttributeName attributeName, String s, IElementTagStructureHandler iElementTagStructureHandler) {
        //s为自定义属性startTime的内容，如果s为表达式，该函数可以获取表达式的值
        final Object diffTimeValue = getExpressionValue(iTemplateContext, s);
        log.info("运行时长：{}", diffTimeValue);
        if (null == diffTimeValue || Long.valueOf(diffTimeValue.toString()) < 0) {
            iElementTagStructureHandler.setBody("-", false);
        } else {
            iElementTagStructureHandler.setBody(computerRunTime(Long.parseLong(String.valueOf(diffTimeValue))), false);
        }
    }

    private Object getExpressionValue(ITemplateContext iTemplateContext, String expressionString) {
        final IEngineConfiguration configuration = iTemplateContext.getConfiguration();
        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
        // 解析expression
        final IStandardExpression expression = parser.parseExpression(iTemplateContext, expressionString);
        // 获取expression的执行结果
        return expression.execute(iTemplateContext);
    }

    private String computerRunTime(Long diffTime) {
        Long totalSeconds = diffTime / 1000;
        Long hour = totalSeconds / 3600;
        Long day = 0L;
        if (hour >= 24) {
            day = hour / 24;
            hour = hour % 24;
        }
        Long seconds = totalSeconds % 3600;
        Long min = seconds / 60;
        Long second = seconds % 60;
        if (second > 0 && min == 0 && hour == 0 && day == 0) {
            return StrUtil.format("{}秒", second);
        } else if (second > 0 && min > 0 && hour == 0 && day == 0) {
            return StrUtil.format("{}分钟{}秒", min, second);
        } else if (hour > 0 && day == 0) {
            return StrUtil.format("{}小时{}分钟{}秒", hour, min, second);
        } else if (day > 0) {
            return StrUtil.format("{}天{}小时{}分钟{}秒", day, hour, min, second);
        }

        return "0秒";
    }

}
