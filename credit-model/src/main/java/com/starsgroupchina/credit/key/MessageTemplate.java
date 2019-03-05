package com.starsgroupchina.credit.key;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 11:22 2018/8/7
 * @Modifed By:
 */
public enum MessageTemplate {

    AssgnTemplate("指派模板", "您好,{name}给您分配了一个新任务，项目编号为{projectNo}，请尽快处理");
    private String name;
    private String content;

    MessageTemplate(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
