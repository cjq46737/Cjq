/*
 * @projectName xsxcrw
 * @package com.thunisoft.xsxcpt.xsxclc.zhfx.test
 * @className com.thunisoft.xsxcpt.xsxclc.zhfx.test.Keywords
 * @copyright Copyright 2020 Thuisoft, Inc. All rights reserved.
 */
package com.thunisoft.xsxcpt.xsxclc.zhfx.test;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Keywords
 * @description TODO
 * @author chenjunqi
 * @date 2020年12月22日 14:10
 * @version 1.0.0
 */
@Getter
public class Keywords {

    public final static List<String> keywords = new ArrayList<>(160);

    static {
        keywords.add("背景");
        keywords.add("平台");
        keywords.add("李云");
        keywords.add("案件");
        keywords.add("详情");
        keywords.add("业务");
        keywords.add("流程");
        keywords.add("财务");
        keywords.add("状态");
        keywords.add("软件");
        keywords.add("功能");
        keywords.add("信息");
        keywords.add("接口");
        keywords.add("设计");
        keywords.add("用户");
        keywords.add("跨部门");
        keywords.add("办案");
        keywords.add("大数据");
        keywords.add("统计");
        keywords.add("资源");
        keywords.add("规划");
        keywords.add("总体");
        keywords.add("要求");
        keywords.add("安全");
        keywords.add("加密");
        keywords.add("密码");
        keywords.add("审计");
        keywords.add("追踪");
        keywords.add("出错");
        keywords.add("负载均衡");
        keywords.add("分布式");
        keywords.add("维护");
        keywords.add("配置");
        keywords.add("监控");
        keywords.add("升级");
        keywords.add("审查");
        keywords.add("性能设计");
        keywords.add("保障");
        keywords.add("小组");
        keywords.add("国务院");
        keywords.add("下发");
        keywords.add("多级联动");
        keywords.add("薄弱");
        keywords.add("公安");
        keywords.add("法院");
        keywords.add("检察院");
        keywords.add("监狱");
        keywords.add("司法局");
        keywords.add("国安");
        keywords.add("严重");
        keywords.add("制约");
        keywords.add("贵州省");
        keywords.add("共享");
        keywords.add("集合");
        keywords.add("电子化");
        keywords.add("工作会议");
        keywords.add("逮捕");
        keywords.add("起诉");
        keywords.add("网络拓扑图");
        keywords.add("一审");
        keywords.add("二审上诉");
        keywords.add("二审抗诉");
        keywords.add("决定再审");
        keywords.add("反馈");
        keywords.add("执行");
        keywords.add("复议");
        keywords.add("送达");
        keywords.add("原因");
        keywords.add("撤回");
        keywords.add("公开");
        keywords.add("宣布");
        keywords.add("立即");
        keywords.add("释放");
        keywords.add("复核");
        keywords.add("交由");
        keywords.add("流程图");
        keywords.add("意见书");
        keywords.add("文书");
        keywords.add("卷宗");
        keywords.add("统计分析");
        keywords.add("消息");
        keywords.add("超级");
        keywords.add("检索");
        keywords.add("辅助");
        keywords.add("元数据");
        keywords.add("门户");
        keywords.add("存储");
        keywords.add("用户鉴权");
        keywords.add("双击");
        keywords.add("备份");
        keywords.add("集群");
        keywords.add("运维");
        keywords.add("驻场");
        keywords.add("开发");
        keywords.add("重叠");
        keywords.add("zabbix");
        keywords.add("小米");
        keywords.add("中间件");
        keywords.add("数据库");
        keywords.add("磁盘");
        keywords.add("网络");
        keywords.add("cpu");
        keywords.add("简单");
        keywords.add("复杂");
        keywords.add("测试");
        keywords.add("人工升级");
        keywords.add("错误");
        keywords.add("自动化部署");
        keywords.add("定位");
        keywords.add("防篡改");
        keywords.add("携带");
        keywords.add("其阿明");
        keywords.add("优点");
        keywords.add("缺点");
        keywords.add("s3");
        keywords.add("thunisoft");
        keywords.add("传输");
        keywords.add("解析");
        keywords.add("易用性");
        keywords.add("稳固性");
        keywords.add("平滑");
        keywords.add("扩展");
        keywords.add("干警");
        keywords.add("涉案人员");
        keywords.add("案件编号");
        keywords.add("组织机构");
        keywords.add("单位表");
        keywords.add("人员表");
        keywords.add("联系电话");
        keywords.add("换押");
        keywords.add("结构化");
        keywords.add("日志");
        keywords.add("标准");
        keywords.add("基础");
        keywords.add("订阅");
        keywords.add("发布");
        keywords.add("页面");
        keywords.add("多维度");
        keywords.add("物品");
        keywords.add("范畴");
        keywords.add("依法");
        keywords.add("冻结");
        keywords.add("拆封");
        keywords.add("机关");
        keywords.add("宣判");
        keywords.add("汇聚");
        keywords.add("监督");
        keywords.add("案由");
        keywords.add("存根");
        keywords.add("退回");
        keywords.add("单独");
        keywords.add("全文检索");
        keywords.add("前后端");
        keywords.add("架构");
        keywords.add("如下");
        keywords.add("输入");
        keywords.add("输出");
        keywords.add("登记");
        keywords.add("平台统一编号");
        keywords.add("串联");
    }

    public static void main(String[] args) {
        Set<String> key = new HashSet<>(keywords);
        System.out.println(key.size());
    }
}
