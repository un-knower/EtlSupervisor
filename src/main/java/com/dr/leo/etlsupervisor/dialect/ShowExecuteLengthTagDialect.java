package com.dr.leo.etlsupervisor.dialect;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义显示执行时长的标签的方言类
 *
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/4/30 14:03
 */
public class ShowExecuteLengthTagDialect extends AbstractProcessorDialect {
    private static final String PREFIX = "leo";

    public ShowExecuteLengthTagDialect() {
        super("Leo Dialect", PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String s) {
        // 把所有的自定义tag处理器加入处理器集，这个例子中我们只有这一个自定义处理器
        final Set<IProcessor> processorSet = new HashSet<>();
        ShowExecuteLengthTagProcessor dataMaskingDialectTagProcessor = new ShowExecuteLengthTagProcessor(PREFIX);
        processorSet.add(dataMaskingDialectTagProcessor);
        return processorSet;
    }
}
