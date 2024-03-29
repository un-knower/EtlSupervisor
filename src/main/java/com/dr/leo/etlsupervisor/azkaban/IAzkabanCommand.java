package com.dr.leo.etlsupervisor.azkaban;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/15 9:39
 */
public interface IAzkabanCommand {
    /**
     * 输出到模板
     *
     * @param azkabanCommand 命令对象
     * @return 返回模板保存的路径
     */
    String outTemplate(IAzkabanCommand azkabanCommand);

}
