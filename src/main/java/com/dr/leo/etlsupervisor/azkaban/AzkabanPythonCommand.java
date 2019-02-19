package com.dr.leo.etlsupervisor.azkaban;

import org.springframework.stereotype.Component;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/15 15:51
 */
@Component
public class AzkabanPythonCommand extends AbstractAzkabanCommand {
    @Override
    public String outTemplate(IAzkabanCommand azkabanCommand) {
        return "";
    }
}
