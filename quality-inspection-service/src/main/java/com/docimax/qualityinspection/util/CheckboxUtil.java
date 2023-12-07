package com.docimax.qualityinspection.util;

import com.aspose.words.*;

/**
 * @author : lierlin
 * @className : ClientController
 * @description : 客户端(应用)管理
 * @date : 2023/7/26 0:15
 */
public class CheckboxUtil {
    public void getCheckbox() throws Exception {
        // 创建新的文档
        Document doc = new Document();

        // 创建表格并插入到文档中
        Table table = new Table(doc);

        // 插入一个带有控件属性的单元格
        Cell cell = new Cell(doc);
        cell.getCellFormat().setWidth(100.0);

        // 创建字体样式
        Font checkBoxFont = doc.getStyles().getDefaultFont();

        // 设置选中状态的复选框
        Run checkedRun = new Run(doc, "\u00FE");
        cell.appendChild(checkedRun);

        // 设置未选中状态的复选框
        Run uncheckedRun = new Run(doc, "\u00A8");
        cell.appendChild(uncheckedRun);

        // 添加单元格到表格
        table.getFirstRow().appendChild(cell);
    }
}
